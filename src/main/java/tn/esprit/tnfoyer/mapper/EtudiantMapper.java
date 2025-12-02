package tn.esprit.tnfoyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.EtudiantDTO;
import tn.esprit.tnfoyer.entities.Etudiant;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EtudiantMapper {


    @Mapping(target = "reservationIds", expression = "java(mapReservations(etudiant))")
    EtudiantDTO toDto(Etudiant etudiant);


    @Mapping(target = "reservations", ignore = true)
    Etudiant toEntity(EtudiantDTO dto);


    default List<String> mapReservations(Etudiant etudiant) {
        if (etudiant == null || etudiant.getReservations() == null) {
            return null;
        }
        return (List<String>) etudiant.getReservations().stream()
                .map(r -> r.getIdReservation())
                .collect(Collectors.toSet());
    }
}
