package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void saveUsuarioRepo() {
        Usuario usuario = new Usuario();
        usuario.setEmail("marialuizab11@gmail.com");
        usuario.setUser("marialuizab11");
        usuario.setSenha("123456");
        
        this.usuarioRepository.save(usuario);
        Assert.assertNotNull(usuario.getId());
    }

    @Test
    public void findByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste_busca_email@gmail.com");
        usuario.setUser("user_busca");
        usuario.setSenha("123456");
        this.usuarioRepository.save(usuario);

        Usuario encontrado = this.usuarioRepository.findByEmail("teste_busca_email@gmail.com");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("user_busca", encontrado.getUser());
    }

    @Test
    public void findByLogin() {
        Usuario usuario = new Usuario();
        usuario.setEmail("busca_login@teste.com");
        usuario.setUser("marialuiza_repo");
        usuario.setSenha("senha_forte");
        this.usuarioRepository.save(usuario);

        Usuario encontrado = this.usuarioRepository.buscarLogin("marialuiza_repo", "senha_forte");
        Assert.assertNotNull(encontrado);
    }

    @Test
    public void deleteUsuarioRepo() {
        Usuario usuario = new Usuario();
        usuario.setEmail("marialuizab11@gmail.com");
        usuario.setUser("marialuizab11");
        usuario.setSenha("123456");
        this.usuarioRepository.save(usuario);

        Long id = usuario.getId();
        this.usuarioRepository.deleteById(id);
        
        Assert.assertFalse(this.usuarioRepository.findById(id).isPresent());
    }
}