public class Disciplina {
    private String nome;
    private String codigo;
    private int creditos;

    public Disciplina(String codigo, String nome, int creditos) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
