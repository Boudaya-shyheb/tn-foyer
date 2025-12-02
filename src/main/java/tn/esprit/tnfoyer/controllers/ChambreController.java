package tn.esprit.tnfoyer.controllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Chambre;
import tn.esprit.tnfoyer.entities.TypeChambre;
import tn.esprit.tnfoyer.services.implementation.ChambreService;

import java.util.List;

@RestController
@RequestMapping("/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    @PostMapping
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreService.addChambre(chambre);
    }

    @PutMapping
    public String updateChambre(@RequestBody Chambre chambre) {
        return chambreService.updateChambre(chambre);
    }

    @DeleteMapping
    public String deleteChambre(@RequestBody Chambre chambre) {
        return chambreService.deleteChambre(chambre);
    }

    @GetMapping("/{id}")
    public Object getChambre(@PathVariable("id") long id) {
        return chambreService.getChambre(id);
    }

    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambre();
    }

    @GetMapping("/par-universite")
    public List<Chambre> getChambresParNomUniversite(@RequestParam("nomUniversite") String nomUniversite) {
        return chambreService.getChambresParNomUniversite(nomUniversite);
    }

    @GetMapping("/par-bloc-et-type")
    public List<Chambre> getChambresParBlocEtType(@RequestParam("idBloc") long idBloc,
                                                  @RequestParam("type") TypeChambre typeC) {
        return chambreService.getChambresParBlocEtType(idBloc, typeC);
    }

    @GetMapping("/non-reservees")
    public List<Chambre> getChambresReserveParNomFoyerEtTypeChambre(@RequestParam("nomFoyer") String nomUniversite,
                                                                            @RequestParam("type") TypeChambre type) {
        return chambreService.getChambresNonReserveParNomFoyerEtTypeChambre(nomUniversite, type);
    }
}
