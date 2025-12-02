package tn.esprit.tnfoyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.ChambreDTO;
import tn.esprit.tnfoyer.entities.Chambre;


@Mapper(componentModel = "spring")
public interface ChambreMapper {

    // Entité → DTO
    @Mapping(target = "blocId", source = "bloc.idBloc")
    ChambreDTO toDto(Chambre chambre);

    // DTO → Entité
    @Mapping(target = "bloc.idBloc", source = "blocId")
    Chambre toEntity(ChambreDTO dto);
}
