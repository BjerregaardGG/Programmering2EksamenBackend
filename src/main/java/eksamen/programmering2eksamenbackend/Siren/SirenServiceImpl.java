package eksamen.programmering2eksamenbackend.Siren;

import eksamen.programmering2eksamenbackend.Fire.FireModel;
import eksamen.programmering2eksamenbackend.Fire.FireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SirenServiceImpl implements SirenService {

    @Autowired
    SirenRepository sirenRepository;

    @Autowired
    FireRepository fireRepository;

    @Override
    public List<SirenDTO> findAllSirens() {
        List<SirenModel> sirenEntities = sirenRepository.findAll();

        List<SirenDTO> dtos = new ArrayList<>();
        for (SirenModel siren : sirenEntities) {
            SirenDTO dto = new SirenDTO();
            dto.setId(siren.getId());
            dto.setName(siren.getName());
            dto.setLatitude(siren.getLatitude());
            dto.setLongitude(siren.getLongitude());
            dto.setStatus(siren.getStatus());
            dto.setDisabled(siren.isDisabled());
            dto.setLastActivated(siren.getLastActivated());
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public SirenDTO findSirenById(int id) {
        SirenModel siren = sirenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Siren not found"));

        SirenDTO sirenDTO = new SirenDTO();
        sirenDTO.setId(siren.getId());
        sirenDTO.setName(siren.getName());
        sirenDTO.setLatitude(siren.getLatitude());
        sirenDTO.setLongitude(siren.getLongitude());
        sirenDTO.setStatus(siren.getStatus());
        sirenDTO.setDisabled(siren.isDisabled());
        sirenDTO.setLastActivated(siren.getLastActivated());

        return sirenDTO;

    }

    @Override
    public SirenDTO createSiren(SirenDTO sirenDTO) {

        // Sæt default værdier hvis de ikke er sat
        if (sirenDTO.getLastActivated() == null) {
            sirenDTO.setLastActivated(LocalDateTime.now());
        }

        // gem SireModel ud fra sirenDTO værdier
        SirenModel siren = new SirenModel();
        siren.setName(sirenDTO.getName());
        siren.setLatitude(sirenDTO.getLatitude());
        siren.setLongitude(sirenDTO.getLongitude());
        siren.setStatus(sirenDTO.getStatus());
        siren.setDisabled(sirenDTO.isDisabled());
        siren.setLastActivated(sirenDTO.getLastActivated());

        // gem SirenModel i database og gem det nye objekt
        SirenModel newSiren = sirenRepository.save(siren);

        // returner (konverter) en ny SirenDto ud fra det nye SirenModel objekt
        return new SirenDTO(newSiren);

    }

    @Override
    public SirenDTO updateSiren(int id, SirenDTO sirenDTO) {

        SirenModel siren = sirenRepository.findById(sirenDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Siren not found with id" + id));

        if (sirenDTO.getName() != null) {
            siren.setName(sirenDTO.getName());
        }
        siren.setLatitude(sirenDTO.getLatitude());
        siren.setLongitude(sirenDTO.getLongitude());

        if (sirenDTO.getStatus() != null) {
            siren.setStatus(sirenDTO.getStatus());
        }

        siren.setDisabled(sirenDTO.isDisabled());
        siren.setLastActivated(sirenDTO.getLastActivated());

        SirenModel updatedSiren = sirenRepository.save(siren);
        return new SirenDTO(updatedSiren);

    }

    @Override
    public void deleteSiren(int id) {
        SirenModel siren = sirenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Siren not found"));

        // itterer over sirener i Firemodellen og sletter sirenerne, hvilket automatisk opdaterer join
        for (FireModel fire : siren.getFires()) {
            fire.getSirens().remove(siren);
            fireRepository.save(fire);
        }

        sirenRepository.deleteById(id);
    }

    @Override
    public List<SirenDTO> findSirensWithinRadius(double latitude, double longitude, double radiusKm) {
        return List.of();
    }

    @Override
    public void activateSirens(List<SirenDTO> sirens) {

    }

    @Override
    public void deactivateSirens(List<SirenDTO> sirens) {

    }

    @Override
    public List<SirenDTO> findActiveSirens() {
        return List.of();
    }

    @Override
    public boolean canActivateSiren(int id) {
        return false;
    }

}
