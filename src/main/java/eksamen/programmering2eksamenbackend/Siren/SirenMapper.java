package eksamen.programmering2eksamenbackend.Siren;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SirenMapper {

    public SirenDTO toDTO(SirenModel model) {
        if (model == null) return null;

        SirenDTO dto = new SirenDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setLatitude(model.getLatitude());
        dto.setLongitude(model.getLongitude());
        dto.setStatus(model.getStatus());
        dto.setDisabled(model.isDisabled());
        dto.setLastActivated(model.getLastActivated());
        return dto;
    }

    public SirenModel toModel(SirenDTO dto) {
        if (dto == null) return null;

        SirenModel model = new SirenModel();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setLatitude(dto.getLatitude());
        model.setLongitude(dto.getLongitude());
        model.setStatus(dto.getStatus());
        model.setDisabled(dto.isDisabled());
        model.setLastActivated(dto.getLastActivated());
        return model;
    }

    public List<SirenDTO> toDTOList(List<SirenModel> models) {
        return models.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SirenModel> toModelList(List<SirenDTO> dtos) {
        return dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }


}
