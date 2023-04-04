package me.dio.academia.academiadigital.controller;

import me.dio.academia.academiadigital.entities.Matricula;
import me.dio.academia.academiadigital.entities.forms.MatriculaForm;
import me.dio.academia.academiadigital.service.impl.MatriculaServiceImpl;
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

}
