//package org.esprit.tpfoyer.dto;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class EtudiantDTO {
//    private Long idEtudiant;
//    private String nomEt;
//    private String prenomEt;
//    private Long cin;
//    private String ecole;
//    private Date dateNaissance;
//}


package tn.esprit.tnfoyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class EtudiantDTO {
    private Long idEtudiant;
    private String nomEt;
    private String prenomEt;
    private Long cin;
    private String ecole;
    private Date dateNaissance;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> reservationIds;
}
