package exception;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(int codigo) {
        super("Disciplina com código " + codigo + " não encontrada.");
    }
    public DisciplineNotFoundException(String mensagem) {
        super(mensagem);
    }
}
