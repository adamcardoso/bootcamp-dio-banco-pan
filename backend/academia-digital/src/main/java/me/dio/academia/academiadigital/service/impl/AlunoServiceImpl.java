package me.dio.academia.academiadigital.service.impl;

import me.dio.academia.academiadigital.dto.AlunoDTO;
import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AlunoForm;
import me.dio.academia.academiadigital.entities.forms.AlunoUpdateForm;
import me.dio.academia.academiadigital.repositories.AlunoRepository;
import me.dio.academia.academiadigital.service.IAlunoService;
import me.dio.academia.academiadigital.service.impl.exceptions.DatabaseException;
import me.dio.academia.academiadigital.service.impl.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AlunoServiceImpl implements IAlunoService {


    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.alunoRepository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlunoDTO> findAll(String dataDeNascimento) {
        Iterable<Aluno> alunos = alunoRepository.findAll();

        if (Objects.isNull(dataDeNascimento)) {
            return StreamSupport.stream(alunos.spliterator(), false)
                    .map(AlunoDTO::new)
                    .onClose(() -> {})
                    .collect(Collectors.toList());
        } else {
            LocalDate localDate = LocalDate.parse(dataDeNascimento, DateTimeFormatter.ISO_LOCAL_DATE);
            return alunoRepository.findByDataDeNascimento(localDate)
                    .stream()
                    .map(AlunoDTO::new)
                    .onClose(() -> {})
                    .collect(Collectors.toList());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlunoDTO> findById(Long id) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(id);

        return optionalAluno.map(AlunoDTO::new);
    }


    @Override
    public AlunoDTO update(Long id, AlunoUpdateForm formUpdate) {
        try {
            Optional<Aluno> optionalEntity = alunoRepository.findById(id);
            if (optionalEntity.isPresent()) {
                Aluno entity = optionalEntity.get();
                copyFormToEntity(formUpdate, entity); // copia os dados do formUpdate para o objeto Aluno
                entity = alunoRepository.save(entity); // atualiza o objeto Aluno no banco de dados
                return new AlunoDTO(entity);
            } else {
                throw new ResourceNotFoundException("Aluno não encontrado com id " + id);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Erro ao atualizar o aluno com id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            alunoRepository.deleteById(id); // exclui o aluno correspondente no banco de dados
            System.out.println("Aluno deletado com sucesso!");
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    /*
    * Using Optional.get() without first checking if the optional is present with isPresent() is generally considered a bad practice
    * because it can result in a NoSuchElementException being thrown at runtime if the optional is empty.
    * It is always better to first check if the optional has a value using isPresent() before calling get().
    */
    @Override
    public List<AvaliacaoFisica> getAllAvaliacaoFisicaId(Long id) {
        return alunoRepository.findById(id)
                .orElse(new Aluno())
                .getAvaliacoesFisicas();
    }


    @Override
    public List<AlunoDTO> insert(List<AlunoForm> forms) {
        List<Aluno> entities = new ArrayList<>();

        for (AlunoForm form : forms) {
            Aluno entity = new Aluno();
            copyDtoToEntity(form, entity);
            entities.add(entity);
        }

        List<Aluno> insertedEntities = alunoRepository.saveAll(entities);
        return insertedEntities.stream().map(AlunoDTO::new).collect(Collectors.toList());
    }


    private void copyFormToEntity(AlunoUpdateForm formUpdate, Aluno entity) {
        entity.setNome(formUpdate.getNome());
        entity.setBairro(formUpdate.getBairro());
        entity.setDataDeNascimento(formUpdate.getDataDeNascimento());
    }

    private void copyDtoToEntity(AlunoForm form, Aluno entity) {
        entity.setNome(form.getNome());
        entity.setCpf(form.getCpf());
        entity.setDataDeNascimento(form.getDataDeNascimento());
        entity.setBairro(form.getBairro());
    }
}
