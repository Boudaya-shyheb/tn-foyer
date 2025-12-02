package tn.esprit.tnfoyer.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class ReservationDTO {
    private String idReservation;
    private Date anneeUniversitaire;
    private boolean estValide;

    private List<Long> etudiantIds;
}
