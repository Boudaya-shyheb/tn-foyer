package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.ReservationDTO;
import tn.esprit.tnfoyer.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface IReservationService {

    public Reservation addReservation(Reservation reservation);
    public String updateReservation(Reservation reservation);
    public String deleteReservation(Reservation reservation);
    public Object getReservation(String idReservation);
    public List<Reservation> getAllReservation();
    public Reservation ajouterReservation(long idBloc, long cinEtudiant);
    Reservation annulerReservation(long cinEtudiant);
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(Date anneeUniversitaire, String nomUniversite);
    ReservationDTO addOrUpdateReservation(ReservationDTO reservationDTO);
    List<ReservationDTO> findAllReservations();
    ReservationDTO findById(String idReservation);

}
