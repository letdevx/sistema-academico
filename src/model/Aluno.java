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
    private String periodo; // ⬅️ Campo adicionado

    // 🔧 Construtor vazio necessário para o Builder
    public Aluno() {}

    // Construtor completo
    public Aluno(String nome, String cpf, String dataNascimento, String sexo,
                 String email, String telefone, String endereco,
                 String curso, String grau, String turno,
                 String dataIngresso, String situacao, String periodo) {

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
        this.periodo = periodo;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getGrau() { return grau; }
    public void setGrau(String grau) { this.grau = grau; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getDataIngresso() { return dataIngresso; }
    public void setDataIngresso(String dataIngresso) { this.dataIngresso = dataIngresso; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getPeriodo() { return periodo; } // ⬅️ Getter adicionado
    public void setPeriodo(String periodo) { this.periodo = periodo; } // ⬅️ Setter adicionado
}
