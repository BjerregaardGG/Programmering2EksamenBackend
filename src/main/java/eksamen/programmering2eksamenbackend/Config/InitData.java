package eksamen.programmering2eksamenbackend.Config;

import eksamen.programmering2eksamenbackend.Fire.FireModel;
import eksamen.programmering2eksamenbackend.Fire.FireRepository;
import eksamen.programmering2eksamenbackend.Fire.FireStatus;
import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import eksamen.programmering2eksamenbackend.Siren.SirenRepository;
import eksamen.programmering2eksamenbackend.Siren.SirenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private final SirenRepository sirenRepository;
    private final FireRepository fireRepository;

    public InitData(SirenRepository sirenRepository, FireRepository fireRepository) {
        this.sirenRepository = sirenRepository;
        this.fireRepository = fireRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        // Testdata: 10 sirener i og omkring Santa Monica / Pacific Palisades
        SirenModel s1 = sirenRepository.save(new SirenModel("Alarm 1", 34.0194, -118.4912, SirenStatus.PEACE, false));  // Santa Monica Pier
        SirenModel s2 =sirenRepository.save(new SirenModel("Alarm 2",34.0218, -118.4815, SirenStatus.PEACE, false));  // Venice Beach
        SirenModel s3 =sirenRepository.save(new SirenModel("Alarm 3", 34.0522, -118.5270, SirenStatus.PEACE, false));  // Pacific Palisades Center
        SirenModel s4 =sirenRepository.save(new SirenModel("Alarm 4", 34.0259, -118.6081, SirenStatus.PEACE, false));  // Malibu Creek
        SirenModel s5 =sirenRepository.save(new SirenModel("Alarm 5",34.0364, -118.5847, SirenStatus.PEACE, false));  // Topanga Beach
        SirenModel s6 =sirenRepository.save(new SirenModel("Alarm 6",34.0386, -118.5128, SirenStatus.PEACE, false));  // Will Rogers Beach
        SirenModel s7 =sirenRepository.save(new SirenModel("Alarm 7",33.8847, -118.4109, SirenStatus.PEACE, false));  // Manhattan Beach Pier
        SirenModel s8 =sirenRepository.save(new SirenModel("Alarm 8",33.8622, -118.3992, SirenStatus.PEACE, false));  // Hermosa Beach
        SirenModel s9 =sirenRepository.save(new SirenModel("Alarm 9",33.8492, -118.3892, SirenStatus.PEACE, false));  // Redondo Beach
        SirenModel s10 =sirenRepository.save(new SirenModel("Alarm 10",33.9192, -118.4165, SirenStatus.PEACE, false));  // El Segundo

        // Opret en brand ved Santa Monica
        FireModel fire1 = new FireModel();
        fire1.setLatitude(34.0195);
        fire1.setLongitude(-118.4913);
        fire1.setReportedAt(LocalDateTime.now());
        fire1.setStatus(FireStatus.ACTIVE);
        fire1.setSirens(List.of(s1, s2, s6));

        FireModel fire2 = new FireModel();
        fire2.setLatitude(34.0500);
        fire2.setLongitude(-118.5300);
        fire2.setStatus(FireStatus.ACTIVE);
        fire2.setReportedAt(LocalDateTime.now().minusHours(1));
        fire2.setSirens(List.of(s3, s4, s5));

        fireRepository.saveAll(List.of(fire1,fire2));
    }






}
