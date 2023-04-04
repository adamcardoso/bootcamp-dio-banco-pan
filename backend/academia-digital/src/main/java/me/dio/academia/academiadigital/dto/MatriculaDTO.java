package me.dio.academia.academiadigital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dio.academia.academiadigital.entities.Matricula;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private AlunoDTO aluno;
    private LocalDateTime dataDaMatricula;

    public MatriculaDTO(Matricula matricula) {
        this.id = matricula.getId();
        this.aluno = new AlunoDTO(matricula.getAluno());
        this.dataDaMatricula = matricula.getDataDaMatricula();
    }
}

