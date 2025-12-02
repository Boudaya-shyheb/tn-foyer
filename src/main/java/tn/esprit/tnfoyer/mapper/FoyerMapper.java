package tn.esprit.tnfoyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.FoyerDTO;
import tn.esprit.tnfoyer.entities.Foyer;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FoyerMapper {

    // Mapping Entity → DTO
    @Mapping(target = "universiteId", source = "universite.idUniversite")
    @Mapping(target = "blocIds", expression = "java(mapBlocs(foyer))")
    FoyerDTO toDto(Foyer foyer);

    // Mapping DTO → Entity
    @Mapping(target = "universite.idUniversite", source = "universiteId")
    @Mapping(target = "blocs", ignore = true) // on ignore pour simplifier
    Foyer toEntity(FoyerDTO dto);

    // Méthode utilitaire pour récupérer les IDs des blocs
    default List<Long> mapBlocs(Foyer foyer) {
        if (foyer.getBlocs() == null) return null;
        return (List<Long>) foyer.getBlocs().stream()
                .map(bloc -> bloc.getIdBloc())
                .collect(Collectors.toSet());
    }
}
