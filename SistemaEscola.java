package app;

import manager.AlunoJpaController;
import model.Aluno;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import java.util.Random;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SistemaEscola {
    private static final Random r = new Random();
    
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("studingSistWeb_SupertrumpoMestre_jar_1.0-SNAPSHOTPU");

        AlunoJpaController alunoController = new AlunoJpaController(emf);
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n1-Inserir\t2-Listar\t3-Excluir\t4-Editar\t5-Torneio\t6-Lendárias\t7-Estatísticas\t0-Sair");
            int opcao = Integer.parseInt(entrada.readLine());
            if (opcao == 0) {
                break;
            }

            try {
                switch (opcao) {
                    case 1: // Inserir
                        Aluno aluno = new Aluno();
                        System.out.println("Nome:");
                        aluno.setNome(entrada.readLine());
                        System.out.println("Matrícula:");
                        aluno.setMatricula(entrada.readLine());
                        System.out.println("Ano de entrada:");
                        aluno.setEntrada(Integer.parseInt(entrada.readLine()));
                        aluno.setInteligencia(new Random().nextInt(100));
                        aluno.setForca(new Random().nextInt(100));
                        aluno.setCriatividade(new Random().nextInt(100));
                        alunoController.create(aluno);
                        System.out.println("Aluno inserido!");
                        break;

                    case 2: // Listar
                        alunoController.findAlunoEntities().forEach(System.out::println);
                        break;

                    case 3: // Excluir
                        System.out.println("Matrícula a excluir:");
                        alunoController.destroy(entrada.readLine());
                        System.out.println("Aluno excluído!");
                        break;

                    case 4: // Editar
                        System.out.println("Matrícula a editar:");
                        String mat = entrada.readLine();
                        System.out.println("Novo nome:");
                        String nome = entrada.readLine();
                        System.out.println("Novo ano de entrada:");
                        int ano = Integer.parseInt(entrada.readLine());
                        Aluno editado = new Aluno();
                        editado.setMatricula(mat);
                        editado.setNome(nome);
                        editado.setEntrada(ano);
                        alunoController.edit(editado);
                        System.out.println("Aluno atualizado!");
                        break;

                    case 5: // Torneio
                        miniTorneio(alunoController.findAlunoEntities(), alunoController);
                        break;

                    case 6: // Lendárias
                        alunoController.findAlunoEntities().stream()
                                .filter(Aluno::isLendaria)
                                .forEach(System.out::println);
                        break;

                    case 7: // Estatísticas
                        List<Aluno> alunos = alunoController.findAlunoEntities();
                        System.out.println("Total de cartas: " + alunos.size());
                        long lendarias = alunos.stream().filter(Aluno::isLendaria).count();
                        System.out.println("Cartas lendárias: " + lendarias);
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
        emf.close();
    }

    private static void miniTorneio(List<Aluno> alunos, AlunoJpaController controller) throws Exception {
        if (alunos.size() < 3) {
            System.out.println("Poucos alunos para o torneio!");
            return;
        }
        
        Aluno jogador = alunos.get(r.nextInt(alunos.size()));
        Aluno oponente = alunos.get(r.nextInt(alunos.size()));
        
        System.out.println("Sua carta:" + jogador);
        System.out.println("Carta do oponente: " + oponente);
        
        //comparação pelo atributo de entrada
        if (jogador.getEntrada() >= oponente.getEntrada()) {
            System.out.println("Você venceu! Ganhou uma carta lendária!");
           
            Aluno lendaria = new Aluno();
            lendaria.setMatricula("LEND-" + System.currentTimeMillis());
            lendaria.setNome("Carta Lendária");
            lendaria.setEntrada(2030);
            lendaria.setInteligencia(100);
            lendaria.setForca(100);
            lendaria.setCriatividade(100);
            controller.create(lendaria);
        } else {
            System.out.println("Infelizmente, não foi desta vez!");

        }
    }
}
