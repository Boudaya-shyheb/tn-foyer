package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tnfoyer.services.UniversiteService;

@RestController
@RequestMapping("/universites")
public class UniversiteController {

    private final UniversiteService universiteService;

    public UniversiteController(UniversiteService universiteService) {
        this.universiteService = universiteService;
    }
}
