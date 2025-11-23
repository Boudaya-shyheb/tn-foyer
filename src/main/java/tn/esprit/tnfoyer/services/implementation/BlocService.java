package tn.esprit.tnfoyer.services.implementation;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.entities.Bloc;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.repositories.BlocRepository;
import tn.esprit.tnfoyer.repositories.ChambreRepository;
import tn.esprit.tnfoyer.services.interfaces.IBlocService;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlocService implements IBlocService {

    private final BlocRepository blocRepository;
    private final ChambreRepository chambreRepository;

    public BlocService(BlocRepository blocRepository, ChambreRepository chambreRepository) {
        this.blocRepository = blocRepository;
        this.chambreRepository = chambreRepository;
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

}
