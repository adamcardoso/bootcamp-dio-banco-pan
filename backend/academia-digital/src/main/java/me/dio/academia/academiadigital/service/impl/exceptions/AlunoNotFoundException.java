package me.dio.academia.academiadigital.service.impl.exceptions;

public class AlunoNotFoundException extends RuntimeException {
    public AlunoNotFoundException() {
        super("Aluno não encontrado");
    }
}

