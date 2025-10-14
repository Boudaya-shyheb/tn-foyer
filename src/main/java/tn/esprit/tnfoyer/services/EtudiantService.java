package tn.esprit.tnfoyer.services;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.repositories.EtudiantRepository;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }
}
