package tn.esprit.tnfoyer.services;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.repositories.BlocRepository;

@Service
public class BlocService {

    private final BlocRepository blocRepository;

    public BlocService(BlocRepository blocRepository) {
        this.blocRepository = blocRepository;
    }
}
