package tn.esprit.tnfoyer.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tnfoyer.dto.BlocDTO;
import tn.esprit.tnfoyer.entities.Bloc;

@Mapper(componentModel = "spring")
public interface BlocMapper {

    // Mapping Entité → DTO
    @Mapping(target = "libelleBloc", source = "nomBloc")
    BlocDTO toDto(Bloc bloc);

    // Mapping DTO → Entité
    @Mapping(target = "nomBloc", source = "libelleBloc")
    Bloc toEntity(BlocDTO blocDTO);
}
