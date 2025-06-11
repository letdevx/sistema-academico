package br.com.sistema.academico.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.sistema.academico.dao.ProfessorDAO;
import br.com.sistema.academico.factory.ComponenteFactory;
import br.com.sistema.academico.factory.SwingComponenteFactory;
import br.com.sistema.academico.model.Professor;
import br.com.sistema.academico.service.ProfessorService;

public class TelaCadastroProfessor extends TelaCadastroTemplate {
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoDepartamento;
    private JTextField campoEmail;
    private ComponenteFactory factory;
    private ProfessorDAO professorDAO;
    private ProfessorService professorService = new ProfessorService();
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
        try {
            professorService.validarCampos(
                campoNome.getText(),
                campoCpf.getText(),
                campoDepartamento.getText(),
                campoEmail.getText()
            );
            professorService.salvarProfessor(
                campoNome.getText(),
                campoCpf.getText(),
                campoDepartamento.getText(),
                campoEmail.getText()
            );
            limparCampos();
            mostrarMensagemSucesso("Professor salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (Exception e) {
            mostrarMensagemErro("Erro ao salvar professor: " + e.getMessage());
        }
    }

    private boolean validarCampos() {
        try {
            professorService.validarCampos(
                campoNome.getText(),
                campoCpf.getText(),
                campoDepartamento.getText(),
                campoEmail.getText()
            );
            return true;
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
            return false;
        }
    }

    private boolean validarEmail(String email) {
        return professorService != null && professorService.validarCampos("nome", "123.456.789-00", "dep", email);
    }

    private boolean validarCPF(String cpf) {
        return professorService != null && professorService.validarCampos("nome", cpf, "dep", "email@email.com");
    }

    private boolean cpfJaExiste(String cpf) {
        return false; // Lógica já está no serviço
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
