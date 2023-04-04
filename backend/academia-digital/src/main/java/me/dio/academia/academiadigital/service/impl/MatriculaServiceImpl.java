package me.dio.academia.academiadigital.service.impl;

import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.Matricula;
import me.dio.academia.academiadigital.entities.forms.MatriculaForm;
import me.dio.academia.academiadigital.repositories.AlunoRepository;
import me.dio.academia.academiadigital.repositories.MatriculaRepository;
import me.dio.academia.academiadigital.service.IMatriculaService;
import me.dio.academia.academiadigital.service.impl.exceptions.AlunoNotFoundException;
import me.dio.academia.academiadigital.service.impl.exceptions.MatriculaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatriculaServiceImpl implements IMatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;

    public MatriculaServiceImpl(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Matricula create(MatriculaForm form) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(form.getAlunoId());
        if (optionalAluno.isPresent()) {
            Matricula matricula = new Matricula();
            Aluno aluno = optionalAluno.get();

            matricula.setAluno(aluno);

            return matriculaRepository.save(matricula);
        } else {
            // handle the case where the Aluno is not found
            throw new AlunoNotFoundException();
        }
    }

    @Override
    public Matricula findById(Long id) {
        Optional<Matricula> optionalMatricula = matriculaRepository.findById(id);
        if (optionalMatricula.isPresent()) {
            return optionalMatricula.get();
        } else {
            // handle the case where the Matricula is not found
            throw new MatriculaNotFoundException();
        }
    }


    @Override
    public List<Matricula> getAll(String bairro) {

        if(Objects.isNull(bairro)){
            return matriculaRepository.findAll();
        }else{
            return matriculaRepository.findAlunosMatriculadosBairro(bairro);
        }

    }

    @Override
    public void delete(Long id) {
        Matricula matricula = findById(id);
        matriculaRepository.delete(matricula);
    }
}
