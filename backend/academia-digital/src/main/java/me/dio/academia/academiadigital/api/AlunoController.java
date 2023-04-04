package me.dio.academia.academiadigital.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
@OpenAPIDefinition
@Tag(name = "/alunos", description = "Grupo de API's para manipulação de dados de alunos")
public class AlunoController {

    private final AlunoServiceImpl alunoService;

    public AlunoController(AlunoServiceImpl alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> findAll(@RequestParam(value = "dataDeNascimento", required = false)
                               String dataDeNacimento){
        List<AlunoDTO> alunos = alunoService.findAll(dataDeNacimento);

        return ResponseEntity.ok(alunos);
    }

    @Operation(description = "API para buscar alunos por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno OK da Lista de alunos"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> findById(@PathVariable("id") Long id) {
        Optional<AlunoDTO> aluno = alunoService.findById(id);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        }
    }

    // adiciona vários alunos
    @Operation(description = "API para inserir dados de um aluno no banco")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping
    public ResponseEntity<List<AlunoDTO>> insert(@Valid @RequestBody List<AlunoDTO> alunosDTO) {
        List<AlunoForm> alunosForm = alunosDTO.stream().map(AlunoDTO::toForm).collect(Collectors.toList());
        List<AlunoDTO> insertedAlunosDTO = alunoService.insert(alunosForm);
        List<Long> ids = insertedAlunosDTO.stream().map(AlunoDTO::getId).collect(Collectors.toList());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ids}")
                .buildAndExpand(ids).toUri();
        return ResponseEntity.created(uri).body(insertedAlunosDTO);
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
