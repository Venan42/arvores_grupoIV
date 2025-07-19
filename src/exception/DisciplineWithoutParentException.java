package exception;

public class DisciplineWithoutParentException extends RuntimeException {
    public DisciplineWithoutParentException(int codigo) {
        super("Erro interno: disciplina com código " + codigo + " não possui referência ao genitor.");
    }
}
