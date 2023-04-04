package me.dio.academia.academiadigital.service.impl.exceptions;

public class AvaliacaoFisicaNotFoundException extends RuntimeException {

    public AvaliacaoFisicaNotFoundException(Long id) {
        super("Avaliação Física não encontrada com o id: " + id);
    }

}
