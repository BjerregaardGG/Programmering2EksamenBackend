package eksamen.programmering2eksamenbackend.Siren;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SirenRepository extends JpaRepository<SirenModel, Integer> {

}
