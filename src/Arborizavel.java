public interface Arborizavel<T>{
    void inserirDisciplina(T nova);
    void removerDisciplina(String codigo);

    Nodo buscarNodo(String codigo);

    String exibirArvore(Nodo atual, int nivel);
    String visualizarArvore();

    boolean contemDisciplina(String codigo);
    boolean removerNodo(String codigo, Nodo atual, Nodo pai);
    boolean vincularPreRequisito(String codigoPai, String codigoFilho);   
}