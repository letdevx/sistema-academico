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

public class DisciplinaServiceTest {
    private DisciplinaService service;
    private final String testFile = "src/test/resources/test_disciplinas.txt";

    @Before
    public void setUp() {
        service = new DisciplinaService();
        service.setArquivoDisciplinas(testFile);
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
    public void testValidarNomeValido() {
        assertTrue(service.validarNome("Matemática"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeVazio() {
        service.validarNome("");
    }

    @Test
    public void testSalvarDisciplina() throws IOException {
        service.salvarDisciplina("Matemática");
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("Matemática", linha);
        }
    }
}
