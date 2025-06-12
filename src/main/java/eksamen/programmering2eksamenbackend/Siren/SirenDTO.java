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

    public SirenDTO(SirenModel model) {
        this.id = model.getId();
        this.name = model.getName();
        this.latitude = model.getLatitude();
        this.longitude = model.getLongitude();
        this.status = model.getStatus();
        this.disabled = model.isDisabled();
        this.lastActivated = model.getLastActivated();
    }

    public SirenDTO(String name, double latitude, double longitude, SirenStatus status, boolean disabled) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.disabled = disabled;
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
