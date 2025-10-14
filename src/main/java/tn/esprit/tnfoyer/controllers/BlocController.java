package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tnfoyer.services.BlocService;

@RestController
@RequestMapping("/blocs")
public class BlocController {

    private final BlocService blocService;

    public BlocController(BlocService blocService) {
        this.blocService = blocService;
    }
}
