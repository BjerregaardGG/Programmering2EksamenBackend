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

    // finder alle sirener og konverterer til DTO'er
    @Override
    public List<SirenDTO> findAllSirens() {

        // henter alle sirener
        List<SirenModel> sirenEntities = sirenRepository.findAll();

        // konverterer til DTO'er
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

    // finder sirener baseret på id
    @Override
    public SirenDTO findSirenById(int id) {

        // henter en specifik sirene på ID
        SirenModel siren = sirenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Siren not found"));

        // konverterer sirenen til en DTO
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

    // opretter en ny sirene
    @Override
    public SirenDTO createSiren(SirenDTO sirenDTO) {

        // Sæt default værdier hvis de ikke er sat
        if (sirenDTO.getLastActivated() == null) {
            sirenDTO.setLastActivated(LocalDateTime.now());
        }

        // gemmer SirenModel ud fra sirenDTO værdier
        SirenModel siren = new SirenModel();
        siren.setName(sirenDTO.getName());
        siren.setLatitude(sirenDTO.getLatitude());
        siren.setLongitude(sirenDTO.getLongitude());
        siren.setStatus(sirenDTO.getStatus());
        siren.setDisabled(sirenDTO.isDisabled());
        siren.setLastActivated(sirenDTO.getLastActivated());

        // gemmer SirenModel i database og gemmer det nye objekt til returnering
        SirenModel newSiren = sirenRepository.save(siren);

        // returner (konverter) en ny SirenDto ud fra det nye SirenModel objekt
        return new SirenDTO(newSiren);

    }

    // opdaterer en sirene ud fra id og SirenDTO
    @Override
    public SirenDTO updateSiren(int id, SirenDTO sirenDTO) {

        SirenModel siren = sirenRepository.findById(sirenDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Siren not found with id" + id));

        // håndterer null værdier
        if (sirenDTO.getName() != null) {
            siren.setName(sirenDTO.getName());
        }
        siren.setLatitude(sirenDTO.getLatitude());
        siren.setLongitude(sirenDTO.getLongitude());

        // håndterer null værdier
        if (sirenDTO.getStatus() != null) {
            siren.setStatus(sirenDTO.getStatus());
        }

        siren.setDisabled(sirenDTO.isDisabled());
        siren.setLastActivated(sirenDTO.getLastActivated());

        // gemmer SirenModel i database og gemmer det nye objekt til returnering
        SirenModel updatedSiren = sirenRepository.save(siren);
        return new SirenDTO(updatedSiren);

    }

    // sletter en sirene på ID
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

    // sætter sirene statussen til ALARM for fire
    public void activateSirensForFire(FireModel fire) {

        // henter alle sirener for den specifikke fire
        List<SirenModel> sirens = fire.getSirens();
        for (SirenModel siren : sirens) {
            siren.setStatus(SirenStatus.ALARM); // sætter alarmerne til
        }

        sirenRepository.saveAll(sirens);
    }

}
