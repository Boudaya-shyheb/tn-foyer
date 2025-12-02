package tn.esprit.tnfoyer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;



@Entity
public class Reservation {

        @Id
        private String idReservation;

        @Temporal(TemporalType.DATE)
        private LocalDate anneeUniversitaire;

        private boolean estValide;

        @ManyToMany
        private List<Etudiant> etudiants;

        @ManyToOne
        private Chambre chambre;

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public LocalDate getAnneeUniversitaire() {
        return anneeUniversitaire;
    }

    public void setAnneeUniversitaire(LocalDate anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public boolean isEstValide() {
        return estValide;
    }

    public void setEstValide(boolean estValide) {
        this.estValide = estValide;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation='" + idReservation + '\'' +
                ", anneeUniversitaire=" + anneeUniversitaire +
                ", estValide=" + estValide +
                ", etudiants=" + etudiants +
                '}';
    }
}


