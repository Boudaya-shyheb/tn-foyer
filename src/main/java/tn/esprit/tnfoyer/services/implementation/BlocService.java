package tn.esprit.tnfoyer.services.implementation;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.dto.BlocDTO;
import tn.esprit.tnfoyer.entities.Bloc;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.mapper.BlocMapper;
import tn.esprit.tnfoyer.repositories.BlocRepository;
import tn.esprit.tnfoyer.repositories.ChambreRepository;
import tn.esprit.tnfoyer.services.interfaces.IBlocService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlocService implements IBlocService {

    private final BlocRepository blocRepository;
    private final ChambreRepository chambreRepository;
    private final BlocMapper blocMapper;

    public BlocService(BlocRepository blocRepository, ChambreRepository chambreRepository, BlocMapper blocMapper) {
        this.blocRepository = blocRepository;
        this.chambreRepository = chambreRepository;
        this.blocMapper = blocMapper;
    }

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public String updateBloc(Bloc bloc) {
        Bloc existingBloc= blocRepository.getById(bloc.getIdBloc());
        if(existingBloc!=null){
            blocRepository.save(bloc);
            return "successfully updated";
        }else{
            return "not found";
        }
    }

    @Override
    public String deleteBloc(Bloc bloc) {
        Bloc existingBloc= blocRepository.getById(bloc.getIdBloc());
        if(existingBloc!=null){
            blocRepository.delete(existingBloc);
            return "successfully updated";
        }else{
            return "not found";
        }
    }

    @Override
    public Bloc getBloc(Long idBloc) {
        Bloc existingBloc = blocRepository.getById(idBloc);
        if (existingBloc != null) {
            return existingBloc;
        }else {
            return null;
        }
    }

    @Override
    public List<Bloc> getAllBloc() {
        List blocs=blocRepository.findAll();
        return blocs;
    }

    @Override
    @Transactional
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc){

        Bloc b = blocRepository.findById(idBloc).get();

        List<Chambre> chambres = new ArrayList<>();
        for (Long i : numChambre ){
            Chambre c = chambreRepository.findChambreByNumeroChambre(i);
            if(c!=null){
                c.setBloc(b);
                chambres.add(c);
            }
        }
        b.setChambres(chambres);

        return blocRepository.save(b);

    }

    @Override
    public BlocDTO addOrUpdateBloc(BlocDTO blocDTO) {
        Bloc bloc = blocMapper.toEntity(blocDTO);

        Bloc savedBloc = blocRepository.save(bloc);
        return blocMapper.toDto(savedBloc);
    }

    @Override
    public List<BlocDTO> findAllBlocs() {
        return blocRepository.findAll()
                .stream()
                .map(blocMapper::toDto) // Conversion automatique en DTO
                .collect(Collectors.toList());
    }

    @Override
    public BlocDTO findById(long idBloc) {
        Bloc bloc = blocRepository.findById(idBloc)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec l'ID : " + idBloc));
        return blocMapper.toDto(bloc);
    }

}
