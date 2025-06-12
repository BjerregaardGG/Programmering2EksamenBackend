package eksamen.programmering2eksamenbackend.Siren;

import java.time.LocalDateTime;
import java.util.Date;

public class SirenDTO {

    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private SirenStatus status;
    private boolean disabled;
    private LocalDateTime lastActivated;

    public SirenDTO(){}

    public SirenDTO(int id, String name, double latitude, double longitude, SirenStatus status, boolean disabled, LocalDateTime lastActivated) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.disabled = disabled;
        this.lastActivated = lastActivated;
    }
}
