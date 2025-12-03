package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.entities.TypeChambre;
import tn.esprit.tnfoyer.entities.Universite;

import java.util.List;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {



    Chambre findChambreByNumeroChambre(long numeroChambre);

    List<Chambre> findByBlocIdBlocAndTypeC(long idBloc, TypeChambre typeC);

    @Query("select c from Chambre c where c.bloc.idBloc = :idBloc and c.typeC = :typeC")
    List<Chambre> findChambresByBlocAndTypeJPQL(long idBloc, TypeChambre typeC);


    List<Chambre> findChambresByBlocIdBloc(long blocIdBloc);

    List<Chambre> findChambresByBlocFoyerUniversiteNomUniversite(String nomUniversite);

    List<Chambre> findChambresByBlocFoyerNomFoyerAndTypeC(String nomFoyer, TypeChambre typeC);

    @Query(value = "select c from Chambre c INNER JOIN c.reservation r INNER JOIN r.etudiants e where e.cin = :cin", nativeQuery = true)
    Chambre findChambreByReservationsEtudiantsCin(@Param("cin") long cin);

    @Query("select c.typeC, count(c) " +
            "from Chambre c " +
            "join c.bloc b " +
            "join b.foyer f " +
            "join f.universite u " +
            "where u.nomUniversite = :nomUniversite " +
            "group by c.typeC")
    List<Object[]> countChambresByTypeInUniversite(@Param("nomUniversite") String nomUniversite);

    @Query("select distinct c " +
            "from Chambre c " +
            "left join c.reservations r " +
            "group by c " +
            "having sum(case when r.estValide = true then 1 else 0 end) = 0")
    List<Chambre> findChambresSansReservationValide();


}
