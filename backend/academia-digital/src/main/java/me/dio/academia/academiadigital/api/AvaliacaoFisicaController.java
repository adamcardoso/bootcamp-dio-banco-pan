package me.dio.academia.academiadigital.api;

import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaForm;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaUpdateForm;
import me.dio.academia.academiadigital.service.impl.AvaliacaoFisicaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {
    private final AvaliacaoFisicaServiceImpl avaliacaoFisicaService;

    public AvaliacaoFisicaController(AvaliacaoFisicaServiceImpl service) {
        this.avaliacaoFisicaService = service;
    }

    @PostMapping
    public AvaliacaoFisica create(@RequestBody AvaliacaoFisicaForm form) {
        return avaliacaoFisicaService.create(form);
    }

    @GetMapping
    public List<AvaliacaoFisica> getAll(){
        return avaliacaoFisicaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> findById(@PathVariable Long id) {
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaService.findById(id);
        return ResponseEntity.ok().body(avaliacaoFisica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoFisica> update(@PathVariable Long id, @RequestBody @Valid AvaliacaoFisicaUpdateForm formUpdate) {
        AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaService.update(id, formUpdate);
        return ResponseEntity.ok().body(avaliacaoFisica);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        avaliacaoFisicaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
