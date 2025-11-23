package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.*;
import tn.esprit.tnfoyer.repositories.ChambreRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IChambreService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChambreService implements IChambreService {

    private final ChambreRepository chambreRepository;
    private final UniversiteRepository universiteRepository;


    public ChambreService(ChambreRepository chambreRepository, UniversiteRepository universiteRepository) {
        this.chambreRepository = chambreRepository;
        this.universiteRepository = universiteRepository;
    }

    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public String updateChambre(Chambre chambre) {
        Chambre existing = chambreRepository.getById(chambre.getIdChambre());
        if (existing != null) {
            chambreRepository.save(chambre);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteChambre(Chambre chambre) {
        Chambre existing = chambreRepository.getById(chambre.getIdChambre());
        if (existing != null) {
            chambreRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getChambre(Long idChambre) {
        Chambre existing = chambreRepository.findById(idChambre).get();
        if (existing != null) {
            return existing;
        } else {
            return "Chambre not found";
        }
    }

    @Override
    public List<Chambre> getAllChambre() {
        return chambreRepository.findAll();
    }

    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null || universite.getFoyer() == null) {
            return List.of();
        }
        Foyer foyer = universite.getFoyer();
        if (foyer.getBlocs() == null) {
            return List.of();
        }
        List<Chambre> result = new ArrayList<>();
        for (Bloc bloc : foyer.getBlocs()) {
            if (bloc.getChambres() != null) {
                result.addAll(bloc.getChambres());
            }
        }
        return result;
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {

        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
        // return chambreRepository.findChambresByBlocAndTypeJPQL(idBloc, typeC);

    }

    @Override
    @Transactional
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(String nomUniversite, TypeChambre type) {
        if (nomUniversite == null || nomUniversite.isBlank() || type == null) return List.of();

        Universite u = universiteRepository.findByNomUniversite(nomUniversite);
        if (u == null || u.getFoyer() == null || u.getFoyer().getBlocs() == null) return List.of();

        LocalDate anneeCourante = LocalDate.now();
        List<Chambre> res = new ArrayList<>();

        for (Bloc b : u.getFoyer().getBlocs()) {
            if (b.getChambres() == null) continue;
            for (Chambre ch : b.getChambres()) {
                if (ch.getTypeC() != type) continue;

                boolean aReservationValideCetteAnnee = ch.getReservations() != null
                        && ch.getReservations().stream()
                        .anyMatch(r -> r.isEstValide()
                                && r.getAnneeUniversitaire() != null
                                && r.getAnneeUniversitaire().isEqual(anneeCourante));

                if (!aReservationValideCetteAnnee) {
                    res.add(ch);
                }
            }
        }
        return res;
    }

}
