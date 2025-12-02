package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.UniversiteDTO;
import tn.esprit.tnfoyer.entities.Universite;

import java.util.List;

public interface IUniversiteService {

    public Universite addUniversite(Universite universite);
    public String updateUniversite(Universite universite);
    public String deleteUniversite(Universite universite);
    public Object getUniversite(Long idUniversite);
    public List<Universite> getAllUniversite();
    public Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) ;
    public Universite desaffecterFoyerAUniversite (long idUniversite) ;
    UniversiteDTO addOrUpdateUniversite(UniversiteDTO universiteDTO);
    List<UniversiteDTO> findAllUniversites();
    UniversiteDTO findById(long idUniversite);

}
