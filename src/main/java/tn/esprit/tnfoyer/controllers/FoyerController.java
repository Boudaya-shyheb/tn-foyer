package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tnfoyer.services.FoyerService;

@RestController
@RequestMapping("/foyers")
public class FoyerController {

    private final FoyerService foyerService;

    public FoyerController(FoyerService foyerService) {
        this.foyerService = foyerService;
    }
}
