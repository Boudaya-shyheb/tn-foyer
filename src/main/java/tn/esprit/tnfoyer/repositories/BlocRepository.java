package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Bloc;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
}
