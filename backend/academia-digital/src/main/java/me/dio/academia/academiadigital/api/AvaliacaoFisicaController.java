package me.dio.academia.academiadigital.api;

import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaForm;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaUpdateForm;
import me.dio.academia.academiadigital.service.impl.AvaliacaoFisicaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {
    private final AvaliacaoFisicaServiceImpl avaliacaoFisicaService;

    public AvaliacaoFisicaController(AvaliacaoFisicaServiceImpl service) {
        this.avaliacaoFisicaService = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AvaliacaoFisicaForm form) {
        try {
            AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaService.create(form);
            return ResponseEntity.ok().body(avaliacaoFisica);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            List<AvaliacaoFisica> avaliacoes = avaliacaoFisicaService.getAll();
            return ResponseEntity.ok().body(avaliacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaService.findById(id);
            return ResponseEntity.ok().body(avaliacaoFisica);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AvaliacaoFisicaUpdateForm formUpdate) {
        try {
            AvaliacaoFisica avaliacaoFisica = avaliacaoFisicaService.update(id, formUpdate);
            return ResponseEntity.ok().body(avaliacaoFisica);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            avaliacaoFisicaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
