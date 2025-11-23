package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Foyer;

import java.util.List;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {

    List<Foyer> findByBlocsNomBlocAndBlocsCapaciteBloc(String nomBloc, int capaciteBloc);


    Foyer findFoyerByNomFoyer(String nomFoyer);
}
