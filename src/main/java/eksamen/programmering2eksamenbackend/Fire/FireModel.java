package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FireModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double latitude;
    private double longitude;

    private LocalDateTime reportedAt = LocalDateTime.now(); // Automatisk timestamp
    private LocalDateTime closedAt; // Hvornår blev branden lukket

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FireStatus status = FireStatus.ACTIVE;

    @ManyToMany
    @JoinTable(
            name = "fire_siren",
            joinColumns = @JoinColumn(name = "fire_id"),
            inverseJoinColumns = @JoinColumn(name = "siren_id")
    )
    private List<SirenModel> sirens = new ArrayList<>();

    public FireModel() {}

    public FireModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.reportedAt = LocalDateTime.now();
        this.status = FireStatus.CLOSED;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public FireStatus getStatus() {
        return status;
    }

    public void setStatus(FireStatus status) {
        this.status = status;
    }

    public List<SirenModel> getSirens() {
        return sirens;
    }

    public void setSirens(List<SirenModel> sirens) {
        this.sirens = sirens;
    }
}
