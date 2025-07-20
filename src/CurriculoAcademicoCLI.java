import exception.DisciplineNotFoundException;
import exception.DisciplineWithoutParentException;
import exception.RootRemovalException;

import java.util.Scanner;

public class CurriculoAcademicoCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Qual o nome do curso da grade curricular? ");
        String nomeCurso = scanner.nextLine();
        System.out.print("Qual a sigla do curso da grade curricular? ");
        String sigla = scanner.nextLine();
        GradeCurricular<Disciplina> curriculo = new GradeCurricular<>(sigla, nomeCurso);

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
                        System.out.print("Código: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Créditos da disciplina: ");
                        int creditos = Integer.parseInt(scanner.nextLine());
                        Disciplina nova = new Disciplina(codigo, nome, creditos);
                        curriculo.inserirDisciplina(nova);

                        break;

                    case 2:
                        System.out.print("Código da disciplina a remover: ");
                        String codRemover = scanner.nextLine();
                        System.out.println(curriculo.removerDisciplina(codRemover));
                        break;

                    case 3:
                        System.out.print("Código da disciplina pré-requisito: ");
                        String codPai = scanner.nextLine();
                        System.out.print("Código da disciplina dependente: ");
                        String codFilho = scanner.nextLine();
                        boolean vinculado = curriculo.vincularPreRequisito(codPai, codFilho);
                        System.out.println("Vinculado? " + vinculado);
                        break;

                    case 4:
                        System.out.println(curriculo.visualizarArvore());
                        break;

                    case 5:
                        System.out.print("Digite o código da disciplina: ");
                        String codDisciplina = scanner.nextLine();
                        System.out.println(curriculo.mostrarPreRequisitos(codDisciplina));
                        break;
                    case 6:
                        System.out.print("Digite o código da disciplina: ");
                        String codBusca = scanner.nextLine();
                        System.out.println(curriculo.buscarDisciplina(codBusca));
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um número!");
            } catch (DisciplineNotFoundException | RootRemovalException | DisciplineWithoutParentException e) {
                System.out.println(e.getMessage());
            }
        } while (opcao != 0);

        scanner.close();
    }
}



