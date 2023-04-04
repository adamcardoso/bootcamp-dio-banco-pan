package me.dio.academia.academiadigital.service.impl;

import me.dio.academia.academiadigital.service.impl.exceptions.AlunoNotFoundException;
import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaForm;
import me.dio.academia.academiadigital.entities.forms.AvaliacaoFisicaUpdateForm;
import me.dio.academia.academiadigital.repositories.AlunoRepository;
import me.dio.academia.academiadigital.repositories.AvaliacaoFisicaRepository;
import me.dio.academia.academiadigital.service.IAvaliacaoFisicaService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoFisicaServiceImpl implements IAvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final AlunoRepository alunoRepository;

    public AvaliacaoFisicaServiceImpl(AvaliacaoFisicaRepository avaliacaoFisicaRepository, AlunoRepository alunoRepository) {
        this.avaliacaoFisicaRepository = avaliacaoFisicaRepository;
        this.alunoRepository = alunoRepository;
    }

    /*
     * Using Optional.get() without first checking if the optional is present with isPresent() is generally considered a bad practice
     * because it can result in a NoSuchElementException being thrown at runtime if the optional is empty.
     * It is always better to first check if the optional has a value using isPresent() before calling get().
     */
    @Override
    public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
        Optional<Aluno> optionalAluno = alunoRepository.findById(form.getAlunoId());
        if (optionalAluno.isPresent()) {
            AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
            Aluno aluno = optionalAluno.get();

            avaliacaoFisica.setAluno(aluno);
            avaliacaoFisica.setPeso(form.getPeso());
            avaliacaoFisica.setAltura(form.getAltura());

            return avaliacaoFisicaRepository.save(avaliacaoFisica);
        } else {
            // handle the case where the Aluno is not found
            throw new AlunoNotFoundException();
        }
    }

    @Override
    public List<AvaliacaoFisica> getAll() {

        return avaliacaoFisicaRepository.findAll();
    }

    @Override
    public AvaliacaoFisica findById(Long id) {
        Optional<AvaliacaoFisica> avaliacaoFisicaOptional = avaliacaoFisicaRepository.findById(id);
        if (avaliacaoFisicaOptional.isPresent()) {
            return avaliacaoFisicaOptional.get();
        } else {
            throw new EntityNotFoundException("Avaliação Física não encontrada com o id: " + id);
        }
    }

    @Override
    public AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm formUpdate) {
        AvaliacaoFisica avaliacaoFisica = this.findById(id);

        avaliacaoFisica.setPeso(formUpdate.getPeso());
        avaliacaoFisica.setAltura(formUpdate.getAltura());

        return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }

    @Override
    public void delete(Long id) {
        AvaliacaoFisica avaliacaoFisica = this.findById(id);
        avaliacaoFisicaRepository.delete(avaliacaoFisica);
    }
}
