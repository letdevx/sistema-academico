package model;

public class Aluno {
    // Dados pessoais
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String sexo;

    // Contato
    private String email;
    private String telefone;
    private String endereco;

    // Acadêmico
    private String curso;
    private String grau;
    private String turno;
    private String dataIngresso;
    private String situacao;

    // Construtor completo
    public Aluno(String nome, String cpf, String dataNascimento, String sexo,
                 String email, String telefone, String endereco,
                 String curso, String grau, String turno,
                 String dataIngresso, String situacao) {

        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;

        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;

        this.curso = curso;
        this.grau = grau;
        this.turno = turno;
        this.dataIngresso = dataIngresso;
        this.situacao = situacao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getDataNascimento() { return dataNascimento; }
    public String getSexo() { return sexo; }

    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }

    public String getCurso() { return curso; }
    public String getGrau() { return grau; }
    public String getTurno() { return turno; }
    public String getDataIngresso() { return dataIngresso; }
    public String getSituacao() { return situacao; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public void setCurso(String curso) { this.curso = curso; }
    public void setGrau(String grau) { this.grau = grau; }
    public void setTurno(String turno) { this.turno = turno; }
    public void setDataIngresso(String dataIngresso) { this.dataIngresso = dataIngresso; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
}
