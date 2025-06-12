package eksamen.programmering2eksamenbackend.Config;

import eksamen.programmering2eksamenbackend.Fire.FireModel;
import eksamen.programmering2eksamenbackend.Fire.FireRepository;
import eksamen.programmering2eksamenbackend.Fire.FireStatus;
import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import eksamen.programmering2eksamenbackend.Siren.SirenRepository;
import eksamen.programmering2eksamenbackend.Siren.SirenServiceImpl;
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
    private final SirenServiceImpl sirenService;

    public InitData(SirenRepository sirenRepository, FireRepository fireRepository, SirenServiceImpl sirenService) {
        this.sirenRepository = sirenRepository;
        this.fireRepository = fireRepository;
        this.sirenService = sirenService;
    }


    @Override
    public void run(String... args) throws Exception {

        // Testdata: 10 sirener i og omkring Santa Monica / Pacific Palisades
        SirenModel s1 = sirenRepository.save(new SirenModel("Santa Monica Pier", 34.0194, -118.4912, SirenStatus.PEACE, false));
        SirenModel s2 = sirenRepository.save(new SirenModel("Venice Beach", 34.0218, -118.4815, SirenStatus.PEACE, false));
        SirenModel s3 = sirenRepository.save(new SirenModel("Pacific Palisades Center", 34.0522, -118.5270, SirenStatus.PEACE, false));
        SirenModel s4 = sirenRepository.save(new SirenModel("Malibu Creek", 34.0259, -118.6081, SirenStatus.PEACE, false));
        SirenModel s5 = sirenRepository.save(new SirenModel("Topanga Beach", 34.0364, -118.5847, SirenStatus.PEACE, false));
        SirenModel s6 = sirenRepository.save(new SirenModel("Will Rogers Beach", 34.0386, -118.5128, SirenStatus.PEACE, false));
        SirenModel s7 = sirenRepository.save(new SirenModel("Manhattan Beach Pier", 33.8847, -118.4109, SirenStatus.PEACE, false));
        SirenModel s8 = sirenRepository.save(new SirenModel("Hermosa Beach", 33.8622, -118.3992, SirenStatus.PEACE, false));
        SirenModel s9 = sirenRepository.save(new SirenModel("Redondo Beach", 33.8492, -118.3892, SirenStatus.PEACE, false));
        SirenModel s10 = sirenRepository.save(new SirenModel("El Segundo", 33.9192, -118.4165, SirenStatus.PEACE, false));

        // Opret en brand ved Santa Monica
        FireModel fire1 = new FireModel();
        fire1.setLatitude(34.0195);
        fire1.setLongitude(-118.4913);
        fire1.setReportedAt(LocalDateTime.now());
        fire1.setStatus(FireStatus.ACTIVE);

        // Relationen er dobbeltejet (bidirektionel)
        fire1.setSirens(List.of(s1, s2, s6));
        s1.getFires().add(fire1);
        s2.getFires().add(fire1);
        s6.getFires().add(fire1);

        FireModel fire2 = new FireModel();
        fire2.setLatitude(34.0500);
        fire2.setLongitude(-118.5300);
        fire2.setStatus(FireStatus.ACTIVE);
        fire2.setReportedAt(LocalDateTime.now().minusHours(1));

        // Relationen er dobbeltejet (bidirektionel)
        fire2.setSirens(List.of(s3, s4));
        s3.getFires().add(fire2);
        s4.getFires().add(fire2);

        FireModel fire3 = new FireModel();
        fire3.setLatitude(34.0500);
        fire3.setLongitude(-118.5300);
        fire3.setStatus(FireStatus.ACTIVE);
        fire3.setReportedAt(LocalDateTime.now().minusHours(1));

        // Relationen er dobbeltejet (bidirektionel)
        fire3.setSirens(List.of(s8, s9));
        s8.getFires().add(fire3);
        s9.getFires().add(fire3);

        fireRepository.saveAll(List.of(fire1,fire2,fire3));
        sirenService.activateSirensForFire(fire1);
        sirenService.activateSirensForFire(fire2);
        sirenService.activateSirensForFire(fire3);
    }


}
