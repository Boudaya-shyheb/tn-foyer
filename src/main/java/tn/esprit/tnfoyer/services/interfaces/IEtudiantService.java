package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.EtudiantDTO;
import tn.esprit.tnfoyer.entities.Etudiant;

import java.time.LocalDate;
import java.util.List;

public interface IEtudiantService {

    public Etudiant addEtudiant(Etudiant etudiant);
    public String updateEtudiant(Etudiant etudiant);
    public String deleteEtudiant(Etudiant etudiant);
    public Object getEtudiant(Long idEtudiant);
    public List<Etudiant> getAllEtudiant();
    EtudiantDTO addOrUpdateEtudiant(EtudiantDTO etudiantDTO);
    List<EtudiantDTO> findAllEtudiants();
    EtudiantDTO findById(long idEtudiant);
    List<Etudiant> getEtudiantsAvecReservationValideeParAnnee(LocalDate annee);
    List<Etudiant> getEtudiantsSansReservations();

}
