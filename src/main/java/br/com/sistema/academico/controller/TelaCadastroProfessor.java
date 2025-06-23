package br.com.sistema.academico.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
    private final ProfessorService professorService = new ProfessorService();
    private JTable tabelaProfessores;
    private DefaultTableModel tabelaModel;

    public TelaCadastroProfessor() {
        super("Cadastro de Professor");
        // Após a construção completa, carregar a tabela
        carregarProfessoresNaTabela();
    }

    @Override
    protected void inicializarComponentes() {
        this.factory = new SwingComponenteFactory();
        super.inicializarComponentes();
        inicializarTabelaProfessores();
        // Remover chamada daqui para evitar NullPointerException
        // carregarProfessoresNaTabela();
    }

    private void inicializarTabelaProfessores() {
        String[] colunas = {"Nome", "CPF", "Departamento", "E-mail"};
        tabelaModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProfessores = new JTable(tabelaModel);
    }

    private void carregarProfessoresNaTabela() {
        tabelaModel.setRowCount(0); // Limpa a tabela
        List<Professor> professores = professorService.listarProfessores();
        for (Professor p : professores) {
            tabelaModel.addRow(new Object[]{
                p.getNome(),
                p.getCpf(),
                p.getDepartamento(),
                p.getEmail()
            });
        }
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

        // Adiciona a tabela de professores abaixo do formulário
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane scrollPane = new JScrollPane(tabelaProfessores);
        painel.add(scrollPane, gbc);

        return painel;
    }

    private void inicializarCampos() {
        campoNome = factory.criarCampoTexto(false);
        campoCpf = factory.criarCampoTexto(false);
        campoDepartamento = factory.criarCampoTexto(false);
        campoEmail = factory.criarCampoTexto(false);

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
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(13, 110, 253)); // Bootstrap azul
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(btnSalvar.getFont().deriveFont(java.awt.Font.BOLD));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(13, 110, 253)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            carregarProfessoresNaTabela(); // Atualiza a tabela após salvar
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (Exception e) {
            mostrarMensagemErro("Erro ao salvar professor: " + e.getMessage());
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoDepartamento.setText("");
        campoEmail.setText("");
    }
}
