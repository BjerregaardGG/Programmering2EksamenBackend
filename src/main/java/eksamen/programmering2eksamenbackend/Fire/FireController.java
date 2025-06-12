package eksamen.programmering2eksamenbackend.Fire;

import eksamen.programmering2eksamenbackend.Siren.SirenDTO;
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

    @GetMapping("")
    public ResponseEntity<List<FireDTO>> getActiveFires(@RequestParam(required = false) String status){
        try {
            List<FireDTO> fires;

            if ("closed".equalsIgnoreCase(status)) {
                fires = fireService.findClosedFires();
            } else if ("active".equalsIgnoreCase(status) || status == null) {
                fires = fireService.findActiveFires();
            } else {
                return ResponseEntity.badRequest().build(); // ukendt status
            }

            return ResponseEntity.ok(fires);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

   @PostMapping
   public ResponseEntity<FireDTO> createFire(@RequestBody FireDTO fire) {
       try {
           FireDTO fireDTO = fireService.reportFire(fire.getLatitude(), fire.getLongitude());

           return ResponseEntity.ok(fireDTO);

       } catch (Exception e) {
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



