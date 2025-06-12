package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;
import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import eksamen.programmering2eksamenbackend.Siren.SirenRepository;
import eksamen.programmering2eksamenbackend.Siren.SirenStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireServiceImpl implements FireService {

    @Autowired
    private FireRepository fireRepository;

    @Autowired
    private SirenRepository sirenRepository;

    private static final int EARTH_RADIUS_KM = 6371;

    public List<FireDTO> findAllFires(){
        return null;
    }

    public List<FireDTO> findActiveFires(){

        List<FireModel> fires = fireRepository.findByStatus(FireStatus.ACTIVE);

        List<FireDTO> fireDtos = new ArrayList<>();
        for (FireModel fire : fires) {
            FireDTO dto = new FireDTO();
            dto.setId(fire.getId());
            dto.setLatitude(fire.getLatitude());
            dto.setLongitude(fire.getLongitude());
            dto.setStatus(fire.getStatus());
            dto.setClosedAt(fire.getClosedAt());
            dto.setReportedAt(fire.getReportedAt());


            //Konverterer sirener til DTOs
            List<SirenDTO> sirenDTOs = fire.getSirens()
                    .stream()
                    .map(this::convertToSirenDTO)
                    .collect(Collectors.toList());

            dto.setActivatedSirens(sirenDTOs);
            fireDtos.add(dto);
        }

        return fireDtos;

    }

    @Override
    public void closeFire(int fireId) {

        FireModel fire = fireRepository.findById(fireId)
                .orElseThrow(() -> new EntityNotFoundException("Fire not found"));

        fire.setStatus(FireStatus.CLOSED);
        fire.setClosedAt(LocalDateTime.now());

        List<SirenModel> sirens = fire.getSirens();
        for(SirenModel s : sirens){
            s.setStatus(SirenStatus.PEACE);
            sirenRepository.save(s);
        }

        fireRepository.save(fire);


    }

    @Override
    @Transactional // automatisk opdatering
    public FireDTO reportFire(double latitude, double longitude) {

        // 1. Opret brand
        FireModel fire = new FireModel(latitude, longitude);
        fire.setStatus(FireStatus.ACTIVE);
        fire = fireRepository.save(fire); // får id

        // 2. Find sirener inden for en radius af 10km med hjælpemetode
        List<SirenModel> nearbySirens = findSirensWithinRadius(latitude, longitude, 10.0);

        // 3. Find derefter sirener som ikke er disabled
        List<SirenModel> availableSirens = nearbySirens.stream()
                .filter(s -> !s.isDisabled()) // kun aktive sirener
                .peek(siren -> { // opdaterer hver hver siren med peek uden seperat loop
                    siren.setStatus(SirenStatus.ALARM);
                    siren.setLastActivated(LocalDateTime.now());
                })
                .collect(Collectors.toList()); // returnerer en liste

        // 5. opretter relationer hvis der er sirener (join tabel)
        if(!availableSirens.isEmpty()) {
            fire.getSirens().addAll(availableSirens);
        }

        // 5. Gem alle ændringer
        sirenRepository.saveAll(availableSirens);  // Batch-opdatering efter ændringer i peek
        fire = fireRepository.save(fire);  // Opdater fire med relationer

        return new FireDTO(fire);

    }

    private List<SirenModel> findSirensWithinRadius(double latitude, double longitude, double radiusKm) {
        List<SirenModel> allSirens = sirenRepository.findAll();

        // udregner distance mellem fire og sirener og returnerer alle sirener indenfor afstanden
        return allSirens.stream()
                .filter(siren -> calculateDistanceKM(latitude, longitude,
                        siren.getLatitude(),
                        siren.getLongitude()) <= radiusKm)
                .collect(Collectors.toList());
    }

    // Hjælpemetode til at beregne afstand mellem to koordinater
    private double calculateDistanceKM(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private SirenDTO convertToSirenDTO(SirenModel siren) {
        SirenDTO dto = new SirenDTO();
        dto.setId(siren.getId());
        dto.setName(siren.getName());
        dto.setLatitude(siren.getLatitude());
        dto.setLongitude(siren.getLongitude());
        dto.setStatus(siren.getStatus());
        dto.setDisabled(siren.isDisabled());
        dto.setLastActivated(siren.getLastActivated());
        return dto;
    }


}
