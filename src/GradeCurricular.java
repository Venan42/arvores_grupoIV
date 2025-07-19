import exception.DisciplineNotFoundException;
import exception.DisciplineWithoutParentException;
import exception.RootRemovalException;

public class GradeCurricular<T> implements Arborizavel<T>{
    private Nodo<T> raiz;

    public GradeCurricular(){
        Disciplina curso= new Disciplina("Bacharelado em Sistemas de Informação", 0);
        raiz = new Nodo<>((T)curso);
    }

    @Override
    public void inserirDisciplina(T nova) {

    }

    @Override
    public String removerDisciplina(int codigo) {
        if (((Disciplina) raiz.getDado()).getCodigo() == codigo) {
            throw new RootRemovalException();
        }
        Nodo<T> alvo = buscarNodo(codigo);

        if (alvo == null) {
            throw new DisciplineNotFoundException(codigo);
        }

        Nodo<T> pai = alvo.getGenitor();
        if (pai == null) {
            throw new DisciplineWithoutParentException(codigo);
        }

       Nodo<T> subarvoreRemovida= removerNodo(alvo, pai);

        if (subarvoreRemovida == null) {
            throw new RuntimeException("Falha inesperada ao remover a disciplina.");
        }
        return "✅ Disciplina removida com sucesso. Subárvore excluída:\n"
                + exibirArvore(subarvoreRemovida, 0);

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
        if (atual == null || atual.getDado() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("  ".repeat(nivel));

        Disciplina d = (Disciplina) atual.getDado();
        sb.append("[").append(d.getCodigo()).append("] ")
                .append(d.getNome()).append(" (")
                .append(d.getCreditos()).append(" créditos)\n");

        for (Nodo<T> filho : atual.getFilhos()) {
            sb.append(exibirArvore(filho, nivel + 1));
        }

        return sb.toString();
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
    public Nodo<T> removerNodo(Nodo<T> atual, Nodo<T> pai) {
        boolean removido = pai.getFilhos().remove(atual);
        if (removido) {
            atual.setGenitor(null);
            return atual;
        }
        return null;
    }

    @Override
    public boolean vincularPreRequisito(int codigoPai, int codigoFilho) {
        return false;
    }
}
