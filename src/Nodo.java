import java.util.LinkedList;
import java.util.List;

public class Nodo<T> {
    private T dado;
    private Nodo<T> genitor;
    private List<Nodo<T>> filhos;

    public Nodo(){
        this(null);
    }

    public Nodo(T dado){
        this.dado = dado;
        filhos = new LinkedList<>();
    }

    public Nodo<T> getGenitor() {
        return genitor;
    }

    public void setGenitor(Nodo<T> genitor) {
        this.genitor = genitor;
    }

    public T getDado() {
        return dado;
    }

    public void setDado(T dado) {
        this.dado = dado;
    }

    public Nodo<T> getFilho(int i) {
        return filhos.get(i);
    }

    public void setFilho(int i, Nodo<T> nodo) {
        filhos.set(i, nodo);
    }
}
