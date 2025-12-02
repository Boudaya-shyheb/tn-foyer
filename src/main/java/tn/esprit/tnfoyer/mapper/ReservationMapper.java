package tn.esprit.tnfoyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.ReservationDTO;
import tn.esprit.tnfoyer.entities.Reservation;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {


    @Mapping(target = "etudiantIds", expression = "java(mapEtudiants(reservation))")
    ReservationDTO toDto(Reservation reservation);


    @Mapping(target = "etudiants", ignore = true) // on ignore pour simplifier le mapping inverse
    Reservation toEntity(ReservationDTO dto);


    default List<Long> mapEtudiants(Reservation reservation) {
        if (reservation.getEtudiants() == null) return null;
        return (List<Long>) reservation.getEtudiants()
                .stream()
                .map(e -> e.getIdEtudiant())
                .collect(Collectors.toSet());
    }
}
