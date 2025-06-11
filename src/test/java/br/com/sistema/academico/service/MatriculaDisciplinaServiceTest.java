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

public class MatriculaDisciplinaServiceTest {
    private MatriculaDisciplinaService service;
    private final String testFile = "src/test/resources/test_matriculas_disciplinas.txt";

    @Before
    public void setUp() {
        service = new MatriculaDisciplinaService();
        service.setArquivoMatriculasDisciplinas(testFile);
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
        assertTrue(service.validarCampos("123.456.789-00", "Turma 1", disciplinas));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCpfVazio() {
        service.validarCampos("", "Turma 1", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurmaVazia() {
        service.validarCampos("123.456.789-00", "", Arrays.asList("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisciplinasVazia() {
        service.validarCampos("123.456.789-00", "Turma 1", Arrays.asList());
    }

    @Test
    public void testSalvarMatricula() throws IOException {
        List<String> disciplinas = Arrays.asList("Matemática", "Português");
        service.salvarMatricula("123.456.789-00", "Turma 1", disciplinas);
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("123.456.789-00;Turma 1;Matemática,Português", linha);
        }
    }
}
