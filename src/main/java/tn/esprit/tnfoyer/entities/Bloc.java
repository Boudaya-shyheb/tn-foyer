package tn.esprit.tnfoyer.entities;

import jakarta.persistence.*;

import java.nio.MappedByteBuffer;
import java.util.List;

@Entity
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBloc;

    private String nomBloc;

    private int capaciteBloc;

    @OneToMany( mappedBy = "bloc", cascade = CascadeType.ALL)
    private List<Chambre> chambres;

}
