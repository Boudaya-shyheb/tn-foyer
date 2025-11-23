package tn.esprit.tnfoyer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.entities.TypeChambre;

import java.util.List;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {



    Chambre findChambreByNumeroChambre(long numeroChambre);

    List<Chambre> findByBlocIdBlocAndTypeC(long idBloc, TypeChambre typeC);

    @Query("select c from Chambre c where c.bloc.idBloc = :idBloc and c.typeC = :typeC")
    List<Chambre> findChambresByBlocAndTypeJPQL(long idBloc, TypeChambre typeC);



}
