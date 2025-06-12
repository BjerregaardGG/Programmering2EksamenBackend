package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public FireDTO(FireModel fire) {
        this.id = fire.getId();
        this.latitude = fire.getLatitude();
        this.longitude = fire.getLongitude();
        this.reportedAt = fire.getReportedAt();
        this.status = fire.getStatus();
        this.closedAt = fire.getClosedAt();

        // Konverterer listen af SirenModel til SirenDTO
        this.activatedSirens = fire.getSirens().stream()
                .map(SirenDTO::new)
                .collect(Collectors.toList());
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

    public FireStatus getStatus() {
        return status;
    }

    public void setStatus(FireStatus status) {
        this.status = status;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public List<SirenDTO> getActivatedSirens() {
        return activatedSirens;
    }

    public void setActivatedSirens(List<SirenDTO> activatedSirens) {
        this.activatedSirens = activatedSirens;
    }
}
