package exception;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(String codigo) {
        super("Disciplina com código " + codigo + " não encontrada.");
    }
}
