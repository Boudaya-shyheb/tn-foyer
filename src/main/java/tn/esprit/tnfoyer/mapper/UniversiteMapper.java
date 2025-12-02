package tn.esprit.tnfoyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.UniversiteDTO;
import tn.esprit.tnfoyer.entities.Universite;


@Mapper(componentModel = "spring")
public interface UniversiteMapper {

    // Mapping Entity → DTO
    @Mapping(target = "foyerId", source = "foyer.idFoyer")
    UniversiteDTO toDto(Universite universite);

    // Mapping DTO → Entity
    @Mapping(target = "foyer.idFoyer", source = "foyerId")
    Universite toEntity(UniversiteDTO universiteDTO);
}
