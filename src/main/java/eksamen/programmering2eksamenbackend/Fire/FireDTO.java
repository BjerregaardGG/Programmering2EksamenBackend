package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;

import java.time.LocalDateTime;
import java.util.List;

public class FireDTO {

    private int id;
    private double latitude;
    private double longitude;
    private LocalDateTime reportedAt;
    private FireStatus status;
    private LocalDateTime closedAt;
    private List<SirenDTO> activatedSirens;

    public FireDTO(){

    }

    public FireDTO(int id, double latitude, double longitude, LocalDateTime reportedAt, FireStatus status, LocalDateTime closedAt, List<SirenDTO> activatedSirens) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reportedAt = reportedAt;
        this.status = status;
        this.closedAt = closedAt;
        this.activatedSirens = activatedSirens;
    }
}
