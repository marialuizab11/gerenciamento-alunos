package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario_novo@gmail.com");
        usuario.setUser("user_novo");
        usuario.setSenha("123456");
        
        this.serviceUsuario.salvarUsuario(usuario);
        
        Assert.assertNotNull(usuario.getId());
    }

    @Test
    public void efetuarLogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario_login@gmail.com");
        usuario.setUser("user_login");
        usuario.setSenha("senha123");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario logado = this.serviceUsuario.loginUser(usuario.getUser(), Util.md5("senha123"));
        Assert.assertNotNull(logado);
    }

    @Test
    public void loginInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario_invalido@gmail.com");
        usuario.setUser("user_invalido");
        usuario.setSenha("321");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario logado = this.serviceUsuario.loginUser("user_invalido", "senha_errada");
        Assert.assertNull(logado);
    }

    @Test
    public void verificarCriptografia() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario_crypto@gmail.com");
        usuario.setUser("user_crypto");
        usuario.setSenha("123456");
        this.serviceUsuario.salvarUsuario(usuario);

        Assert.assertNotEquals("123456", usuario.getSenha());
    }
}