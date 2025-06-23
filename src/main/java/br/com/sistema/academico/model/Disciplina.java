package br.com.sistema.academico.model;

public class Disciplina implements Cloneable {
    private String nome;

    public Disciplina() {}
    public Disciplina(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public Disciplina clone() {
        try {
            return (Disciplina) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
