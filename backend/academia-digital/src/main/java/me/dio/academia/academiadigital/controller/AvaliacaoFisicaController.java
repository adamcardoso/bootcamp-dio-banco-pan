package me.dio.academia.academiadigital.controller;

import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaForm;
import me.dio.academia.academiadigital.service.impl.AvaliacaoFisicaServiceImpl;
import org.springframework.web.bind.annotation.*;

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

}
