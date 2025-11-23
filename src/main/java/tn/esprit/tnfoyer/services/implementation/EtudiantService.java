package tn.esprit.tnfoyer.services.implementation;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.Etudiant;
import tn.esprit.tnfoyer.repositories.EtudiantRepository;
import tn.esprit.tnfoyer.services.interfaces.IEtudiantService;

import java.util.List;

@Service
public class EtudiantService implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public String updateEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.getById(etudiant.getIdEtudiant());
        if (existing != null) {
            etudiantRepository.save(etudiant);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.getById(etudiant.getIdEtudiant());
        if (existing != null) {
            etudiantRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getEtudiant(Long idEtudiant) {
        Etudiant existing = etudiantRepository.findById(idEtudiant).get();
        if (existing != null) {
            return existing;
        } else {
            return "Etudiant not found";
        }
    }

    @Override
    public List<Etudiant> getAllEtudiant() {
        return etudiantRepository.findAll();
    }
}
