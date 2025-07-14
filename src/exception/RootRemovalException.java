package exception;

public class RootRemovalException extends RuntimeException {
    public RootRemovalException(String message) {

        super("Não é permitido remover a raiz do curso (Bacharelado em Sistemas de Informação).");
    }
}
