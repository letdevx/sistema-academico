package builder;

import model.Aluno;

public class AlunoBuilder {
    private Aluno aluno = new Aluno();

    public AlunoBuilder setNome(String nome) {
        aluno.setNome(nome);
        return this;
    }

    public AlunoBuilder setCpf(String cpf) {
        aluno.setCpf(cpf);
        return this;
    }

    public AlunoBuilder setCurso(String curso) {
        aluno.setCurso(curso);
        return this;
    }

    public AlunoBuilder setPeriodo(String periodo) {  // ✅ Adicione este método
        aluno.setPeriodo(periodo);
        return this;
    }

    public AlunoBuilder setDataNascimento(String dataNascimento) {
        aluno.setDataNascimento(dataNascimento);
        return this;
    }

    public AlunoBuilder setSexo(String sexo) {
        aluno.setSexo(sexo);
        return this;
    }

    public AlunoBuilder setEmail(String email) {
        aluno.setEmail(email);
        return this;
    }

    public AlunoBuilder setTelefone(String telefone) {
        aluno.setTelefone(telefone);
        return this;
    }

    public AlunoBuilder setEndereco(String endereco) {
        aluno.setEndereco(endereco);
        return this;
    }

    public AlunoBuilder setGrau(String grau) {
        aluno.setGrau(grau);
        return this;
    }

    public AlunoBuilder setTurno(String turno) {
        aluno.setTurno(turno);
        return this;
    }

    public AlunoBuilder setDataIngresso(String dataIngresso) {
        aluno.setDataIngresso(dataIngresso);
        return this;
    }

    public AlunoBuilder setSituacao(String situacao) {
        aluno.setSituacao(situacao);
        return this;
    }

    public Aluno build() {
        return aluno;
    }
}
