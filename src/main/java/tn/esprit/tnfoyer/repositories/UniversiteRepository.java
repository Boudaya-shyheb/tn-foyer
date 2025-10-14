package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Universite;

@Repository
public interface UniversiteRepository extends JpaRepository<Universite, Long> {
}
