package me.dio.academia.academiadigital.controller;

import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AlunoForm;
import me.dio.academia.academiadigital.service.impl.AlunoServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoServiceImpl alunoService;

    public AlunoController(AlunoServiceImpl service) {
        this.alunoService = service;
    }

    @PostMapping
    public Aluno create(@Valid @RequestBody AlunoForm form) {
        return alunoService.create(form);
    }

    @GetMapping("/avaliacoes/{id}")
    public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
        return alunoService.getAllAvaliacaoFisicaId(id);
    }

    @GetMapping
    public List<Aluno> getAll(@RequestParam(value = "dataDeNascimento", required = false)
                              String dataDeNacimento){
        return alunoService.getAll(dataDeNacimento);
    }


}
