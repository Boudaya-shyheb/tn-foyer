package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.ChambreDTO;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.entities.TypeChambre;

import java.util.List;
import java.util.Map;

public interface IChambreService {

    public Chambre addChambre(Chambre chambre);
    public String updateChambre(Chambre chambre);
    public String deleteChambre(Chambre chambre);
    public Object getChambre(Long idChambre);
    public List<Chambre> getAllChambre();
    public List<Chambre> getChambresParNomUniversite(String nomUniversite);
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC);
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomUniversite, TypeChambre type);
    ChambreDTO addOrUpdateChambre(ChambreDTO chambreDTO);
    List<ChambreDTO> findAllChambres();
    ChambreDTO findById(long idChambre);
    Chambre findByCinEtudiant(long cinEtudiant);
    Map<TypeChambre, Long> countChambresParTypeDansUniversite(String nomUniversite);
    List<Chambre> getChambresSansReservationValide();

}
