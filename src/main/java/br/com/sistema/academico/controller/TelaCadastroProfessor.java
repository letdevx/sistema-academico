package br.com.sistema.academico.controller;

import br.com.sistema.academico.factory.ComponenteFactory;
import br.com.sistema.academico.factory.SwingComponenteFactory;
import br.com.sistema.academico.model.Professor;
import br.com.sistema.academico.dao.ProfessorDAO;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class TelaCadastroProfessor extends TelaCadastroTemplate {
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoDepartamento;
    private JTextField campoEmail;
    private ComponenteFactory factory;
    private ProfessorDAO professorDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

    public TelaCadastroProfessor() {
        super("Cadastro de Professor");
        this.professorDAO = new ProfessorDAO();
    }

    @Override
    protected void inicializarComponentes() {
        this.factory = new SwingComponenteFactory();
        super.inicializarComponentes();
    }

    @Override
    protected JPanel criarFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        inicializarCampos();
        adicionarCamposAoFormulario(painel, gbc);

        return painel;
    }

    private void inicializarCampos() {
        campoNome = factory.criarCampoTexto();
        campoCpf = factory.criarCampoTexto();
        campoDepartamento = factory.criarCampoTexto();
        campoEmail = factory.criarCampoTexto();

        campoNome.setToolTipText("Digite o nome do professor");
        campoCpf.setToolTipText("Digite o CPF");
        campoDepartamento.setToolTipText("Digite o departamento");
        campoEmail.setToolTipText("Digite o e-mail");
    }

    private void adicionarCamposAoFormulario(JPanel painel, GridBagConstraints gbc) {
        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(factory.criarLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painel.add(campoNome, gbc);

        // CPF
        gbc.gridx = 0; gbc.gridy++;
        painel.add(factory.criarLabel("CPF:"), gbc);
        gbc.gridx = 1;
        painel.add(campoCpf, gbc);

        // Departamento
        gbc.gridx = 0; gbc.gridy++;
        painel.add(factory.criarLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        painel.add(campoDepartamento, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy++;
        painel.add(factory.criarLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        painel.add(campoEmail, gbc);
    }

    @Override
    protected JPanel criarBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = criarBotaoSalvar();
        painel.add(btnSalvar);
        return painel;
    }

    private JButton criarBotaoSalvar() {
        JButton btnSalvar = factory.criarBotao("Salvar");
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarProfessor());
        return btnSalvar;
    }

    private void salvarProfessor() {
        if (!validarCampos()) {
            return;
        }

        Professor professor = criarProfessor();
        try {
            professorDAO.save(professor);
            limparCampos();
            mostrarMensagemSucesso("Professor salvo com sucesso!");
        } catch (Exception e) {
            mostrarMensagemErro("Erro ao salvar professor: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (campoNome.getText().trim().isEmpty()) {
            mostrarMensagemErro("Nome é obrigatório!");
            return false;
        }
        if (campoCpf.getText().trim().isEmpty()) {
            mostrarMensagemErro("CPF é obrigatório!");
            return false;
        }
        if (!validarCPF(campoCpf.getText())) {
            mostrarMensagemErro("CPF inválido!");
            return false;
        }
        if (cpfJaExiste(campoCpf.getText())) {
            mostrarMensagemErro("CPF já cadastrado!");
            return false;
        }
        if (campoEmail.getText().trim().isEmpty()) {
            mostrarMensagemErro("E-mail é obrigatório!");
            return false;
        }
        if (!validarEmail(campoEmail.getText())) {
            mostrarMensagemErro("E-mail inválido!");
            return false;
        }
        return true;
    }

    private boolean validarEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean validarCPF(String cpf) {
        return CPF_PATTERN.matcher(cpf).matches();
    }

    private boolean cpfJaExiste(String cpf) {
        // TODO: Implementar lógica de verificação no banco de dados
        return false;
    }

    private Professor criarProfessor() {
        Professor professor = new Professor();
        professor.setNome(campoNome.getText());
        professor.setCpf(campoCpf.getText());
        professor.setDepartamento(campoDepartamento.getText());
        professor.setEmail(campoEmail.getText());
        return professor;
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoDepartamento.setText("");
        campoEmail.setText("");
    }
}
