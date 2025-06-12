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

    // finder aktive fires
    public List<FireDTO> findActiveFires(){

        // finder fires baseret på ACTIVE
        List<FireModel> fires = fireRepository.findByStatus(FireStatus.ACTIVE);

        // omdanner til DTO'er
        List<FireDTO> fireDtos = new ArrayList<>();
        for (FireModel fire : fires) {
            FireDTO dto = new FireDTO();
            dto.setId(fire.getId());
            dto.setLatitude(fire.getLatitude());
            dto.setLongitude(fire.getLongitude());
            dto.setStatus(fire.getStatus());
            dto.setClosedAt(fire.getClosedAt());
            dto.setReportedAt(fire.getReportedAt());


            //Konverterer sirener til DTO'er
            List<SirenDTO> sirenDTOs = fire.getSirens()
                    .stream()
                    .map(this::convertToSirenDTO)
                    .collect(Collectors.toList());

            dto.setActivatedSirens(sirenDTOs);
            fireDtos.add(dto);
        }

        // returnerer til sidst fire DTO
        return fireDtos;

    }

    // finder closed fires
    @Override
    public List<FireDTO> findClosedFires() {

        // finder fires baseret på status CLOSED
        List<FireModel> fires = fireRepository.findByStatus(FireStatus.CLOSED);

        // omdanner til DTO'er
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

    // slukker en fire
    @Override
    public void closeFire(int fireId) {

        // finder først fire baseret på id
        FireModel fire = fireRepository.findById(fireId)
                .orElseThrow(() -> new EntityNotFoundException("Fire not found"));

        // sætter status til CLOSED og localDate til nuværende tidspunkt
        fire.setStatus(FireStatus.CLOSED);
        fire.setClosedAt(LocalDateTime.now());

        // ittererer over sirener for den specifikke fire om sætter dem til PEACE
        List<SirenModel> sirens = fire.getSirens();
        for(SirenModel s : sirens){
            s.setStatus(SirenStatus.PEACE);
            sirenRepository.save(s);
        }

        // gemmer til sidst fire i databasen
        fireRepository.save(fire);

    }

    // rapporterer om fire
    @Override
    @Transactional // automatisk opdatering
    public FireDTO reportFire(double latitude, double longitude) {

        // Opretter først en fire med status ACTIVE
        FireModel fire = new FireModel(latitude, longitude);
        fire.setStatus(FireStatus.ACTIVE);
        fire = fireRepository.save(fire); // får id

        // 2. Find sirener inden for en radius af 10km med hjælpemetode
        List<SirenModel> nearbySirens = findSirensWithinRadius(latitude, longitude, 10.0);

        // 3. Find derefter sirener som ikke er disabled
        List<SirenModel> availableSirens = nearbySirens.stream()
                .filter(s -> !s.isDisabled()) // filtrerer kun aktive sirener
                .peek(siren -> { // opdaterer hver hver siren med peek for at undgå seperat loop
                    siren.setStatus(SirenStatus.ALARM); // sætter status til ALARM på sirene
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

    // hjælpemetode til at finde sirener indenfor en specifik radius
    private List<SirenModel> findSirensWithinRadius(double latitude, double longitude, double radiusKm) {

        // henter alle sirener
        List<SirenModel> allSirens = sirenRepository.findAll();

        // udregner distance mellem fire (to koords) og sirener (to koords)
        return allSirens.stream()
                .filter(siren -> calculateDistanceKM(latitude, longitude, // kalder hjælpemetode
                        siren.getLatitude(),
                        siren.getLongitude()) <= radiusKm) // mindre eller lig med radiusmKm
                .collect(Collectors.toList()); // returnerer alle sirener indenfor afstanden
    }

    // Hjælpemetode til at beregne afstand mellem to punkter ud fra fire koordinater
    double calculateDistanceKM(double lat1, double lon1, double lat2, double lon2) {

        // konverterer grader til radianer (F.eks. 180° = π radianer)
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // beregner forskellen i bredde- og længdegrad mellem de to punkter
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Haversine-formlen
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // ganger til sidst med jordens radius for at få km afstand
        return EARTH_RADIUS_KM * c;
    }

    // hjælpemetode til at konvertere SireneModel til DTO
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
