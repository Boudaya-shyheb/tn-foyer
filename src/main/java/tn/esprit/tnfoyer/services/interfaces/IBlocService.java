package tn.esprit.tnfoyer.services.interfaces;

import tn.esprit.tnfoyer.dto.BlocDTO;
import tn.esprit.tnfoyer.entities.Bloc;

import java.util.List;

public interface IBlocService {

    public Bloc addBloc(Bloc bloc);
    public String updateBloc(Bloc bloc);
    public String deleteBloc(Bloc bloc);
    public Object getBloc(Long idBloc);
    public List<Bloc> getAllBloc();
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) ;
    BlocDTO addOrUpdateBloc(BlocDTO blocDTO);
    List<BlocDTO> findAllBlocs();
    BlocDTO findById(long idBloc);

}
