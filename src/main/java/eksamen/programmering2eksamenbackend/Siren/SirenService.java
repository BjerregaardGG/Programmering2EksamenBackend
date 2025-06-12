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

    // finder en sirene inden for en given radius af et punkt
    List<SirenDTO> findSirensWithinRadius(double latitude, double longitude, double radiusKm);

    // aktiverer en liste af sirener
    void activateSirens(List<SirenDTO> sirens);

    // deaktiverer en liste af sirener
    void deactivateSirens(List<SirenDTO> sirens);

    // finder aktive sirener
    List<SirenDTO> findActiveSirens();

    // tjekker om en sirene kan aktiveres
    boolean canActivateSiren(int id);
}

