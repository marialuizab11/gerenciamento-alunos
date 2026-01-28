package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarPaginaInserirAlunos() throws Exception {
        this.mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void deveListarAlunos() throws Exception {
        this.mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void cadastrarAlunoComSucesso() throws Exception {
        this.mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Teste Integração")
                .param("turno", Turno.MATUTINO.name())
                .param("curso", Curso.ADMINISTRACAO.name())
                .param("status", Status.ATIVO.name())
                .param("matricula", "INT999"))
                .andExpect(status().is3xxRedirection()) 
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }

    @Test
    public void deveRemoverAluno() throws Exception {
        this.mockMvc.perform(get("/remover/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/alunos-adicionados"));
    }
}