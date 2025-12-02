package tn.esprit.tnfoyer.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
public class FoyerDTO {
    Long idFoyer;
    String nomFoyer;
    Long capaciteFoyer;
    Long universiteId;
    List<Long> blocIds;
}
