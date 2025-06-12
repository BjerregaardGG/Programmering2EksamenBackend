package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;
import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import eksamen.programmering2eksamenbackend.Siren.SirenRepository;
import eksamen.programmering2eksamenbackend.Siren.SirenStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FireServiceImplTest {

    @InjectMocks
    FireServiceImpl fireService;

    @Mock
    FireRepository fireRepository;

    @Mock
    SirenRepository sirenRepository;

    private List<FireModel> fireModelList = new ArrayList<>();
    private List<FireModel> activeFires = new ArrayList<>();
    private FireModel fire1 = new FireModel();
    private SirenModel siren = new SirenModel();

    // dummy data
    @BeforeEach
    void setUp(){

        fire1.setLatitude(34.0195);
        fire1.setLongitude(-118.4913);
        fire1.setReportedAt(LocalDateTime.now());
        fire1.setStatus(FireStatus.ACTIVE);

        FireModel fire2 = new FireModel();
        fire2.setLatitude(34.0500);
        fire2.setLongitude(-118.5300);
        fire2.setStatus(FireStatus.ACTIVE);
        fire2.setReportedAt(LocalDateTime.now().minusHours(1));

        siren.setStatus(SirenStatus.ALARM);
        fire1.setSirens(Arrays.asList(siren));

        fireModelList.add(fire1);
        fireModelList.add(fire2);

        // Filter kun de aktive til repository mock
        activeFires = fireModelList.stream()
                .filter(f -> f.getStatus() == FireStatus.ACTIVE)
                .collect(Collectors.toList());

    }

    // tester om findActiveFires finder fires med ACTIVE
    @Test
    void findActiveFires() {

        Mockito.when(fireRepository.findByStatus(FireStatus.ACTIVE)).thenReturn(activeFires);

        List<FireDTO> fires = fireService.findActiveFires();

        assertEquals(fires.size(), activeFires.size());

        Mockito.verify(fireRepository, times(1)).findByStatus(FireStatus.ACTIVE);
    }

    // tester om closeFire() slukker en brand og validerer fire- og sirenestatus
    @Test
    public void closeFire(){

        Mockito.when(fireRepository.findById(1)).thenReturn(Optional.of(fire1));

        fireService.closeFire(1);

        assertEquals(FireStatus.CLOSED, fire1.getStatus());
        assertEquals(SirenStatus.PEACE, siren.getStatus());
        Mockito.verify(fireRepository, times(1)).save(fire1);
    }

    // tester hjælpemetode ud fra forskellige værdier
    @Test
    void calculateDistanceKM() {

        // Test samme punkt
        double dist = fireService.calculateDistanceKM(55.6761, 12.5683, 55.6761, 12.5683);
        assertEquals(0.0, dist, 0.0001);

        // Test to punkter tæt på hinanden - afstand skal være større end 0
        double dist2 = fireService.calculateDistanceKM(55.6761, 12.5683, 55.6850, 12.5700);
        assertTrue(dist2 > 0);

        // Østerbro til Nørrebro - afstand skal være i intervallet 1.45 km og 2.05 km
        double dist3 = fireService.calculateDistanceKM(55.7053, 12.5700, 55.6929, 12.5528);
        System.out.println("dist3 = " + dist3);
        assertTrue(dist3 > 0);
        assertTrue(Math.abs(dist3 - 1.75) < 0.3);
    }

}