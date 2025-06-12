package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;

import java.time.LocalDateTime;
import java.util.List;

public class FireDTO {

    private int id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime reportedAt;
    private boolean closed;
    private LocalDateTime closedAt;
    private List<SirenDTO> activatedSirens;

}
