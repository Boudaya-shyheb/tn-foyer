package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.*;
import tn.esprit.tnfoyer.repositories.BlocRepository;
import tn.esprit.tnfoyer.repositories.EtudiantRepository;
import tn.esprit.tnfoyer.repositories.ReservationRepository;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;
import tn.esprit.tnfoyer.services.interfaces.IReservationService;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final BlocRepository blocRepository;
    private final EtudiantRepository etudiantRepository;
    private final UniversiteRepository universiteRepository;

    public ReservationService(ReservationRepository reservationRepository, BlocRepository blocRepository, EtudiantRepository etudiantRepository, UniversiteRepository universiteRepository) {
        this.reservationRepository = reservationRepository;
        this.blocRepository = blocRepository;
        this.etudiantRepository = etudiantRepository;
        this.universiteRepository = universiteRepository;
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
        if (bloc == null || bloc.getChambres() == null || bloc.getChambres().isEmpty()) {
            throw new IllegalArgumentException("Bloc introuvable ou sans chambres");
        }

        Etudiant etudiant = etudiantRepository.findEtudiantByCin(cinEtudiant);
        if (etudiant == null) {
            throw new IllegalArgumentException("Etudiant introuvable avec cin=" + cinEtudiant);
        }


        Chambre chambre = null;
        for (Chambre ch : bloc.getChambres()) {
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

        System.out.println("1");

        Reservation reservation = new Reservation();
        reservation.setIdReservation(numReservation);
        reservation.setAnneeUniversitaire(anneeUniversitaire);
        reservation.setEstValide(true);

        System.out.println("2");

        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        reservation.setEtudiants(etudiants);

        System.out.println("3");

        if (chambre.getReservations() == null) {
            chambre.setReservations(new ArrayList<>());
        }
        chambre.getReservations().add(reservation);

        System.out.println("4");
        System.out.println(reservation.toString());

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

        LocalDate annee = anneeUniversitaire.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Universite u = universiteRepository.findByNomUniversite(nomUniversite);
        if (u == null || u.getFoyer() == null)
            return null;

        List<Reservation> res = new ArrayList<>();
        if (u.getFoyer().getBlocs() == null)
            return res;

        for (Bloc b : u.getFoyer().getBlocs()) {
            for (Chambre ch : b.getChambres()) {
                for (Reservation r : ch.getReservations()) {
                    if (r.isEstValide()
                            && r.getAnneeUniversitaire() != null
                            && r.getAnneeUniversitaire().isEqual(annee)) {
                        res.add(r);
                    }
                }
            }
        }
        return res;
    }
}
