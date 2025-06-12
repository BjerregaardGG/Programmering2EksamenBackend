package eksamen.programmering2eksamenbackend.Siren;


import eksamen.programmering2eksamenbackend.Fire.FireModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sirens")
public class SirenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private SirenStatus status; // FARE, FRED

    private boolean disabled; // true = ude af drift
    private LocalDateTime lastActivated;

    @ManyToMany(mappedBy = "sirens")
    private List<FireModel> fires = new ArrayList<>();

    public SirenModel() {}

    public SirenModel(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = SirenStatus.PEACE;
        this.disabled = false;
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

    public List<FireModel> getFires() {
        return fires;
    }

    public void setFires(List<FireModel> fires) {
        this.fires = fires;
    }
}

