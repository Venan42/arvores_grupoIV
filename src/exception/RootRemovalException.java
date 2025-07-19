package exception;

public class RootRemovalException extends RuntimeException {
    public RootRemovalException() {
        super("Não é permitido remover a raiz do curso (Bacharelado em Sistemas de Informação).");
    }
}
