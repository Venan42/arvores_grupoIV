public interface Arborizavel<T>{
    void inserirDisciplina(T nova);
    void removerDisciplina(String codigo);

    Nodo<T> buscarNodo(int codigo);

    String exibirArvore(Nodo<T> atual, int nivel);
    String visualizarArvore();

    boolean contemDisciplina(int codigo);
    boolean removerNodo(String codigo, Nodo<T> atual, Nodo<T> pai);
    boolean vincularPreRequisito(String codigoPai, String codigoFilho);   
}