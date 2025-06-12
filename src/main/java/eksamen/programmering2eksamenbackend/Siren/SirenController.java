package eksamen.programmering2eksamenbackend.Siren;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sirens")
@CrossOrigin("x")
public class SirenController {

    @GetMapping
    public ResponseEntity<List<SirenDTO>> getAllSirens(){

    }

    @GetMapping("/{id}")
    public ResponseEntity<SirenDTO> getSirenById(@PathVariable Long id){

    }

    @PostMapping
    public ResponseEntity<SirenDTO> createSiren(@RequestBody SirenDTO sirenDTO){

    }

    @PutMapping("/{id}")
    public ResponseEntity<SirenDTO> updateSiren(@PathVariable Long id, @RequestBody SirenDTO sirenDTO){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiren(@PathVariable Long id){

    }
}


}
