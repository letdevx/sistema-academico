package br.com.sistema.academico.service;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import br.com.sistema.academico.dao.AlunoRepository;
import br.com.sistema.academico.model.Aluno;

public class AlunoServiceTest {
    private AlunoService service;
    private AlunoRepository mockRepo;

    @Before
    public void setUp() {
        mockRepo = new MockAlunoRepository();
        service = new AlunoService(mockRepo);
    }

    @After
    public void tearDown() {
        // Opcional: Limpar alunos de teste do banco, se necessário
    }

    @Test
    public void testValidarCamposValido() {
        assertTrue(service.validarCampos("João", "01/01/2000", 1, "123.456.789-00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidarCamposNomeVazio() {
        service.validarCampos("", "01/01/2000", 1, "123.456.789-00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidarCamposDataNascimentoInvalida() {
        service.validarCampos("João", "__/__/____", 1, "123.456.789-00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidarCamposGeneroNaoSelecionado() {
        service.validarCampos("João", "01/01/2000", 0, "123.456.789-00");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidarCamposCpfInvalido() {
        service.validarCampos("João", "01/01/2000", 1, "___.___.___-__");
    }

    @Test
    public void testSalvarAluno() {
        Aluno aluno = new Aluno(
            "Maria",
            "987.654.321-00",
            "02/02/2001",
            "Feminino",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        );
        service.salvarAluno(aluno);
        assertTrue(service.cpfJaExiste("987.654.321-00"));
    }
}
