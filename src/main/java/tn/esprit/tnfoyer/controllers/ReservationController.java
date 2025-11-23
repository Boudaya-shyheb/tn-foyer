package tn.esprit.tnfoyer.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Reservation;
import tn.esprit.tnfoyer.services.implementation.ReservationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @PutMapping
    public String updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    @DeleteMapping
    public String deleteReservation(@RequestBody Reservation reservation) {
        return reservationService.deleteReservation(reservation);
    }

    @GetMapping("/{id}")
    public Object getReservation(@PathVariable("id") String id) {
        return reservationService.getReservation(id);
    }

    @GetMapping
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }

    @PostMapping("/ajouter")
    public Reservation ajouterReservation(@RequestParam("idBloc") long idBloc,
                                          @RequestParam("cin") long cinEtudiant) {
        return reservationService.ajouterReservation(idBloc, cinEtudiant);
    }

    @PutMapping("/annuler")
    public Reservation annulerReservation(@RequestParam("cin") long cinEtudiant) {
        return reservationService.annulerReservation(cinEtudiant);
    }

    @GetMapping("/par-annee-et-universite")
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            @RequestParam("annee") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date anneeUniversitaire,
            @RequestParam("nomUniversite") String nomUniversite) {
        return reservationService.getReservationParAnneeUniversitaireEtNomUniversite(anneeUniversitaire, nomUniversite);
    }
}
