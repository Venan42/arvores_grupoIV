//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import exception.DisciplineNotFoundException;
import exception.DisciplineWithoutParentException;
import exception.RootRemovalException;

import java.util.Scanner;

public class CurriculoAcademicoCLI {
    public static void main(String[] args) {
        GradeCurricular<Disciplina> curriculo = new GradeCurricular<>();
        Scanner scanner = new Scanner(System.in);

        int opcao = 0;

        do {
            try {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Inserir Disciplina \n" +
                        "2. Remover Disciplina \n" +
                        "3. Vincular Pré-requisito \n" +
                        "4. Visualizar Árvore \n" +
                        "5. Visualizar Pré-requisitos \n" +
                        "6. Buscar Disciplina \n" +
                        "0. Sair \n" +
                        "Escolha uma opção: ");

                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Créditos da disciplina: ");
                        int creditos = Integer.parseInt(scanner.nextLine());
                        Disciplina nova = new Disciplina(nome, creditos);
                        curriculo.inserirDisciplina(nova);

                        break;

                    case 2:
                        System.out.print("Código da disciplina a remover: ");
                        int codRemover = Integer.parseInt(scanner.nextLine());
                        curriculo.removerDisciplina(codRemover);
                        break;

                    case 3:
                        System.out.print("Código da disciplina pré-requisito: ");
                        int codPai = Integer.parseInt(scanner.nextLine());
                        System.out.print("Código da disciplina dependente: ");
                        int codFilho = Integer.parseInt(scanner.nextLine());
                        boolean vinculado = curriculo.vincularPreRequisito(codPai, codFilho);
                        System.out.println("Vinculado? " + vinculado);
                        break;

                    case 4:
                        System.out.println(curriculo.visualizarArvore());
                        break;

                    case 5:
                        System.out.print("Digite o código da disciplina: ");
                        String codigoBusca = scanner.nextLine();
                        System.out.println(curriculo.buscarDisciplina(codigoBusca));
                        break;
                    case 6:
                        System.out.println(MostrarpreRequisitos());
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número!");
            } catch (DisciplineNotFoundException e) {
                System.out.println("Disciplina não encontrada!");
            } catch (RootRemovalException e) {
                System.out.println("Remoção de raiz inválida.");
            } catch (DisciplineWithoutParentException e) {
                System.out.println();
            }
        } while (opcao != 0);

        scanner.close();
    }
}



