package exception;

public class DisciplineWithoutParentException extends RuntimeException {
    public DisciplineWithoutParentException(String codigo) {
        super("Erro interno: disciplina com código " + codigo + " não possui referência ao genitor.");
    }
}
