package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.dto.ChambreDTO;
import tn.esprit.tnfoyer.entities.*;
import tn.esprit.tnfoyer.mapper.ChambreMapper;
import tn.esprit.tnfoyer.repositories.ChambreRepository;
import tn.esprit.tnfoyer.repositories.FoyerRepository;
import tn.esprit.tnfoyer.repositories.ReservationRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IChambreService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChambreService implements IChambreService {

    private final ChambreRepository chambreRepository;
    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;
    private final ChambreMapper chambreMapper;
    private final ReservationRepository reservationRepository;


    public ChambreService(ChambreRepository chambreRepository, UniversiteRepository universiteRepository, FoyerRepository foyerRepository, ChambreMapper chambreMapper, ReservationRepository reservationRepository) {
        this.chambreRepository = chambreRepository;
        this.universiteRepository = universiteRepository;
        this.foyerRepository = foyerRepository;
        this.chambreMapper = chambreMapper;
        this.reservationRepository = reservationRepository;
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
        List<Chambre> result = chambreRepository.findChambresByBlocFoyerUniversiteNomUniversite(nomUniversite);
        return result;
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {

        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }

    @Override
    @Transactional
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {

        List<Chambre> chambres = chambreRepository.findChambresByBlocFoyerNomFoyerAndTypeC(nomFoyer, type);

        List<Reservation> res = reservationRepository.findAll();

        for (Chambre c : chambres) {
            for (Reservation r : res) {
                if (c.getIdChambre() == r.getChambre().getIdChambre()) {
                    chambres.remove(c);
                }
            }
        }
        return chambres;
    }

    @Override
    public ChambreDTO addOrUpdateChambre(ChambreDTO chambreDTO) {
        Chambre chambre = chambreMapper.toEntity(chambreDTO);
        Chambre saved = chambreRepository.save(chambre);
        return chambreMapper.toDto(saved);
    }

    @Override
    public List<ChambreDTO> findAllChambres() {
        return chambreRepository.findAll()
                .stream()
                .map(chambreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChambreDTO findById(long idChambre) {
        Chambre chambre = chambreRepository.findById(idChambre)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
        return chambreMapper.toDto(chambre);
    }

    @Override
    public Chambre findByCinEtudiant(long cinEtudiant){
        return chambreRepository.findChambreByReservationsEtudiantsCin(cinEtudiant);
    }

    @Override
    public Map<TypeChambre, Long> countChambresParTypeDansUniversite(String nomUniversite) {
        Map<TypeChambre, Long> result = new EnumMap<>(TypeChambre.class);
        for (Object[] row : chambreRepository.countChambresByTypeInUniversite(nomUniversite)) {
            TypeChambre type = (TypeChambre) row[0];
            Long total = (Long) row[1];
            result.put(type, total);
        }
        return result;
    }

    @Override
    public List<Chambre> getChambresSansReservationValide() {
        return chambreRepository.findChambresSansReservationValide();
    }

}
