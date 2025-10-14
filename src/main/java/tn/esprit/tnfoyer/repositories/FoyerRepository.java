package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Foyer;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {
}
