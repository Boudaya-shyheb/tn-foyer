package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Foyer;
import tn.esprit.tnfoyer.services.implementation.FoyerService;

import java.util.List;

@RestController
@RequestMapping("/foyers")
public class FoyerController {

    private final FoyerService foyerService;

    public FoyerController(FoyerService foyerService) {
        this.foyerService = foyerService;
    }

    @PostMapping
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @PutMapping
    public String updateFoyer(@RequestBody Foyer foyer) {
        return foyerService.updateFoyer(foyer);
    }

    @DeleteMapping
    public String deleteFoyer(@RequestBody Foyer foyer) {
        return foyerService.deleteFoyer(foyer);
    }

    @GetMapping("/{id}")
    public Object getFoyer(@PathVariable("id") long id) {
        return foyerService.getFoyer(id);
    }

    @GetMapping
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyer();
    }

    @PostMapping("/ajouter-et-affecter")
    public Foyer ajouterFoyerEtAffecterAUniversite(@RequestBody Foyer foyer,
                                                   @RequestParam("idUniversite") long idUniversite) {
        return foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);
    }
}
