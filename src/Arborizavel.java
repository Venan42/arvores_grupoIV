public interface Arborizavel<T>{
    void inserirDisciplina(T nova);
    Disciplina buscarDisciplina(String codigo);

    String mostrarPreRequisitos(String codigo);

    Nodo<T> buscarNodo(String nome);
    Nodo<T> removerNodo(Nodo<T> atual, Nodo<T> pai);

    String exibirArvore(Nodo<T> atual, int nivel);
    String visualizarArvore();
    String removerDisciplina(String codigo);

    boolean contemDisciplina(String codigo);
    boolean vincularPreRequisito(String codigoPai, String codigoFilho);
}