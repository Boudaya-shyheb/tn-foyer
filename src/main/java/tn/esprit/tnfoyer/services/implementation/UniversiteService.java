package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.Foyer;
import tn.esprit.tnfoyer.entities.Universite;
import tn.esprit.tnfoyer.repositories.FoyerRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IUniversiteService;

import java.util.List;

@Service
public class UniversiteService implements IUniversiteService {

    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;

    public UniversiteService(UniversiteRepository universiteRepository, FoyerRepository foyerRepository ) {
        this.universiteRepository = universiteRepository;
        this.foyerRepository = foyerRepository;
    }

    @Override
    public Universite addUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    @Transactional
    public String updateUniversite(Universite universite) {
        Universite existing = universiteRepository.getById(universite.getIdUniversite());
        if (existing != null) {
            universiteRepository.save(universite);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteUniversite(Universite universite) {
        Universite existing = universiteRepository.getById(universite.getIdUniversite());
        if (existing != null) {
            universiteRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getUniversite(Long idUniversite) {
        Universite existing = universiteRepository.findById(idUniversite).get();
        if (existing != null) {
            return existing;
        } else {
            return "Universite not found";
        }
    }

    @Override
    public List<Universite> getAllUniversite() {
        return universiteRepository.findAll();
    }

    @Override
    @Transactional
    public Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) {

        Foyer f = foyerRepository.findById(idFoyer).get();
        Universite u = universiteRepository.findByNomUniversite(nomUniversite);

        f.setUniversite(u);
        u.setFoyer(f);
        return universiteRepository.save(u);

    }

    @Override
    @Transactional
    public Universite desaffecterFoyerAUniversite (long idUniversite) {

        Universite u = universiteRepository.findById(idUniversite).get();

        Foyer f = u.getFoyer();
        f.setUniversite(null);
        u.setFoyer(null);

        return universiteRepository.save(u);
    }


}
