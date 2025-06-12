package eksamen.programmering2eksamenbackend.Siren;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SirenServiceImpl {

        public List<SirenDTO> findAllSirens() {
            return null;
        }

        public SirenDTO findSirenById(int id) {
            return null;
        }
        public SirenDTO createSiren(SirenDTO sirenDTO){
            return null;
        }

        public SirenDTO updateSiren(int id, SirenDTO sirenDTO){
            return null;
        }

        public void deleteSiren(int id){
        }

        public List<SirenDTO> findSirensWithinRadius(double latitude, double longitude, double radiusKm){
            return null;
        }

        public void activateSirens(List<SirenDTO> sirens){

        }
        public void deactivateSirens(List<SirenDTO> sirens){
        }

}
