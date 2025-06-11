package br.com.sistema.academico.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LoginServiceTest {
    private final LoginService service = new LoginService();

    @Test
    public void testAutenticacaoCorreta() {
        assertTrue(service.autenticar("admin", "123"));
    }

    @Test
    public void testUsuarioIncorreto() {
        assertFalse(service.autenticar("usuario", "123"));
    }

    @Test
    public void testSenhaIncorreta() {
        assertFalse(service.autenticar("admin", "senhaerrada"));
    }

    @Test
    public void testUsuarioESenhaIncorretos() {
        assertFalse(service.autenticar("user", "senha"));
    }
}
