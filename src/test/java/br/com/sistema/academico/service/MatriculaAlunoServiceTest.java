package br.com.sistema.academico.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MatriculaAlunoServiceTest {
    private MatriculaAlunoService service;
    private final String testFile = "src/test/resources/test_matriculas.txt";

    @Before
    public void setUp() {
        service = new MatriculaAlunoService();
        service.setArquivoMatriculas(testFile);
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
        assertTrue(service.validarCampos("123.456.789-00", "Turma 1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCpfVazio() {
        service.validarCampos("", "Turma 1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTurmaVazia() {
        service.validarCampos("123.456.789-00", "");
    }

    @Test
    public void testSalvarMatricula() throws IOException {
        service.salvarMatricula("123.456.789-00", "Turma 1");
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("123.456.789-00;Turma 1", linha);
        }
    }
}
