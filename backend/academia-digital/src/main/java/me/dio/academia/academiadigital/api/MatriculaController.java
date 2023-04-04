package me.dio.academia.academiadigital.api;

import me.dio.academia.academiadigital.dto.MatriculaDTO;
import me.dio.academia.academiadigital.entities.Matricula;
import me.dio.academia.academiadigital.entities.forms.MatriculaForm;
import me.dio.academia.academiadigital.service.impl.MatriculaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaServiceImpl matriculaService;

    public MatriculaController(MatriculaServiceImpl service) {
        this.matriculaService = service;
    }

    @PostMapping
    public MatriculaDTO create(@Valid @RequestBody MatriculaForm form) {
        Matricula matricula = matriculaService.create(form);
        return new MatriculaDTO(matricula);
    }

    @GetMapping
    public List<MatriculaDTO> getAll(@RequestParam(value = "bairro", required = false) String bairro) {
        List<Matricula> matriculas = matriculaService.getAll(bairro);
        return matriculas.stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaDTO> findById(@PathVariable Long id) {
        Matricula matricula = matriculaService.findById(id);
        return ResponseEntity.ok(new MatriculaDTO(matricula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
