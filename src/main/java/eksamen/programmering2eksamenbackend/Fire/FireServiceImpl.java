package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import eksamen.programmering2eksamenbackend.Siren.SirenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireServiceImpl {

    @Autowired
    private FireRepository fireRepository;

    @Autowired
    private SirenRepository sirenRepository;

    private static final int EARTH_RADIUS_KM = 6371;

    public List<FireDTO> findAllFires(){
        return null;
    }

    public List<FireDTO> findActiveFires(){
        return null;
    }

    public FireDTO closeFire(Long fireId){
        return null;
    }

    /*
    public FireModel reportFire(double latitude, double longitude) {
        // 1. Opret brand
        FireModel fire = new FireModel(latitude, longitude);
        fire = fireRepository.save(fire);

        // 2. Find sirener inden for 10km
        List<SirenModel> nearbySirens = findSirensWithinRadius(latitude, longitude, 10.0);

        // 3. Aktiver tilgængelige sirener
        List<SirenModel> availableSirens = nearbySirens.stream()
                .filter(s -> !s.isDisabled())
                .collect(Collectors.toList());

        for (SirenModel siren : availableSirens) {
            fire.addSiren(siren);
            siren.activate();
        }

        sirenRepository.saveAll(availableSirens);
        return fireRepository.save(fire);
    }

     */

    private List<SirenModel> findSirensWithinRadius(double latitude, double longitude, double radiusKm) {
        List<SirenModel> allSirens = sirenRepository.findByDisabledFalse();

        return allSirens.stream()
                .filter(siren -> calculateDistanceKM(latitude, longitude,
                        siren.getLatitude(),
                        siren.getLongitude()) <= radiusKm)
                .collect(Collectors.toList());
    }

    // HAVERSINE FORMULA - beregner afstand mellem to koordinater
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


}
