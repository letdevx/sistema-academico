package br.com.sistema.academico.model;

import java.util.List;

public class Turma implements Cloneable {
    private String nome;
    private String curso;
    private String turno;
    private String anoSemestre;
    private List<String> disciplinas;

    public Turma() {}

    public Turma(String nome, String curso, String turno, String anoSemestre, List<String> disciplinas) {
        this.nome = nome;
        this.curso = curso;
        this.turno = turno;
        this.anoSemestre = anoSemestre;
        this.disciplinas = disciplinas;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getAnoSemestre() { return anoSemestre; }
    public void setAnoSemestre(String anoSemestre) { this.anoSemestre = anoSemestre; }
    public List<String> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<String> disciplinas) { this.disciplinas = disciplinas; }

    @Override
    public Turma clone() {
        try {
            Turma clone = (Turma) super.clone();
            clone.disciplinas = disciplinas != null ? List.copyOf(disciplinas) : null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
