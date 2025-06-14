package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;
import eksamen.programmering2eksamenbackend.Siren.SirenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FireMapper {

    @Autowired
    private SirenMapper sirenMapper;

    // Konvertering fra Model til DTO
    public FireDTO toDTO(FireModel model) {
        if (model == null) return null;

        FireDTO dto = new FireDTO();
        dto.setId(model.getId());
        dto.setLatitude(model.getLatitude());
        dto.setLongitude(model.getLongitude());
        dto.setStatus(model.getStatus());
        dto.setReportedAt(model.getReportedAt());
        dto.setClosedAt(model.getClosedAt());

        // Konverter sirener med sirenMapper
        List<SirenDTO> sirenDTOs = model.getSirens()
                .stream()
                .map(sirenMapper::toDTO)
                .collect(Collectors.toList());
        dto.setActivatedSirens(sirenDTOs);

        return dto;
    }

}
