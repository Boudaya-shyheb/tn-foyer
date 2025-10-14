package tn.esprit.tnfoyer.services;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.repositories.UniversiteRepository;

@Service
public class UniversiteService {

    private final UniversiteRepository universiteRepository;

    public UniversiteService(UniversiteRepository universiteRepository) {
        this.universiteRepository = universiteRepository;
    }
}
