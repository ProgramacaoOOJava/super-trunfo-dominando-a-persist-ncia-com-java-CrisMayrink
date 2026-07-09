
package manager;

import model.Aluno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;



public class AlunoJpaController {
    //criada a partir do persistense.xml
    private EntityManagerFactory emf;
    
    //construtor
    public AlunoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    //metodo para obeter um EntityMnager novo
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    //criando um novo aluno
    public void create(Aluno aluno) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();              // inicia a transação
            em.persist(aluno);       // insere o objeto aluno
            tx.commit();             // confirma a transação
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback(); // desfaz caso dê erro
            throw new PersistenceException("Erro ao inserir aluno.", e);
        } finally {
            em.close();              // fecha conexão
        }
    }
    // READ lista todos os alunos usando NamedQuery
    public List<Aluno> findAlunoEntities() {
        EntityManager em = getEntityManager();
       try {
            // Executa a NamedQuery "Aluno.findAll" definida na entidade
            return em.createNamedQuery("Aluno.findAll", Aluno.class).getResultList();
        } finally {
            em.close(); // fecha conexão
        }
    } 
    // UPDATE: atualiza os dados de um aluno existente

    public void edit(Aluno aluno) throws Exception {
        EntityManager em = getEntityManager(); // abre conexão com o banco
        EntityTransaction tx = em.getTransaction(); // obtém a transação

        try {
            tx.begin();              // inicia a transação
            em.merge(aluno);         // atualiza os dados do aluno no banco
            tx.commit();             // confirma a transação
        }  catch (IllegalArgumentException e) {
        // ocorre se o objeto passado não for uma entidade gerenciada
            if (tx.isActive()) tx.rollback();
            throw new IllegalArgumentException("Aluno inválido para atualização.", e);
        } catch (javax.persistence.PersistenceException e) {
            // ocorre em problemas de persistência (ex: violação de chave)
            if (tx.isActive()) tx.rollback();
            throw new javax.persistence.PersistenceException("Erro de persistência ao atualizar aluno.", e);
        } finally {
            em.close(); // fecha o EntityManager
            }
    }
    //DELETE remove um aluno pela matricula
    public void destroy(String matricula) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // inicia transação
            Aluno aluno = em.find(Aluno.class, matricula); // busca aluno
            if (aluno == null) {
                throw new IllegalArgumentException("Aluno não encontrado para exclusão.");
            }
            em.remove(aluno); // remove do banco
            tx.commit();      // confirma transação
        } catch (IllegalArgumentException e) {
            if (tx.isActive()) tx.rollback();
            throw e; // repropaga exceção específica
        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            throw new PersistenceException("Erro de persistência ao excluir aluno.", e);
        } finally {
            em.close();
        }
    }
}

