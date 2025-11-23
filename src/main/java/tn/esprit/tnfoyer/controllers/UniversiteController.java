package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Universite;
import tn.esprit.tnfoyer.services.implementation.UniversiteService;

import java.util.List;

@RestController
@RequestMapping("/universites")
public class UniversiteController {

    private final UniversiteService universiteService;

    public UniversiteController(UniversiteService universiteService) {
        this.universiteService = universiteService;
    }

    @PostMapping
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }

    @PutMapping
    public String updateUniversite(@RequestBody Universite universite) {
        return universiteService.updateUniversite(universite);
    }

    @DeleteMapping
    public String deleteUniversite(@RequestBody Universite universite) {
        return universiteService.deleteUniversite(universite);
    }

    @GetMapping("/{id}")
    public Object getUniversite(@PathVariable("id") long id) {
        return universiteService.getUniversite(id);
    }

    @GetMapping
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversite();
    }

    @PutMapping("/affecter-foyer")
    public Universite affecterFoyerAUniversite(@RequestParam("idFoyer") long idFoyer,
                                               @RequestParam("nomUniversite") String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
    }

    @PutMapping("/desaffecter-foyer/{idUniversite}")
    public Universite desaffecterFoyerAUniversite(@PathVariable("idUniversite") long idUniversite) {
        return universiteService.desaffecterFoyerAUniversite(idUniversite);
    }
}
