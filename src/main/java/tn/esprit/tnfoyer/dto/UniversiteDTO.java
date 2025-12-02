package tn.esprit.tnfoyer.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
public class UniversiteDTO {
    Long idUniversite;
    String nomUniversite;
    String adresse;
    Long foyerId;
}
