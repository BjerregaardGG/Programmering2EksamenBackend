package eksamen.programmering2eksamenbackend.Siren;

import eksamen.programmering2eksamenbackend.Fire.FireModel;
import eksamen.programmering2eksamenbackend.Fire.FireServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SirenServiceImplTest {

    @InjectMocks
    SirenServiceImpl sirenService;

    @Mock
    SirenRepository sirenRepository;

    private List<SirenModel> sirenModelList = new ArrayList<>();

    @BeforeEach
    void setUp(){

        SirenModel s1 = new SirenModel("Santa Monica Pier", 34.0194, -118.4912, SirenStatus.PEACE, false);
        SirenModel s2 = new SirenModel("Venice Beach", 34.0218, -118.4815, SirenStatus.PEACE, false);
        SirenModel s3 = new SirenModel("Pacific Palisades Center", 34.0522, -118.5270, SirenStatus.PEACE, false);
        SirenModel s4 = new SirenModel("Malibu Creek", 34.0259, -118.6081, SirenStatus.PEACE, false);

        sirenModelList.addAll(List.of(s1,s2,s3,s4));
    }


    @Test
    void findAllSirens() {

        Mockito.when(sirenRepository.findAll()).thenReturn(sirenModelList);

        List<SirenDTO> sirenDTOS = sirenService.findAllSirens();

        assertEquals(4, sirenDTOS.size());
        Mockito.verify(sirenRepository, times(1)).findAll();

    }

    @Test
    void findSirenById() {
        SirenModel expected = sirenModelList.get(0);
        Mockito.when(sirenRepository.findById(1)).thenReturn(Optional.ofNullable((expected)));

        SirenDTO siren = sirenService.findSirenById(1);

        assertEquals(expected.getName(), siren.getName());
        Mockito.verify(sirenRepository).findById(1);
    }

    @Test
    void createSiren() {

        SirenDTO inputDTO = new SirenDTO("Malibu Creek", 34.0259, -118.6081, SirenStatus.PEACE, false);
        SirenModel savedModel = new SirenModel("Malibu Creek", 34.0259, -118.6081, SirenStatus.PEACE, false);

        Mockito.when(sirenRepository.save(Mockito.any(SirenModel.class))).thenReturn(savedModel);

        SirenDTO result = sirenService.createSiren(inputDTO);

        assertEquals("Malibu Creek", result.getName());
        assertEquals(34.0259, result.getLatitude());
        assertEquals(-118.6081, result.getLongitude());
        assertEquals(SirenStatus.PEACE, result.getStatus());
        assertFalse(result.isDisabled());

    }

}