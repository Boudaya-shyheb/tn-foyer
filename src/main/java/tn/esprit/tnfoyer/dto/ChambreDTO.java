package tn.esprit.tnfoyer.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tnfoyer.entities.TypeChambre;

@Data
public class ChambreDTO {
    private Long idChambre;
    private Long numeroChambre;
    private TypeChambre typec;

    private Long blocId; // Id du bloc associé
}
