package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.FoyerDTO;
import tn.esprit.tnfoyer.entities.Foyer;

import java.util.List;

public interface IFoyerService {

    public Foyer addFoyer(Foyer foyer);
    public String updateFoyer(Foyer foyer);
    public String deleteFoyer(Foyer foyer);
    public Object getFoyer(Long idFoyer);
    public List<Foyer> getAllFoyer();
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite);
    FoyerDTO addOrUpdateFoyer(FoyerDTO foyerDTO);
    List<FoyerDTO> findAllFoyers();
    FoyerDTO findById(long idFoyer);
    Foyer findFoyerAvecPlusDeChambres();
}
