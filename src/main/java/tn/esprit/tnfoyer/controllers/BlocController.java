package tn.esprit.tnfoyer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tnfoyer.entities.Bloc;
import tn.esprit.tnfoyer.services.implementation.BlocService;

import java.util.List;

@Tag(name = "Gestion Blocs")
@RestController
@RequestMapping("/blocs")
public class BlocController {

    private final BlocService blocService;

    public BlocController(BlocService blocService) {
        this.blocService = blocService;
    }

    @Operation(summary = "Ajouter un bloc")
    @PostMapping
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

    @PutMapping
    public String updateBloc(@RequestBody Bloc bloc) {
        return blocService.updateBloc(bloc);
    }

    @DeleteMapping
    public String deleteBloc(@RequestBody Bloc bloc) {
        return blocService.deleteBloc(bloc);
    }

    @GetMapping("/{id}")
    public Object getBloc(@PathVariable("id") long id) {
        return blocService.getBloc(id);
    }

    @GetMapping
    public List<Bloc> getAllBlocs() {
        return blocService.getAllBloc();
    }

    @PostMapping("/affecter-chambres")
    public Bloc affecterChambresABloc(@RequestBody List<Long> numerosChambres,
                                      @RequestParam("idBloc") long idBloc) {
        return blocService.affecterChambresABloc(numerosChambres, idBloc);
    }
}
