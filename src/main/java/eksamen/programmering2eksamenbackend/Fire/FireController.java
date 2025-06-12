package eksamen.programmering2eksamenbackend.Fire;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fires")
@CrossOrigin(origins = "http://localhost:63342")

public class FireController {

    private final FireServiceImpl fireService;

    @Autowired
    FireController(FireServiceImpl fireService){
        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<List<FireDTO>> getAllFires(){
        try {
            List<FireDTO> fires = fireService.findAllFires();
            return ResponseEntity.ok(fires);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<FireDTO>> getActiveFires(){
        try {
            List<FireDTO> fires = fireService.findActiveFires();
            return ResponseEntity.ok(fires);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}/closure")
    public ResponseEntity<Void> closeFire(@PathVariable int id){
        try{
            fireService.closeFire(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



