package eksamen.programmering2eksamenbackend.Siren;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import eksamen.programmering2eksamenbackend.Siren.SirenDTO;

import java.util.List;


@RestController
@RequestMapping("/sirens")
@CrossOrigin(origins = "http://localhost:63342")
public class SirenController {

    private final SirenServiceImpl sirenService;
    private final SirenServiceImpl sirenServiceImpl;

    @Autowired
    public SirenController(SirenServiceImpl sirenService, SirenServiceImpl sirenServiceImpl){
        this.sirenService = sirenService;
        this.sirenServiceImpl = sirenServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<SirenDTO>> getAllSirens(){
        try {
            List<SirenDTO> sirens = sirenService.findAllSirens();
            return ResponseEntity.ok(sirens);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SirenDTO> getSirenById(@PathVariable int id) {
        SirenDTO siren;
        try {
            siren = sirenService.findSirenById(id);
            return ResponseEntity.ok(siren);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<SirenDTO> createSiren(@RequestBody SirenDTO siren){
        try {
            sirenService.createSiren(siren);
            return ResponseEntity.ok(siren);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<SirenDTO> updateSiren (@PathVariable int id, @RequestBody SirenDTO sirenDTO){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiren (@PathVariable int id) {
        try {
            sirenService.deleteSiren(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
