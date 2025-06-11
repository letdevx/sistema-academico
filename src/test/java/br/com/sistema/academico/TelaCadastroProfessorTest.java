package br.com.sistema.academico;

import java.awt.Component;
import java.awt.Container;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import br.com.sistema.academico.controller.TelaCadastroProfessor;
import br.com.sistema.academico.dao.ProfessorDAO;
import br.com.sistema.academico.database.DatabaseManager;
import br.com.sistema.academico.model.Professor;

public class TelaCadastroProfessorTest {
    private TelaCadastroProfessor tela;
    private ProfessorDAO professorDAO;
    private Connection connection;

    @Before
    public void setUp() {
        tela = new TelaCadastroProfessor();
        professorDAO = new ProfessorDAO();
        connection = DatabaseManager.getInstance().getConnection();
        limparTabelaProfessores();
    }

    @After
    public void tearDown() {
        limparTabelaProfessores();
    }

    private void limparTabelaProfessores() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM professores");
        } catch (Exception e) {
            fail("Erro ao limpar tabela de professores: " + e.getMessage());
        }
    }

    private Component[] getAllComponents(Container container) {
        java.util.List<Component> componentList = new java.util.ArrayList<>();
        for (Component component : container.getComponents()) {
            componentList.add(component);
            if (component instanceof Container) {
                Component[] children = getAllComponents((Container) component);
                for (Component child : children) {
                    componentList.add(child);
                }
            }
        }
        return componentList.toArray(new Component[0]);
    }

    private JTextField getTextFieldByToolTip(String tooltip) {
        for (Component comp : getAllComponents(tela)) {
            if (comp instanceof JTextField) {
                JTextField textField = (JTextField) comp;
                String fieldTooltip = textField.getToolTipText();
                if (fieldTooltip != null) {
                    System.out.println("Field tooltip: " + fieldTooltip + ", searching for: " + tooltip);
                    if (fieldTooltip.toLowerCase().contains(tooltip.toLowerCase())) {
                        return textField;
                    }
                }
            }
        }
        System.out.println("Campo não encontrado para tooltip: " + tooltip);
        System.out.println("Campos disponíveis:");
        for (Component comp : getAllComponents(tela)) {
            if (comp instanceof JTextField) {
                System.out.println("- " + ((JTextField) comp).getToolTipText());
            }
        }
        return null;
    }

    private JButton getButtonByText(String text) {
        for (Component comp : getAllComponents(tela)) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (text.equalsIgnoreCase(button.getText())) {
                    return button;
                }
            }
        }
        return null;
    }

    @Test
    public void testCamposVazios() {
        JButton btnSalvar = getButtonByText("Salvar");
        assertNotNull("Botão Salvar não encontrado", btnSalvar);
        
        btnSalvar.doClick();
        
        List<Professor> professores = professorDAO.findAll();
        assertEquals("Não deveria ter salvo nenhum registro", 0, professores.size());
    }

    @Test
    public void testSalvarProfessorValido() {
        JTextField campoNome = getTextFieldByToolTip("nome");
        JTextField campoCpf = getTextFieldByToolTip("cpf");
        JTextField campoDepartamento = getTextFieldByToolTip("departamento");
        JTextField campoEmail = getTextFieldByToolTip("email");
        JButton btnSalvar = getButtonByText("Salvar");

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

        List<Professor> professores = professorDAO.findAll();
        assertEquals("Deve ter salvo um registro", 1, professores.size());
        
        Professor professor = professores.get(0);
        assertEquals("Nome deve estar correto", "João Silva", professor.getNome());
        assertEquals("CPF deve estar correto", "123.456.789-00", professor.getCpf());
        assertEquals("Departamento deve estar correto", "Computação", professor.getDepartamento());
        assertEquals("Email deve estar correto", "joao@email.com", professor.getEmail());

        assertTrue("Campo nome deve estar vazio", campoNome.getText().isEmpty());
        assertTrue("Campo CPF deve estar vazio", campoCpf.getText().isEmpty());
        assertTrue("Campo departamento deve estar vazio", campoDepartamento.getText().isEmpty());
        assertTrue("Campo email deve estar vazio", campoEmail.getText().isEmpty());
    }
}
