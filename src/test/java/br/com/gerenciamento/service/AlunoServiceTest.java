package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void salvarComSucesso() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Luiza");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA); 
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("654321");
        
        this.serviceAluno.save(aluno);
        
        boolean encontrado = this.serviceAluno.findAll().stream()
                .anyMatch(a -> a.getNome().equals("Maria Luiza"));
        Assert.assertTrue(encontrado);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void excluirAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Saulo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("000999");
        this.serviceAluno.save(aluno);

        this.serviceAluno.deleteById(aluno.getId());
        
        Aluno alunoDeletado = this.serviceAluno.getById(aluno.getId());
        Assert.assertNull(alunoDeletado);
    }

    @Test
    public void buscarPelaMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Paulo Rodrigues");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("888777");
        this.serviceAluno.save(aluno);

        aluno.setStatus(Status.INATIVO);
        this.serviceAluno.save(aluno);
        
        Aluno alunoAlterado = this.serviceAluno.getById(aluno.getId());
        Assert.assertEquals(Status.INATIVO, alunoAlterado.getStatus());
    }

    @Test
    public void buscarAlunosAtivos() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria do Socorro");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setMatricula("111222");
        this.serviceAluno.save(aluno);

        java.util.List<Aluno> ativos = this.serviceAluno.findAll();
        Assert.assertFalse(ativos.isEmpty());
    }
}