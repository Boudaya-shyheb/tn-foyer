package tn.esprit.tnfoyer.services;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.repositories.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
