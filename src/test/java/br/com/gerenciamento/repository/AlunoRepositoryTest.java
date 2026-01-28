package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void saveAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Guimarães");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("REPO001");
        
        this.alunoRepository.save(aluno);
        Assert.assertNotNull(aluno.getId());
    }

    @Test
    public void findByStatusAtivo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria Luiza Bezerra");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("REPO002");
        this.alunoRepository.save(aluno);

        List<Aluno> alunosAtivos = this.alunoRepository.findByStatusAtivo();
        Assert.assertFalse(alunosAtivos.isEmpty());
    }

    @Test
    public void findByNomeContaining() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("REPO003");
        this.alunoRepository.save(aluno);

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingIgnoreCase("Vinicius");
        Assert.assertFalse(alunos.isEmpty());
    }

    @Test
    public void deleteAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Guimarães");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("REPO001");
        this.alunoRepository.save(aluno);

        Long id = aluno.getId();
        this.alunoRepository.deleteById(id);
        
        Assert.assertFalse(this.alunoRepository.findById(id).isPresent());
    }
}