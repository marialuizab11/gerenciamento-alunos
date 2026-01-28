package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioAcceptanceTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void setup() {
        this.driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Test
    public void cadastrarUsuarioPelaInterface() throws InterruptedException {
        // Acessa a página de cadastro
        driver.get("http://localhost:" + port + "/cadastro");

        // Preenche os campos de usuário
        driver.findElement(By.id("email")).sendKeys("aceitacao@teste.com");
        driver.findElement(By.id("user")).sendKeys("user_aceitacao");
        driver.findElement(By.id("senha")).sendKeys("senha123");

        // Clica no botão de salvar/cadastrar
        // Na maioria dos templates, o botão de submit é o que dispara o POST
        driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]")).click();

        // Aguarda o processamento
        Thread.sleep(1000);

        // O UsuarioController redireciona para "redirect:/" (página de login)
        String urlAtual = driver.getCurrentUrl();
        Assert.assertEquals("http://localhost:" + port + "/", urlAtual);
        
        // Verifica se a página de login carregou o campo de usuário
        Assert.assertTrue(driver.getPageSource().contains("Login"));
    }
}