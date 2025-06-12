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

    public SirenDTO(int id, double latitude, String name, double longitude, SirenStatus status, boolean disabled, LocalDateTime lastActivated) {
        this.id = id;
        this.latitude = latitude;
        this.name = name;
        this.longitude = longitude;
        this.status = status;
        this.disabled = disabled;
        this.lastActivated = lastActivated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public SirenStatus getStatus() {
        return status;
    }

    public void setStatus(SirenStatus status) {
        this.status = status;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public LocalDateTime getLastActivated() {
        return lastActivated;
    }

    public void setLastActivated(LocalDateTime lastActivated) {
        this.lastActivated = lastActivated;
    }
}
