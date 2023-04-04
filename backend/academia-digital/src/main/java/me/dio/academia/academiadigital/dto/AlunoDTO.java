package me.dio.academia.academiadigital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import me.dio.academia.academiadigital.entities.Aluno;
import me.dio.academia.academiadigital.entities.AvaliacaoFisica;
import me.dio.academia.academiadigital.entities.forms.AlunoForm;
import me.dio.academia.academiadigital.entities.forms.AlunoUpdateForm;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AlunoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cpf;
    private String bairro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;
    private List<AvaliacaoFisica> avaliacoesFisicas;

    public AlunoDTO(Long id, String nome, String cpf, String bairro, LocalDate dataDeNascimento, List<AvaliacaoFisica> avaliacoesFisicas) {
        this.id = id;
        this.setNome(nome); // chama o método setName modificado para definir o nome em maiúsculas
        this.cpf = cpf;
        this.bairro = bairro;
        this.dataDeNascimento = dataDeNascimento;
        this.avaliacoesFisicas = avaliacoesFisicas;
    }

    public AlunoDTO(Aluno entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.bairro = entity.getBairro();
        this.dataDeNascimento = entity.getDataDeNascimento();
        this.avaliacoesFisicas = entity.getAvaliacoesFisicas();
    }

    public AlunoForm toForm() {
        AlunoForm form = new AlunoForm();
        form.setNome(this.getNome());
        form.setCpf(this.getCpf());
        form.setBairro(this.getBairro());
        form.setDataDeNascimento(this.getDataDeNascimento());

        return form;
    }

    public AlunoUpdateForm toUpdateForm() {
        AlunoUpdateForm form = new AlunoUpdateForm();
        form.setNome(this.getNome());
        form.setBairro(this.getBairro());
        form.setDataDeNascimento(this.getDataDeNascimento());

        return form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase(); // converte para maiúsculas e define o nome
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public List<AvaliacaoFisica> getAvaliacoesFisicas() {
        return avaliacoesFisicas;
    }

    public void setAvaliacoesFisicas(List<AvaliacaoFisica> avaliacoesFisicas) {
        this.avaliacoesFisicas = avaliacoesFisicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlunoDTO alunoDTO = (AlunoDTO) o;

        return Objects.equals(id, alunoDTO.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
