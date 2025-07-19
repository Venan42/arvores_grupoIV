public interface Arborizavel<T>{
    void inserirDisciplina(T nova);
    Disciplina buscarDisciplina(int codigo);

    Nodo<T> buscarNodo(int codigo);
    Nodo<T> buscarNodo(String nome);
    Nodo<T> removerNodo(Nodo<T> atual, Nodo<T> pai);

    String exibirArvore(Nodo<T> atual, int nivel);
    String visualizarArvore();
    String removerDisciplina(int codigo);

    boolean contemDisciplina(int codigo);
    boolean vincularPreRequisito(int codigoPai, int codigoFilho);
}