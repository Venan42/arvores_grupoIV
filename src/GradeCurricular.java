public class GradeCurricular<T> implements Arborizavel<T>{
    private Nodo<T> raiz;

    public GradeCurricular(){
        raiz = new Nodo<>();
    }

    @Override
    public void inserirDisciplina(T nova) {

    }

    @Override
    public void removerDisciplina(int codigo) {

    }

    @Override
    public Nodo<T> buscarNodo(int codigo) {
        return buscarNodoRec(codigo, raiz);
    }

    private Nodo<T> buscarNodoRec(int codigo, Nodo<T> nodo) {
        if(nodo == null)
            return null;
        if (codigo == ((Disciplina) nodo.getDado()).getCodigo())
            return nodo;
        for(Nodo<T> filho : nodo.getFilhos()){
            Nodo<T> aux = buscarNodoRec(codigo, filho);
            if(aux != null)
                return aux;
        }
        return null;
    }

    @Override
    public Nodo<T> buscarNodo(String nome) {
        return buscarNodoRec(nome, raiz);
    }

    private Nodo<T> buscarNodoRec(String nome, Nodo<T> nodo) {
        if(nodo == null)
            return null;
        if (nome.trim().equalsIgnoreCase(((Disciplina)nodo.getDado()).getNome()))
            return nodo;
        for(Nodo<T> filho : nodo.getFilhos()){
            Nodo<T> aux = buscarNodoRec(nome, filho);
            if(aux != null)
                return aux;
        }
        return null;
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
    public boolean contemDisciplina(int codigo) {
        return contemDisciplinaRec(codigo, raiz);
    }

    private boolean contemDisciplinaRec(int codigo, Nodo<T> nodo) {
        if (nodo.getDado() != null && codigo == ((Disciplina) nodo.getDado()).getCodigo()) {
            return true;
        }
        for (Nodo<T> filho : nodo.getFilhos()) {
            if (contemDisciplinaRec(codigo, filho)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removerNodo(int codigo, Nodo<T> atual, Nodo<T> pai) {
        return false;
    }

    @Override
    public boolean vincularPreRequisito(int codigoPai, int codigoFilho) {
        return false;
    }
}
