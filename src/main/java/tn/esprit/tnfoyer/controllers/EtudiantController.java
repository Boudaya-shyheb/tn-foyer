package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Etudiant;
import tn.esprit.tnfoyer.services.implementation.EtudiantService;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @PutMapping
    public String updateEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.updateEtudiant(etudiant);
    }

    @DeleteMapping
    public String deleteEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.deleteEtudiant(etudiant);
    }

    @GetMapping("/{id}")
    public Object getEtudiant(@PathVariable("id") long id) {
        return etudiantService.getEtudiant(id);
    }

    @GetMapping
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiant();
    }
}
