package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
