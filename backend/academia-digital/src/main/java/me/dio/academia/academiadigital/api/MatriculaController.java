package me.dio.academia.academiadigital.api;

import me.dio.academia.academiadigital.entities.Matricula;
import me.dio.academia.academiadigital.entities.forms.MatriculaForm;
import me.dio.academia.academiadigital.service.impl.MatriculaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaServiceImpl matriculaService;

    public MatriculaController(MatriculaServiceImpl service) {
        this.matriculaService = service;
    }

    @PostMapping
    public Matricula create(@Valid @RequestBody MatriculaForm form) {
        return matriculaService.create(form);
    }

    @GetMapping
    public List<Matricula> getAll(@RequestParam(value = "bairro", required = false) String bairro) {
        return matriculaService.getAll(bairro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Long id) {
        Matricula matricula = matriculaService.findById(id);
        return ResponseEntity.ok(matricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
