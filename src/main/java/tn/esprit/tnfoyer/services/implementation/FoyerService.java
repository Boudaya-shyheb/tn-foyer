package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.Bloc;
import tn.esprit.tnfoyer.entities.Foyer;
import tn.esprit.tnfoyer.entities.Universite;
import tn.esprit.tnfoyer.repositories.BlocRepository;
import tn.esprit.tnfoyer.repositories.FoyerRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IFoyerService;

import java.util.List;

@Service
public class FoyerService implements IFoyerService {

    private final FoyerRepository foyerRepository;
    private final BlocRepository blocRepository;
    private final UniversiteRepository universiteRepository;

    public FoyerService(FoyerRepository foyerRepository, BlocRepository blocRepository, UniversiteRepository universiteRepository) {
        this.foyerRepository = foyerRepository;
        this.blocRepository = blocRepository;
        this.universiteRepository = universiteRepository;
    }

    @Override
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public String updateFoyer(Foyer foyer) {
        Foyer existing = foyerRepository.getById(foyer.getIdFoyer());
        if (existing != null) {
            foyerRepository.save(foyer);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteFoyer(Foyer foyer) {
        Foyer existing = foyerRepository.getById(foyer.getIdFoyer());
        if (existing != null) {
            foyerRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getFoyer(Long idFoyer) {
        Foyer existing = foyerRepository.findById(idFoyer).get();
        if (existing != null) {
            return existing;
        } else {
            return "Foyer not found";
        }
    }

    @Override
    public List<Foyer> getAllFoyer() {
        return foyerRepository.findAll();
    }

    @Override
    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).get();

        List<Bloc> blocs = foyer.getBlocs();
        if (blocs != null) {
            for (Bloc b : blocs) {
                b.setFoyer(foyer);
                blocRepository.save(b);
            }
        }

        foyer.setUniversite(universite);
        Foyer savedFoyer = foyerRepository.save(foyer);

        universite.setFoyer(savedFoyer);
        universiteRepository.save(universite);

        return savedFoyer;
    }

}
