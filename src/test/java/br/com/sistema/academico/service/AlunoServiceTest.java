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

public class AlunoServiceTest {
    private AlunoService service;
    private final String testFile = "src/test/resources/test_alunos.txt";

    @Before
    public void setUp() {
        service = new AlunoService();
        service.setArquivoSaida(testFile);
        // Garante que o diretório existe
        File dir = new File("src/test/resources");
        if (!dir.exists()) dir.mkdirs();
        // Limpa o arquivo antes de cada teste
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
    public void testSalvarAluno() throws IOException {
        service.salvarAluno("Maria", "02/02/2001", "Feminino", "987.654.321-00", "12.345.678-9", "SSP", "03/03/2010", "Brasileira", "São Paulo", "Solteira");
        File file = new File(testFile);
        assertTrue(file.exists());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            assertEquals("Maria;02/02/2001;Feminino;987.654.321-00;12.345.678-9;SSP;03/03/2010;Brasileira;São Paulo;Solteira", linha);
        }
    }
}
