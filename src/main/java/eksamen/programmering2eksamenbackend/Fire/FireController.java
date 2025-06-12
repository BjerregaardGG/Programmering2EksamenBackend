package eksamen.programmering2eksamenbackend.Fire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fires")
@CrossOrigin("x")

public class FireController {

    private final FireServiceImpl fireService;

    @Autowired
    FireController(FireServiceImpl fireService){
        this.fireService = fireService;
    }

    @GetMapping
    public ResponseEntity<List<FireDTO>> getFires(){
        try {
            List<FireDTO> fires = fireService.findAllFires();
            return ResponseEntity.ok(fires);

        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}/closure")
    public ResponseEntity<FireDTO> closeFire(@PathVariable Long id){
        return null;

    }
}

