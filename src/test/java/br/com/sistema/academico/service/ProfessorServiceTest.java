package br.com.sistema.academico.service;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import br.com.sistema.academico.dao.ProfessorRepository;

public class ProfessorServiceTest {
    private ProfessorService service;
    private ProfessorRepository mockDAO;

    @Before
    public void setUp() throws Exception {
        mockDAO = new MockProfessorRepository();
        service = new ProfessorService(mockDAO);
    }

    @Test
    public void testValidarCamposValido() {
        assertTrue(service.validarCampos("João Silva", "123.456.789-00", "Computação", "joao@email.com"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeVazio() {
        service.validarCampos("", "123.456.789-00", "Computação", "joao@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCpfVazio() {
        service.validarCampos("João Silva", "", "Computação", "joao@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCpfInvalido() {
        service.validarCampos("João Silva", "123", "Computação", "joao@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmailVazio() {
        service.validarCampos("João Silva", "123.456.789-00", "Computação", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmailInvalido() {
        service.validarCampos("João Silva", "123.456.789-00", "Computação", "joaoemail.com");
    }
}
