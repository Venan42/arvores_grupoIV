import java.util.Iterator;
import java.util.List;

public class GradeCurricular<T> implements Arborizavel<T>{
    private Nodo<T> raiz;

    public GradeCurricular(){
        raiz = new Nodo<>();
    }

    @Override
    public void inserirDisciplina(T nova) {

    }

    @Override
    public void removerDisciplina(String codigo) {

    }

    @Override
    public Nodo<T> buscarNodo(int codigo) {
        return buscarNodoRec(codigo, raiz);
    }

    private Nodo<T> buscarNodoRec(int codigo, Nodo<T> nodo) {
        if (codigo == ((Disciplina) raiz.getDado()).getCodigo()) {
            return nodo;
        }
        Nodo<T> resp = null;
        List<Nodo<T>> auxFilhos = nodo.getFilhos();
        for(Nodo<T> filho : auxFilhos){
            resp = buscarNodoRec(codigo, filho);
        }
        return resp;
    }

    @Override
    public String exibirArvore(Nodo<T> atual, int nivel) {
        return "";
    }

    @Override
    public String visualizarArvore() {
        return "";
    }

    @Override
    public boolean contemDisciplina(String codigo) {
        return false;
    }

    @Override
    public boolean removerNodo(String codigo, Nodo<T> atual, Nodo<T> pai) {
        return false;
    }

    @Override
    public boolean vincularPreRequisito(String codigoPai, String codigoFilho) {
        return false;
    }
}
