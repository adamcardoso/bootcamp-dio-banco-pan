package me.dio.academia.academiadigital.service.impl;

import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AlunoForm;
import me.dio.academia.academiadigital.entities.forms.AlunoUpdateForm;
import me.dio.academia.academiadigital.infra.utils.JavaTimeUtils;
import me.dio.academia.academiadigital.repositories.AlunoRepository;
import me.dio.academia.academiadigital.service.IAlunoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoServiceImpl implements IAlunoService {


    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.alunoRepository = repository;
    }

    @Override
    public Aluno create(AlunoForm form) {
        Aluno aluno = new Aluno();
        aluno.setNome(form.getNome());
        aluno.setCpf(form.getCpf());
        aluno.setBairro(form.getBairro());
        aluno.setDataDeNascimento(form.getDataDeNascimento());

        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno get(Long id) {
        return null;
    }

    @Override
    public List<Aluno> getAll(String dataDeNascimento) {

        if(dataDeNascimento == null) {
            return alunoRepository.findAll();
        } else {
            LocalDate localDate = LocalDate.parse(dataDeNascimento, JavaTimeUtils.LOCAL_DATE_FORMATTER);
            return alunoRepository.findByDataDeNascimento(localDate);
        }

    }

    @Override
    public Aluno update(Long id, AlunoUpdateForm formUpdate) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    /*
    * Using Optional.get() without first checking if the optional is present with isPresent() is generally considered a bad practice
    * because it can result in a NoSuchElementException being thrown at runtime if the optional is empty.
    * It is always better to first check if the optional has a value using isPresent() before calling get().
    */
    @Override
    public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(id);
        if (optionalAluno.isPresent()) {
            Aluno aluno = optionalAluno.get();
            return aluno.getAvaliacoesFisicas();
        } else {
            // handle the case where the Aluno is not found
            return Collections.emptyList();
        }
    }
}
