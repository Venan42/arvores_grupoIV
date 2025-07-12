public interface Arborizavel<T>{
    void inserirDisciplina(T nova);
    void removerDisciplina(int codigo);

    Nodo<T> buscarNodo(int codigo);
    Nodo<T> buscarNodo(String nome);

    String exibirArvore(Nodo<T> atual, int nivel);
    String visualizarArvore();

    boolean contemDisciplina(int codigo);
    boolean removerNodo(int codigo, Nodo<T> atual, Nodo<T> pai);
    boolean vincularPreRequisito(int codigoPai, int codigoFilho);
}