package eksamen.programmering2eksamenbackend.Fire;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
class FireRepositoryIntegrationTest {

    @Autowired
    FireRepository fireRepository;

    private List<FireModel> fires = new ArrayList<>();

    @BeforeEach
    public void setup() {
        fireRepository.deleteAll();
        FireModel fire1 = new FireModel();
        fire1.setStatus(FireStatus.ACTIVE);
        FireModel fire2 = new FireModel();
        fire2.setStatus(FireStatus.ACTIVE);
        FireModel fire3 = new FireModel();
        fire3.setStatus(FireStatus.CLOSED);
        FireModel fire4 = new FireModel();
        fire4.setStatus(FireStatus.CLOSED);

        fires = fireRepository.saveAll(List.of(fire1, fire2, fire3, fire4));

    }

    @Test
    void findAllFires(){

        List<FireModel> fireList = fireRepository.findAll();

        assertEquals(4, fireList.size());
        assertEquals(fires.size(), fireList.size());

    }

    @Test
    void findByStatus() {

        List<FireModel> fireList = fireRepository.findByStatus(FireStatus.ACTIVE);

        assertEquals(2, fireList.size());

    }
}