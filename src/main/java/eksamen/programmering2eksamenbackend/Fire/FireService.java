package eksamen.programmering2eksamenbackend.Fire;


import java.util.List;

public interface FireService {

    // finder alle brande
    List<FireDTO> findAllFires();

    // finder aktive brande
    List<FireDTO> findActiveFires();

    // lukker en brand baseret på id
    void closeFire(int fireId);

    // rapporterer om en ny brand og starter sirener indenfor 10 kom
    FireDTO reportFire(double latitude, double longitude);

}
