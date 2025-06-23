package br.com.sistema.academico.controller;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.sistema.academico.factory.ComponenteFactory;
import br.com.sistema.academico.factory.SwingComponenteFactory;
import br.com.sistema.academico.model.Professor;
import br.com.sistema.academico.service.DisciplinaService;
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
    private JList<String> listaDisciplinas;
    private DisciplinaService disciplinaService;

    public TelaCadastroProfessor() {
        super("Cadastro de Professor"); // inicializarComponentes, criarFormulario, criarBotoes
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

        // Campo de seleção de disciplinas
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        painel.add(factory.criarLabel("Disciplinas:"), gbc);
        gbc.gridx = 1;
        try {
            disciplinaService = new DisciplinaService();
            List<String> disciplinas = disciplinaService.listarDisciplinas();
            listaDisciplinas = new JList<>(disciplinas.toArray(new String[0]));
        } catch (IOException e) {
            listaDisciplinas = new JList<>(new String[]{"Erro ao carregar disciplinas"});
        }
        listaDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaDisciplinas.setVisibleRowCount(4);
        JScrollPane scrollDisciplinas = new JScrollPane(listaDisciplinas);
        painel.add(scrollDisciplinas, gbc);

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

    private void editarProfessor() {
        int row = tabelaProfessores.getSelectedRow();
        if (row == -1) {
            mostrarMensagemErro("Selecione um professor para editar.");
            return;
        }
        // Preenche os campos com os dados selecionados
        campoNome.setText((String) tabelaModel.getValueAt(row, 0));
        campoCpf.setText((String) tabelaModel.getValueAt(row, 1));
        campoDepartamento.setText((String) tabelaModel.getValueAt(row, 2));
        campoEmail.setText((String) tabelaModel.getValueAt(row, 3));
        // Seleciona as disciplinas do professor (se houver)
        Professor prof = professorService.listarProfessores().stream()
            .filter(p -> p.getCpf().equals(campoCpf.getText()))
            .findFirst().orElse(null);
        if (prof != null && prof.getDisciplinas() != null) {
            int[] indices = prof.getDisciplinas().stream()
                .mapToInt(disc -> {
                    for (int i = 0; i < listaDisciplinas.getModel().getSize(); i++) {
                        if (listaDisciplinas.getModel().getElementAt(i).equals(disc)) return i;
                    }
                    return -1;
                })
                .filter(i -> i >= 0)
                .toArray();
            listaDisciplinas.setSelectedIndices(indices);
        }
        // Ao clicar em salvar, atualizará o registro existente
    }

    @Override
    protected JPanel criarBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = criarBotaoSalvar();
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        btnSalvar.setBackground(new java.awt.Color(13, 110, 253)); // Azul
        btnSalvar.setForeground(java.awt.Color.BLACK); // Label preto
        btnSalvar.setFont(btnSalvar.getFont().deriveFont(java.awt.Font.BOLD));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(13, 110, 253)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setOpaque(true);
        btnSalvar.setContentAreaFilled(true);

        btnEditar.setBackground(new java.awt.Color(255, 193, 7)); // Amarelo
        btnEditar.setForeground(java.awt.Color.BLACK); // Label preto
        btnEditar.setFont(btnEditar.getFont().deriveFont(java.awt.Font.BOLD));
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 193, 7)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setOpaque(true);
        btnEditar.setContentAreaFilled(true);
        btnEditar.addActionListener(e -> editarProfessor());

        btnExcluir.setBackground(new java.awt.Color(220, 53, 69)); // Vermelho
        btnExcluir.setForeground(java.awt.Color.BLACK); // Label preto
        btnExcluir.setFont(btnExcluir.getFont().deriveFont(java.awt.Font.BOLD));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 53, 69)),
            javax.swing.BorderFactory.createEmptyBorder(8, 24, 8, 24)
        ));
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setOpaque(true);
        btnExcluir.setContentAreaFilled(true);
        btnExcluir.addActionListener(e -> excluirProfessor());

        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
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
            List<String> disciplinasSelecionadas = listaDisciplinas.getSelectedValuesList();
            Professor professor = new Professor(
                campoNome.getText(),
                campoCpf.getText(),
                campoDepartamento.getText(),
                campoEmail.getText(),
                disciplinasSelecionadas
            );
            // Se já existe, atualiza; senão, salva novo
            if (professorService.listarProfessores().stream().anyMatch(p -> p.getCpf().equals(campoCpf.getText()))) {
                professor.setId(
                    professorService.listarProfessores().stream()
                        .filter(p -> p.getCpf().equals(campoCpf.getText()))
                        .findFirst().map(Professor::getId).orElse(null)
                );
                professorService.atualizarProfessor(professor);
                mostrarMensagemSucesso("Professor atualizado com sucesso!");
            } else {
                professorService.salvarProfessor(
                    campoNome.getText(),
                    campoCpf.getText(),
                    campoDepartamento.getText(),
                    campoEmail.getText()
                );
                mostrarMensagemSucesso("Professor salvo com sucesso!");
            }
            limparCampos();
            carregarProfessoresNaTabela();
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (Exception e) {
            mostrarMensagemErro("Erro ao salvar professor: " + e.getMessage());
        }
    }

    private void excluirProfessor() {
        int row = tabelaProfessores.getSelectedRow();
        if (row == -1) {
            mostrarMensagemErro("Selecione um professor para excluir.");
            return;
        }
        String cpf = (String) tabelaModel.getValueAt(row, 1);
        professorService.excluirProfessor(cpf);
        carregarProfessoresNaTabela();
        limparCampos();
        mostrarMensagemSucesso("Professor excluído com sucesso!");
    }

    private void limparCampos() {
        campoNome.setText("");
        campoCpf.setText("");
        campoDepartamento.setText("");
        campoEmail.setText("");
        if (listaDisciplinas != null) listaDisciplinas.clearSelection();
    }
}
