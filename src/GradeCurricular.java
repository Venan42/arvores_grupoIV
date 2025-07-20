import exception.DisciplineNotFoundException;
import exception.DisciplineWithoutParentException;
import exception.RootRemovalException;

import java.util.List;

public class GradeCurricular<T> implements Arborizavel<T>{
    private Nodo<T> raiz;

    public GradeCurricular(){
        Disciplina curso= new Disciplina("BSI", "Bacharelado em Sistemas de Informação", 0);
        raiz = new Nodo<>((T)curso);
    }

    @Override
    public void inserirDisciplina(T nova) {
        if (buscarNodo(((Disciplina) nova).getNome()) != null) {
            throw new IllegalArgumentException("Disciplina já cadastrada!");
        }

        Nodo<T> novo = new Nodo<>(nova);
        novo.setGenitor(raiz);
        raiz.addFilho(novo);
    }

    @Override
    public Disciplina buscarDisciplina(String codigo) {
        Nodo<T> nodo = buscarNodoRec(codigo, raiz);
        if(nodo == null)
            throw new DisciplineNotFoundException("Disciplina não encontrada.");
        return (Disciplina) nodo.getDado();
    }

    @Override
    public String removerDisciplina(String codigo) {
        if (((Disciplina) raiz.getDado()).getCodigo().equals(codigo)) {
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
    public String mostrarPreRequisitos(String codigo) {
        Nodo<T> nodo = buscarNodo(codigo);
        if (nodo == null) {
            throw new DisciplineNotFoundException(codigo);
        }
        if (nodo.getGenitor() == null || nodo.getGenitor() == raiz) {
            return "Essa disciplina não possui um Pré-Requisito.";
        }
        StringBuilder sb = new StringBuilder(((Disciplina) nodo.getDado()).getNome());
        Nodo<T> nodoAtual = nodo.getGenitor();
        while (nodoAtual != null && nodoAtual != raiz) {
            Disciplina disciplinaPai = (Disciplina) nodoAtual.getDado();
            sb.insert(0, disciplinaPai.getNome() + " --> ");
            nodoAtual = nodoAtual.getGenitor();
        }

        return sb.toString();
    }

    @Override
    public Nodo<T> buscarNodo(String codigo) {
        return buscarNodoRec(codigo, raiz);
    }

    private Nodo<T> buscarNodoRec(String codigo, Nodo<T> nodo) {
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
    public String exibirArvore(Nodo<T> atual, int nivel) {
        if (atual == null || atual.getDado() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        // Prefixo para o nó atual
        String prefixo = "│   ".repeat(Math.max(0, nivel - 1));
        if (nivel > 0) {
            prefixo += "├── ";
        }

        Disciplina d = (Disciplina) atual.getDado();
        sb.append(prefixo)
                .append("📁 [").append(d.getCodigo()).append("] ")
                .append(d.getNome()).append("\n");

        // Recursão nos filhos
        List<Nodo<T>> filhos = atual.getFilhos();
        for (int i = 0; i < filhos.size(); i++) {
            sb.append(exibirArvore(filhos.get(i), nivel + 1));
        }

        return sb.toString();
    }

    @Override
    public String visualizarArvore() {
        StringBuilder sb = new StringBuilder();
        sb.append(exibirArvore(raiz, 0));
        return sb.toString();
    }

    @Override
    public boolean contemDisciplina(String codigo) {
        return contemDisciplinaRec(codigo, raiz);
    }

    private boolean contemDisciplinaRec(String codigo, Nodo<T> nodo) {
        if (nodo.getDado() != null && ((Disciplina) nodo.getDado()).getCodigo().equalsIgnoreCase(codigo)) {
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
    public boolean vincularPreRequisito(String codigoPai, String codigoFilho) {
        Nodo<T> pai = buscarNodo(codigoPai);
        Nodo<T> filho = buscarNodo(codigoFilho);
        
        if (pai == null) {
            throw new DisciplineNotFoundException(codigoPai);
        }
        if (filho == null) {
            throw new DisciplineNotFoundException(codigoFilho);
        }

        if (filho.getGenitor() != null) {
            filho.getGenitor().getFilhos().remove(filho);
        }

        Nodo<T> cursor = pai;
        while (cursor != null) {
            if (cursor == filho) {
                return false;
            }
            cursor = cursor.getGenitor();
        }

        pai.addFilho(filho);
        filho.setGenitor(pai);
        return true;
    }
}
