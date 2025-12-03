package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.dto.FoyerDTO;
import tn.esprit.tnfoyer.entities.Bloc;
import tn.esprit.tnfoyer.entities.Foyer;
import tn.esprit.tnfoyer.entities.Universite;
import tn.esprit.tnfoyer.mapper.FoyerMapper;
import tn.esprit.tnfoyer.repositories.BlocRepository;
import tn.esprit.tnfoyer.repositories.FoyerRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IFoyerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoyerService implements IFoyerService {

    private final FoyerRepository foyerRepository;
    private final BlocRepository blocRepository;
    private final UniversiteRepository universiteRepository;
    private final FoyerMapper foyerMapper;

    public FoyerService(FoyerRepository foyerRepository, BlocRepository blocRepository, UniversiteRepository universiteRepository, FoyerMapper foyerMapper) {
        this.foyerRepository = foyerRepository;
        this.blocRepository = blocRepository;
        this.universiteRepository = universiteRepository;
        this.foyerMapper = foyerMapper;
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
        Universite universite = universiteRepository.findById(idUniversite).orElseThrow();

        List<Bloc> blocsInput = foyer.getBlocs();
        if (blocsInput != null) {
            List<Bloc> blocsManaged = new java.util.ArrayList<>(blocsInput.size());
            for (Bloc b : blocsInput) {
                if (b.getIdBloc() == 0) {
                    b.setFoyer(foyer);
                    blocsManaged.add(b);
                } else {
                    Bloc bm = blocRepository.findById(b.getIdBloc()).get();
                    bm.setFoyer(foyer);
                    blocsManaged.add(bm);
                }
            }
            foyer.setBlocs(blocsManaged);
        }

        Foyer savedFoyer = foyerRepository.save(foyer);

        universite.setFoyer(savedFoyer);
        universiteRepository.save(universite);

        return savedFoyer;
    }

    @Override
    public FoyerDTO addOrUpdateFoyer(FoyerDTO foyerDTO) {
        Foyer foyer = foyerMapper.toEntity(foyerDTO);

        Foyer saved = foyerRepository.save(foyer);
        return foyerMapper.toDto(saved);
    }

    @Override
    public List<FoyerDTO> findAllFoyers() {
        return foyerRepository.findAll()
                .stream()
                .map(foyerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoyerDTO findById(long idFoyer) {
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new RuntimeException("Foyer introuvable avec ID : " + idFoyer));
        return foyerMapper.toDto(foyer);
    }

    @Override
    public List<Foyer> findFoyerAvecPlusDeChambres() {
        return foyerRepository.findFoyerWithMostChambres();
    }

}
