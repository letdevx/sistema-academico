package br.com.sistema.academico.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Professor no sistema acadêmico
 */
public class Professor {
    private Long id;
    private String nome;
    private String cpf;
    private String departamento;
    private String email;
    private List<String> disciplinas = new ArrayList<>();

    /**
     * Construtor padrão
     */
    public Professor() {
    }

    /**
     * Construtor com todos os campos
     */
    public Professor(String nome, String cpf, String departamento, String email, List<String> disciplinas) {
        this.nome = nome;
        this.cpf = cpf;
        this.departamento = departamento;
        this.email = email;
        this.disciplinas = disciplinas != null ? disciplinas : new ArrayList<>();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getDisciplinas() {
        return disciplinas;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", departamento='" + departamento + '\'' +
                ", email='" + email + '\'' +
                ", disciplinas='" + disciplinas + '\'' +
                '}';
    }
}
