package eksamen.programmering2eksamenbackend.Fire;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fires")
@CrossOrigin("x")

public class FireController {

    @GetMapping
    public ResponseEntity<List<FireDTO>> getFires(@RequestParam(required = false) String status){

    }


    @PutMapping("/{id}/closure")
    public ResponseEntity<FireDTO> closeFire(@PathVariable Long id){

    }
}

