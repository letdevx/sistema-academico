package br.com.sistema.academico.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.sistema.academico.factory.ComponenteFactory;
import br.com.sistema.academico.factory.SwingComponenteFactory;
import br.com.sistema.academico.service.AlunoService;

public class TelaCadastroAluno extends TelaCadastroTemplate {
    
    private static TelaCadastroAluno instancia;
    private static String arquivoSaida = "alunos.txt"; // Arquivo de saída

    private JTextField campoNome;
    private JFormattedTextField campoDataNascimento, campoCpf, campoRg;
    private JComboBox<String> campoGenero, campoEstadoCivil;
    private JTextField campoNacionalidade, campoNaturalidade, campoOrgaoEmissor;
    private JFormattedTextField campoDataEmissao;
    private ComponenteFactory factory;
    private AlunoService alunoService = new AlunoService();

    public static TelaCadastroAluno getInstancia() {
        if (instancia == null) {
            instancia = new TelaCadastroAluno();
        }
        return instancia;
    }

    private TelaCadastroAluno() {
        super("Cadastro de Alunos");
    }

    // Método para configurar arquivo de saída (para testes)
    public static void setArquivoSaida(String arquivo) {
        arquivoSaida = arquivo;
        // Atualiza também no serviço
        getInstancia().alunoService.setArquivoSaida(arquivo);
    }

    private JTextField criarCampoTexto(String nome, String valorPadrao) {
        JTextField campo = new JTextField(20);
        campo.setName(nome);
        campo.setText(valorPadrao);
        return campo;
    }

    private JFormattedTextField criarCampoData(String nome) {
        JFormattedTextField campo = null;
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(mascara);
            campo.setColumns(20);
            campo.setName(nome);
        } catch (ParseException e) {
            campo = new JFormattedTextField();
            campo.setColumns(20);
            campo.setName(nome);
        }
        return campo;
    }

    private JFormattedTextField criarCampoCPF(String nome) {
        JFormattedTextField campo = null;
        try {
            MaskFormatter mascara = new MaskFormatter("###.###.###-##");
            mascara.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(mascara);
            campo.setColumns(20);
            campo.setName(nome);
        } catch (ParseException e) {
            campo = new JFormattedTextField();
            campo.setColumns(20);
            campo.setName(nome);
        }
        return campo;
    }

    private JFormattedTextField criarCampoRG(String nome) {
        JFormattedTextField campo = null;
        try {
            MaskFormatter mascara = new MaskFormatter("##.###.###-#");
            mascara.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(mascara);
            campo.setColumns(20);
            campo.setName(nome);
        } catch (ParseException e) {
            campo = new JFormattedTextField();
            campo.setColumns(20);
            campo.setName(nome);
        }
        return campo;
    }

    @Override
    protected JPanel criarFormulario() {
        factory = new SwingComponenteFactory();

        // Painel principal que usará GridBagLayout
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título da seção
        JLabel titulo = new JLabel("Informações Pessoais");
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelForm.add(titulo, gbc);
        gbc.gridwidth = 1;

        // Nome Completo
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Nome Completo:"), gbc);
        campoNome = criarCampoTexto("campoNome", "");
        gbc.gridx = 1;
        painelForm.add(campoNome, gbc);

        // Data de Nascimento
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Data de Nascimento:"), gbc);
        campoDataNascimento = criarCampoData("campoDataNascimento");
        gbc.gridx = 1;
        painelForm.add(campoDataNascimento, gbc);

        // Gênero
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Gênero:"), gbc);
        campoGenero = new JComboBox<>(new String[]{"Selecione", "Masculino", "Feminino", "Outro", "Prefiro não informar"});
        campoGenero.setName("campoGenero");
        gbc.gridx = 1;
        painelForm.add(campoGenero, gbc);

        // CPF
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("CPF:"), gbc);
        campoCpf = criarCampoCPF("campoCpf");
        gbc.gridx = 1;
        painelForm.add(campoCpf, gbc);

        // RG
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("RG:"), gbc);
        campoRg = criarCampoRG("campoRg");
        gbc.gridx = 1;
        painelForm.add(campoRg, gbc);

        // Órgão Emissor
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Órgão Emissor:"), gbc);
        campoOrgaoEmissor = criarCampoTexto("campoOrgaoEmissor", "");
        gbc.gridx = 1;
        painelForm.add(campoOrgaoEmissor, gbc);

        // Data de Emissão
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Data de Emissão:"), gbc);
        campoDataEmissao = criarCampoData("campoDataEmissao");
        gbc.gridx = 1;
        painelForm.add(campoDataEmissao, gbc);

        // Nacionalidade
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Nacionalidade:"), gbc);
        campoNacionalidade = criarCampoTexto("campoNacionalidade", "Brasileira");
        gbc.gridx = 1;
        painelForm.add(campoNacionalidade, gbc);

        // Naturalidade
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Naturalidade:"), gbc);
        campoNaturalidade = criarCampoTexto("campoNaturalidade", "");
        gbc.gridx = 1;
        painelForm.add(campoNaturalidade, gbc);

        // Estado Civil
        gbc.gridy++; gbc.gridx = 0;
        painelForm.add(factory.criarLabel("Estado Civil:"), gbc);
        campoEstadoCivil = new JComboBox<>(new String[]{"Selecione", "Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)"});
        campoEstadoCivil.setName("campoEstadoCivil");
        gbc.gridx = 1;
        painelForm.add(campoEstadoCivil, gbc);

        // Quadro visual com borda e título
        JPanel cardForm = new JPanel(new BorderLayout());
        cardForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Cadastro de Aluno"
        ));
        cardForm.add(painelForm, BorderLayout.CENTER);
        cardForm.setBackground(Color.WHITE);

        // Painel final para centralizar
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.add(cardForm);

        return wrapper;
    }

    @Override
    protected JPanel criarBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.setName("botaoSalvar");  // Adicionando nome ao botão
        botaoSalvar.setBackground(new Color(0, 123, 255));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoSalvar.setBorder(BorderFactory.createLineBorder(new Color(0, 105, 217)));
        botaoSalvar.setPreferredSize(new Dimension(120, 40));
        botaoSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoSalvar.setContentAreaFilled(false);
        botaoSalvar.setOpaque(true);

        botaoSalvar.addActionListener(e -> salvarAluno());

        painel.add(botaoSalvar);
        return painel;
    }

    private boolean validarCampos() {
        try {
            alunoService.validarCampos(
                campoNome.getText(),
                campoDataNascimento.getText(),
                campoGenero.getSelectedIndex(),
                campoCpf.getText()
            );
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
    }

    private void salvarAluno() {
        if (!validarCampos()) {
            return;
        }
        try {
            alunoService.salvarAluno(
                campoNome.getText(),
                campoDataNascimento.getText(),
                campoGenero.getSelectedItem().toString(),
                campoCpf.getText(),
                campoRg.getText(),
                campoOrgaoEmissor.getText(),
                campoDataEmissao.getText(),
                campoNacionalidade.getText(),
                campoNaturalidade.getText(),
                campoEstadoCivil.getSelectedItem().toString()
            );
            limparCampos();
            mostrarMensagemSucesso("Aluno cadastrado com sucesso!");
        } catch (IOException e) {
            mostrarMensagemErro("Erro ao salvar aluno: " + e.getMessage());
        }
    }

    private void limparCampos() {
        campoNome.setText("");
        campoDataNascimento.setText("");
        campoGenero.setSelectedIndex(0);
        campoCpf.setText("");
        campoRg.setText("");
        campoOrgaoEmissor.setText("");
        campoDataEmissao.setText("");
        campoNacionalidade.setText("Brasileira");
        campoNaturalidade.setText("");
        campoEstadoCivil.setSelectedIndex(0);
    }
}
