package tn.esprit.tnfoyer.services;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.repositories.FoyerRepository;

@Service
public class FoyerService {

    private final FoyerRepository foyerRepository;

    public FoyerService(FoyerRepository foyerRepository) {
        this.foyerRepository = foyerRepository;
    }
}
