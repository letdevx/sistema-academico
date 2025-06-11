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

public class LancamentoNotasServiceTest {
    private LancamentoNotasService service;
    private final String testFile = "src/test/resources/test_notas.txt";

    @Before
    public void setUp() {
        service = new LancamentoNotasService();
        service.setArquivoNotas(testFile);
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
        assertTrue(service.validarCampos("João", "Matemática", "9.5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAlunoVazio() {
        service.validarCampos("", "Matemática", "9.5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisciplinaVazia() {
        service.validarCampos("João", "", "9.5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotaVazia() {
        service.validarCampos("João", "Matemática", "");
    }

    @Test
    public void testSalvarNota() throws IOException {
        service.salvarNota("João", "Matemática", "9.5");
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("João;Matemática;9.5", linha);
        }
    }
}
