package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Etudiant;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    Etudiant findEtudiantByCin(long cin);

    @Query("select distinct e " +
            "from Etudiant e " +
            "join e.reservations r " +
            "where r.estValide = true " +
            "and r.anneeUniversitaire = :annee")
    List<Etudiant> findEtudiantsWithValidReservationInYear(@Param("annee") LocalDate annee);

    @Query("select e from Etudiant e left join e.reservations r where r is null")
    List<Etudiant> findEtudiantsSansReservations();


}
