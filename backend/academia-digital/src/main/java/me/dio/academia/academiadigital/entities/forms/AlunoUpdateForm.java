package me.dio.academia.academiadigital.entities.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoUpdateForm {

    private String nome;

    private String bairro;

    private LocalDate dataDeNascimento;
}
