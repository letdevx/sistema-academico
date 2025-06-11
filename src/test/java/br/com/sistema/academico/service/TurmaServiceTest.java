package br.com.sistema.academico.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TurmaServiceTest {
    private TurmaService service;
    private final String testFile = "src/test/resources/test_turmas.txt";

    @Before
    public void setUp() {
        service = new TurmaService();
        service.setArquivoTurmas(testFile);
        File dir = new File("src/test/resources");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(testFile);
        if (file.exists()) file.delete();
    }

    @After
    public void tearDown() {
        File file = new File(testFile);
        if (file.exists()) file.delete();
    }

    @Test
    public void testValidarCamposValido() {
        List<String> disciplinas = Arrays.asList("Matemática", "Português");
        assertTrue(service.validarCampos("Turma 1", "Engenharia", "Manhã", "2025/1", disciplinas));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeTurmaVazio() {
        service.validarCampos("", "Engenharia", "Manhã", "2025/1", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCursoVazio() {
        service.validarCampos("Turma 1", "", "Manhã", "2025/1", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurnoVazio() {
        service.validarCampos("Turma 1", "Engenharia", "", "2025/1", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnoSemestreVazio() {
        service.validarCampos("Turma 1", "Engenharia", "Manhã", "", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisciplinasVazia() {
        service.validarCampos("Turma 1", "Engenharia", "Manhã", "2025/1", Arrays.asList());
    }

    @Test
    public void testSalvarTurma() throws IOException {
        List<String> disciplinas = Arrays.asList("Matemática", "Português");
        service.salvarTurma("Turma 1", "Engenharia", "Manhã", "2025/1", disciplinas);
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("Turma 1;Engenharia;Manhã;2025/1;Matemática,Português", linha);
        }
    }
}
