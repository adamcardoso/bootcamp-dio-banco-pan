package me.dio.academia.academiadigital.service.impl.exceptions;

public class MatriculaNotFoundException extends RuntimeException {
    public MatriculaNotFoundException() {
        super("Matricula não encontrada");
    }
}

