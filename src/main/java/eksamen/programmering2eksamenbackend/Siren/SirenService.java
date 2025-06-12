package eksamen.programmering2eksamenbackend.Siren;

import java.util.List;

public interface SirenService {

    // henter alle sirener
    List<SirenDTO> findAllSirens();

    // henter sirener ud fra id
    SirenDTO findSirenById(int id);

    // opretter en ny sirene
    SirenDTO createSiren(SirenDTO sirenDTO);

    // opdaterer en sirene
    SirenDTO updateSiren(int id, SirenDTO sirenDTO);

    // sletter en sirene
    void deleteSiren(int id);


}

