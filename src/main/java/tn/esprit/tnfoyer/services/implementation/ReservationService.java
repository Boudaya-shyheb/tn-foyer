package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.dto.ReservationDTO;
import tn.esprit.tnfoyer.entities.*;
import tn.esprit.tnfoyer.mapper.ReservationMapper;
import tn.esprit.tnfoyer.repositories.*;
import tn.esprit.tnfoyer.services.interfaces.IReservationService;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final BlocRepository blocRepository;
    private final EtudiantRepository etudiantRepository;
    private final UniversiteRepository universiteRepository;
    private final ReservationMapper reservationMapper;
    private final ChambreRepository chambreRepository;

    public ReservationService(ReservationRepository reservationRepository, BlocRepository blocRepository, EtudiantRepository etudiantRepository, UniversiteRepository universiteRepository, ReservationMapper reservationMapper, ChambreRepository chambreRepository) {
        this.reservationRepository = reservationRepository;
        this.blocRepository = blocRepository;
        this.etudiantRepository = etudiantRepository;
        this.universiteRepository = universiteRepository;
        this.reservationMapper = reservationMapper;
        this.chambreRepository = chambreRepository;
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public String updateReservation(Reservation reservation) {
        Reservation existing = reservationRepository.getById(reservation.getIdReservation());
        if (existing != null) {
            reservationRepository.save(reservation);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteReservation(Reservation reservation) {
        Reservation existing = reservationRepository.getById(reservation.getIdReservation());
        if (existing != null) {
            reservationRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getReservation(String idReservation) {
        Reservation existing = reservationRepository.findById(idReservation).get();
        if (existing != null) {
            return existing;
        } else {
            return "Reservation not found";
        }
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation ajouterReservation(long idBloc, long cinEtudiant) {
        Bloc bloc = blocRepository.findById(idBloc).get();
        List<Chambre> chambres = chambreRepository.findChambresByBlocIdBloc(idBloc);
        if (bloc == null || chambres.isEmpty()) {
            throw new IllegalArgumentException("Bloc introuvable ou sans chambres");
        }

        Etudiant etudiant = etudiantRepository.findEtudiantByCin(cinEtudiant);
        if (etudiant == null) {
            throw new IllegalArgumentException("Etudiant introuvable avec cin=" + cinEtudiant);
        }


        Chambre chambre = null;
        for (Chambre ch : chambres) {
            int capaciteMax = switch (ch.getTypeC()) {
                case SIMPLE -> 1;
                case DOUBLE -> 2;
                case TRIPLE -> 3;
            };
            int nbOccupants = (ch.getReservations() == null) ? 0 : ch.getReservations().stream()
                    .filter(Reservation::isEstValide)
                    .map(Reservation::getEtudiants)
                    .mapToInt(lst -> lst == null ? 0 : lst.size())
                    .sum();
            if (nbOccupants < capaciteMax) {
                chambre = ch;
                break;
            }
        }

        if (chambre == null) {
            throw new IllegalStateException("Capacité maximale atteinte pour toutes les chambres du bloc");
        }

        LocalDate anneeUniversitaire = LocalDate.now();
        String numReservation = chambre.getNumeroChambre() + "-" + bloc.getNomBloc() + "-" + anneeUniversitaire;

        Reservation reservation = reservationRepository.findById(numReservation).orElse(null);
        if (reservation == null) {
            reservation = new Reservation();
            reservation.setIdReservation(numReservation);
            reservation.setAnneeUniversitaire(anneeUniversitaire);
            reservation.setEstValide(true);
            reservation.setChambre(chambre);
        }

        List<Etudiant> etudiants = reservation.getEtudiants() == null ? new ArrayList<>() : new ArrayList<>(reservation.getEtudiants());
        if (etudiants.stream().noneMatch(e -> e.getIdEtudiant() == etudiant.getIdEtudiant())) {
            etudiants.add(etudiant);
        }
        reservation.setEtudiants(etudiants);

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation annulerReservation(long cinEtudiant) {

       Reservation reservation =  reservationRepository.findReservationByEtudiantsCin(cinEtudiant);

        reservation.setEstValide(false);

        if (reservation.getEtudiants() != null) {
            List<Etudiant> it = reservation.getEtudiants();
            for (Etudiant e : it) {
                if (e.getIdEtudiant() == e.getIdEtudiant()) {
                    it.remove(e);
                    break;
                }
            }
        }

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversitaire, String nomUniversite) {

        List<Chambre> chambres = chambreRepository.findChambresByBlocFoyerUniversiteNomUniversite(nomUniversite);
        List<Reservation> res = reservationRepository.findAll();
        for (Reservation r : res) {
            for (Chambre c : chambres) {
                if (r.getChambre().getIdChambre() != c.getIdChambre() && anneeUniversitaire.getYear() != r.getAnneeUniversitaire().getYear() && !r.isEstValide()) {
                    res.remove(r);
                }
            }
        }

        return res;
    }

    @Override
    public ReservationDTO addOrUpdateReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDto(saved);
    }

    @Override
    public List<ReservationDTO> findAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO findById(String idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        return reservationMapper.toDto(reservation);
    }

}
