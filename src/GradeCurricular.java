import exception.DisciplineNotFoundException;
import exception.DisciplineWithoutParentException;
import exception.RootRemovalException;

import java.util.List;

/**
 * Código referente a implementação de um Currículo
 * Acadêmico com Pré-requisitos utilizando Árvores N-ária.
 * 
 * @author Gabryelle Beatriz Duarte Moraes
 * @author Vinícius Nunes de Andrade
 * @author Maria Eduarda Santos Campos
 * @author Kaique Silva Souza
 * @version 2.0
 * @since 2025-07-11
 */
public class GradeCurricular<T extends Disciplina> implements Arborizavel<T>{
    private Nodo<T> raiz;

    /**
     * Construtor.
     * 
     * @param sigla     A sigla do curso referente ao currículo acadêmico.
     * @param nomeCurso O nome do curso referente ao currículo acadêmico.
     */
    public GradeCurricular(String sigla, String nomeCurso){
        Disciplina curso= new Disciplina(sigla, nomeCurso, 0);
        raiz = new Nodo<>((T)curso);
    }

    /**
     * Insere uma disciplina na raiz da árvore/no curso.
     * 
     * @param nova A disciplina a ser inserida.
     * @throws IllegalArgumentException Se a disciplina já foi inserida.
     */
    @Override
    public void inserirDisciplina(T nova) {
        if (buscarNodo(nova.getCodigo()) != null) {
            throw new IllegalArgumentException("Disciplina já cadastrada!");
        }

        Nodo<T> novo = new Nodo<>(nova);
        novo.setGenitor(raiz);
        raiz.addFilho(novo);
    }

    /**
     * Busca uma disciplina pelo seu código.
     * 
     * @param codigo O código da disciplina.
     * @return A disciplina encontrada.
     * @throws DisciplineNotFoundException Se nenhuma disciplina foi
     *                                     encontrada.
     */
    @Override
    public Disciplina buscarDisciplina(String codigo) {
        Nodo<T> nodo = buscarNodoRec(codigo, raiz);
        if(nodo == null)
            throw new DisciplineNotFoundException(codigo);
        return nodo.getDado();
    }

    /**
     * Remove uma disciplina pelo seu código.
     * 
     * @param codigo O código da disciplina.
     * @return Uma mensagem de controle com o status da remoção.
     * @throws RootRemovalException Se houver tentativa de remover a raiz.
     * @throws DisciplineNotFoundException      Se nenhuma disciplina foi
     *                                          encontrada.
     * @throws DisciplineWithoutParentException Se a disciplina não possui
     *                                          referência ao seu genitor.
     * @throws RuntimeException                 Se a remoção falhar.
     */
    @Override
    public String removerDisciplina(String codigo) {
        if ((raiz.getDado()).getCodigo().equals(codigo)) {
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
        return "Disciplina removida com sucesso. Subárvore excluída:\n"
                + exibirArvore(subarvoreRemovida, 0);

    }

    /**
     * Remove um nodo e todos os seus filhos do currículo.
     * 
     * @param atual Nodo a ser removido.
     * @param pai Genitor do nodo a ser removido.
     * @return Nodo removido.
     */
    @Override
    public Nodo<T> removerNodo(Nodo<T> atual, Nodo<T> pai) {
        boolean removido = pai.getFilhos().remove(atual);
        if (removido) {
            atual.setGenitor(null);
            return atual;
        }
        return null;
    }

    /**
     * Mostra os pré-requsitos de uma disciplina pelo seu código.
     * 
     * @param codigo O código da disciplina.
     * @return Uma String com o caminho de pré-requisitos encontrados.
     * @throws DisciplineNotFoundException Se nenhuma disciplina foi
     *                                     encontrada.
     */
    @Override
    public String mostrarPreRequisitos(String codigo) {
        Nodo<T> nodo = buscarNodo(codigo);
        if (nodo == null) {
            throw new DisciplineNotFoundException(codigo);
        }
        if (nodo.getGenitor() == null || nodo.getGenitor() == raiz) {
            return "Essa disciplina não possui um Pré-Requisito.";
        }
        StringBuilder sb = new StringBuilder((nodo.getDado()).getNome());
        Nodo<T> nodoAtual = nodo.getGenitor();
        while (nodoAtual != null && nodoAtual != raiz) {
            Disciplina disciplinaPai = nodoAtual.getDado();
            sb.insert(0, disciplinaPai.getNome() + " --> ");
            nodoAtual = nodoAtual.getGenitor();
        }

        return sb.toString();
    }

    /**
     * Busca um nodo pelo seu código.
     * 
     * @param codigo O código da disciplina.
     * @return O nodo encontrado.
     */
    @Override
    public Nodo<T> buscarNodo(String codigo) {
        return buscarNodoRec(codigo, raiz);
    }

    /**
     * Busca um nodo de forma recursiva.
     * 
     * @param codigo O código da disciplina.
     * @param nodo A raiz da árvore.
     * @return O nodo encontrado.
     */
    private Nodo<T> buscarNodoRec(String codigo, Nodo<T> nodo) {
        if(nodo == null)
            return null;
        if ((nodo.getDado()).getCodigo().equalsIgnoreCase(codigo))
            return nodo;
        for(Nodo<T> filho : nodo.getFilhos()){
            Nodo<T> aux = buscarNodoRec(codigo, filho);
            if(aux != null)
                return aux;
        }
        return null;
    }

    /**
     * Exibe a árvore a partir de um determinado nível.
     * 
     * @param atual Nodo inicial da visualização.
     * @param nivel Nivel da visualização.
     * @return String com a visualização da sub-árvore.
     */
    @Override
    public String exibirArvore(Nodo<T> atual, int nivel) {
        if (atual == null || atual.getDado() == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        String prefixo = "│   ".repeat(Math.max(0, nivel - 1));
        if (nivel > 0) {
            prefixo += "├── ";
        }

        Disciplina d = atual.getDado();
        sb.append(prefixo)
                .append("[").append(d.getCodigo()).append("] ")
                .append(d.getNome()).append("\n");

        // Recursão nos filhos
        List<Nodo<T>> filhos = atual.getFilhos();
        for (int i = 0; i < filhos.size(); i++) {
            sb.append(exibirArvore(filhos.get(i), nivel + 1));
        }

        return sb.toString();
    }

    /**
     * Exibe a árvore completa.
     * 
     * @return String com a visualização da sub-árvore.
     */
    @Override
    public String visualizarArvore() {
        StringBuilder sb = new StringBuilder();
        sb.append(exibirArvore(raiz, 0));
        return sb.toString();
    }

    /**
     * Verifica se uma disciplina está no currículo.
     * 
     * @param codigo O código da disciplina.
     * @return Se a disciplina foi encontrada ou não (true ou false).
     */
    @Override
    public boolean contemDisciplina(String codigo) {
        return contemDisciplinaRec(codigo, raiz);
    }

    /**
     * Verifica se uma disciplina está no currículo de forma recursiva.
     * 
     * @param codigo O código da disciplina.
     * @param nodo   A raiz da árvore.
     * @return Se a disciplina foi encontrada ou não (true ou false).
     */
    private boolean contemDisciplinaRec(String codigo, Nodo<T> nodo) {
        if (nodo.getDado() != null && (nodo.getDado()).getCodigo().equalsIgnoreCase(codigo)) {
            return true;
        }
        for (Nodo<T> filho : nodo.getFilhos()) {
            if (contemDisciplinaRec(codigo, filho)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vincula um pré-requisito para determinada disciplina.
     * 
     * @param codigoPai   Pré-requisito a ser vinculado.
     * @param codigoFilho Dependente a ser vinculado.
     * @return Boolean indicando se a vinculação foi bem sucedida.
     * @throws DisciplineNotFoundException Se a disciplina não foi
     *                                     encontrada.
     */
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
