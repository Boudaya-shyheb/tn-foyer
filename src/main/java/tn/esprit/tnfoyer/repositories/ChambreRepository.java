package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Chambre;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
