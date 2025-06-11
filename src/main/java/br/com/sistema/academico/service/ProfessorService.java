package br.com.sistema.academico.service;

import java.util.regex.Pattern;

import br.com.sistema.academico.dao.ProfessorDAO;
import br.com.sistema.academico.model.Professor;

public class ProfessorService {
    private final ProfessorDAO professorDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public ProfessorService() {
        this.professorDAO = new ProfessorDAO();
    }

    public boolean validarCampos(String nome, String cpf, String departamento, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório!");
        }
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        if (cpfJaExiste(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório!");
        }
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido!");
        }
        return true;
    }

    public void salvarProfessor(String nome, String cpf, String departamento, String email) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setCpf(cpf);
        professor.setDepartamento(departamento);
        professor.setEmail(email);
        professorDAO.save(professor);
    }

    private boolean validarEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean validarCPF(String cpf) {
        return CPF_PATTERN.matcher(cpf).matches();
    }

    private boolean cpfJaExiste(String cpf) {
        return professorDAO.findByCpf(cpf).isPresent();
    }
}
