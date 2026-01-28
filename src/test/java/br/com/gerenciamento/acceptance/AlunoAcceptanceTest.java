package br.com.gerenciamento.acceptance;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoAcceptanceTest {

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
    public void cadastrarAlunoPelaInterface() throws InterruptedException {
        driver.get("http://localhost:" + port + "/inserirAlunos");

        // 1. Preenche o Nome
        driver.findElement(By.id("nome")).sendKeys("Maria Luiza Acceptance");

        // 2. Seleciona o Curso (visto na imagem: ADMINISTRACAO)
        new Select(driver.findElement(By.id("curso"))).selectByVisibleText("ADMINISTRACAO");

        // 3. Preenche a Matrícula
        driver.findElement(By.id("matricula")).sendKeys("ACC2026");

        // 4. Seleciona o Turno (visto na imagem: MATUTINO)
        new Select(driver.findElement(By.id("turno"))).selectByVisibleText("MATUTINO");

        // 5. Seleciona o Status (visto na imagem: ATIVO)
        new Select(driver.findElement(By.id("status"))).selectByVisibleText("ATIVO");

        // 6. Clica no botão Salvar
        // Usei o seletor pelo texto do botão conforme a imagem
        driver.findElement(By.xpath("//button[contains(text(), 'Salvar')]")).click();

        // 7. Verificação
        // O sistema deve redirecionar para a listagem
        Thread.sleep(1000); // Pequena pausa para garantir o carregamento
        String corpoPagina = driver.getPageSource();
        Assert.assertTrue("O aluno cadastrado deveria aparecer na lista", 
                          corpoPagina.contains("Maria Luiza Acceptance"));
    }
}