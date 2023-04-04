package me.dio.academia.academiadigital.controller;

import me.dio.academia.academiadigital.dto.AlunoDTO;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AlunoForm;
import me.dio.academia.academiadigital.entities.forms.AlunoUpdateForm;
import me.dio.academia.academiadigital.service.impl.AlunoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoServiceImpl alunoService;

    public AlunoController(AlunoServiceImpl service) {
        this.alunoService = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll(@RequestParam(value = "dataDeNascimento", required = false)
                               String dataDeNacimento){
        List<AlunoDTO> alunos = alunoService.findAll(dataDeNacimento);

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable("id") Long id) {
        Optional<AlunoDTO> aluno = alunoService.findById(id);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> insert(@Valid @RequestBody AlunoDTO alunoDTO) {
        AlunoForm alunoForm = alunoDTO.toForm(); // convert AlunoDTO to AlunoForm
        AlunoDTO insertedAlunoDTO = alunoService.insert(alunoForm);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(insertedAlunoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(insertedAlunoDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoDTO> update(@PathVariable Long id, @Valid @RequestBody AlunoDTO dto) {
        AlunoUpdateForm form = dto.toUpdateForm();
        dto = alunoService.update(id, form);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/avaliacoes/{id}")
    public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(@PathVariable Long id) {
        return alunoService.getAllAvaliacaoFisicaId(id);
    }
}
