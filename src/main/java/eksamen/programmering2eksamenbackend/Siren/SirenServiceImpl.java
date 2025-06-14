package eksamen.programmering2eksamenbackend.Siren;

import eksamen.programmering2eksamenbackend.Fire.FireModel;
import eksamen.programmering2eksamenbackend.Fire.FireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SirenServiceImpl implements SirenService {

    @Autowired
    private SirenRepository sirenRepository;

    @Autowired
    private FireRepository fireRepository;

    @Autowired
    private SirenMapper sirenMapper;

    // !ÆNDRING! Finder alle sirener og konverterer til DTO'er
    @Override
    public List<SirenDTO> findAllSirens() {

        // henter alle sirener
        List<SirenModel> sirens = sirenRepository.findAll();

        // !ÆNDRING! kalder sirenMapper der konverterer til en liste af sirener
        return sirenMapper.toDTOList(sirens);
    }

    // !ÆNDRING! finder sirener baseret på id
    @Override
    public SirenDTO findSirenById(int id) {

        // henter en specifik sirene på ID
        SirenModel siren = sirenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Siren not found"));

        // !ÆNDRING! kalder toDTO i sirenMapper
        return sirenMapper.toDTO(siren);

    }

    // opretter en ny sirene
    @Override
    public SirenDTO createSiren(SirenDTO sirenDTO) {

        // Sæt default værdier hvis de ikke er sat
        if (sirenDTO.getLastActivated() == null) {
            sirenDTO.setLastActivated(LocalDateTime.now());
        }

        // !ÆNDRING! kalder toModel i sirenMapper
        SirenModel siren =  sirenMapper.toModel(sirenDTO);
        // gemmer SirenModel i database og gemmer det nye objekt til returnering
        SirenModel newSiren = sirenRepository.save(siren);

        // !ÆNDRING! kalder toDto i sirenMapper
        return sirenMapper.toDTO(newSiren);

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
        return sirenMapper.toDTO(updatedSiren);

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
