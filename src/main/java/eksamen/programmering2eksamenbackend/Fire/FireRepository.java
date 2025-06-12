package eksamen.programmering2eksamenbackend.Fire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireRepository extends JpaRepository<FireModel, Integer> {

    // databasekald til at finde fire ud fra status
    List<FireModel> findByStatus(FireStatus status);


}
