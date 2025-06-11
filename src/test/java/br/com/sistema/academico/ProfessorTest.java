package br.com.sistema.academico;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import br.com.sistema.academico.controller.TelaCadastroProfessor;

public class ProfessorTest {
    private TelaCadastroProfessor tela;
    private static final String ARQUIVO_PROFESSORES = "src/main/resources/data/professores.txt";

    @Before
    public void setUp() {
        tela = new TelaCadastroProfessor();
        try {
            File file = new File(ARQUIVO_PROFESSORES);
            file.getParentFile().mkdirs();
            file.createNewFile();
            Files.write(Paths.get(ARQUIVO_PROFESSORES), new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JTextField findTextField(String toolTipSubstring) {
        for (Component comp : findAllComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                String tooltip = field.getToolTipText();
                if (tooltip != null && tooltip.toLowerCase().contains(toolTipSubstring.toLowerCase())) {
                    return field;
                }
            }
        }
        return null;
    }

    private JButton findButton(String text) {
        for (Component comp : findAllComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (text.equalsIgnoreCase(button.getText())) {
                    return button;
                }
            }
        }
        return null;
    }

    private List<Component> findAllComponents() {
        List<Component> components = new ArrayList<>();
        addComponents(tela, components);
        return components;
    }

    private void addComponents(Container container, List<Component> components) {
        Component[] comps = container.getComponents();
        for (Component comp : comps) {
            components.add(comp);
            if (comp instanceof Container) {
                addComponents((Container) comp, components);
            }
        }
    }

    @Test
    public void testCamposVazios() {
        JButton btnSalvar = findButton("Salvar");
        assertNotNull("Botão Salvar não encontrado", btnSalvar);
        
        btnSalvar.doClick();
        
        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_PROFESSORES));
            assertEquals("Não deveria ter salvo nenhum registro", 0, linhas.size());
        } catch (IOException e) {
            fail("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    @Test
    public void testSalvarProfessorValido() {
        JTextField campoNome = findTextField("nome");
        JTextField campoCpf = findTextField("cpf");
        JTextField campoDepartamento = findTextField("departamento");
        JTextField campoEmail = findTextField("e-mail");
        JButton btnSalvar = findButton("Salvar");

        assertNotNull("Campo nome não encontrado", campoNome);
        assertNotNull("Campo CPF não encontrado", campoCpf);
        assertNotNull("Campo departamento não encontrado", campoDepartamento);
        assertNotNull("Campo email não encontrado", campoEmail);
        assertNotNull("Botão Salvar não encontrado", btnSalvar);

        campoNome.setText("João Silva");
        campoCpf.setText("123.456.789-00");
        campoDepartamento.setText("Computação");
        campoEmail.setText("joao@email.com");

        btnSalvar.doClick();

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_PROFESSORES));
            assertEquals("Deve ter salvo um registro", 1, linhas.size());
            String linha = linhas.get(0);
            assertTrue("Registro deve conter o nome", linha.contains("João Silva"));
            assertTrue("Registro deve conter o CPF", linha.contains("123.456.789-00"));
            assertTrue("Registro deve conter o departamento", linha.contains("Computação"));
            assertTrue("Registro deve conter o email", linha.contains("joao@email.com"));

            // Verifica se os campos foram limpos
            assertTrue("Campo nome deve estar vazio", campoNome.getText().isEmpty());
            assertTrue("Campo CPF deve estar vazio", campoCpf.getText().isEmpty());
            assertTrue("Campo departamento deve estar vazio", campoDepartamento.getText().isEmpty());
            assertTrue("Campo email deve estar vazio", campoEmail.getText().isEmpty());
        } catch (IOException e) {
            fail("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    @Test
    public void testValidacaoCPF() {
        JTextField campoNome = findTextField("nome");
        JTextField campoCpf = findTextField("cpf");
        JTextField campoDepartamento = findTextField("departamento");
        JTextField campoEmail = findTextField("e-mail");
        JButton btnSalvar = findButton("Salvar");

        campoNome.setText("João Silva");
        campoCpf.setText("123"); // CPF inválido
        campoDepartamento.setText("Computação");
        campoEmail.setText("joao@email.com");

        btnSalvar.doClick();

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_PROFESSORES));
            assertEquals("Não deve salvar com CPF inválido", 0, linhas.size());
        } catch (IOException e) {
            fail("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    @Test
    public void testDuplicacaoCPF() {
        JTextField campoNome = findTextField("nome");
        JTextField campoCpf = findTextField("cpf");
        JTextField campoDepartamento = findTextField("departamento");
        JTextField campoEmail = findTextField("e-mail");
        JButton btnSalvar = findButton("Salvar");

        // Primeiro professor
        campoNome.setText("João Silva");
        campoCpf.setText("123.456.789-00");
        campoDepartamento.setText("Computação");
        campoEmail.setText("joao@email.com");
        btnSalvar.doClick();

        // Tenta cadastrar outro professor com mesmo CPF
        campoNome.setText("Maria Santos");
        campoCpf.setText("123.456.789-00");
        campoDepartamento.setText("Matemática");
        campoEmail.setText("maria@email.com");
        btnSalvar.doClick();

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_PROFESSORES));
            assertEquals("Deve ter apenas um registro", 1, linhas.size());
            assertTrue("Deve manter o primeiro registro", linhas.get(0).contains("João Silva"));
        } catch (IOException e) {
            fail("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}
