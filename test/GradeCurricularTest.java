import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GradeCurricularTest {
    private GradeCurricular<Disciplina> grade;
    private Disciplina disciplina;

    @BeforeEach
    public void setUp() {
        grade = new GradeCurricular<>();
        disciplina = new Disciplina("MDI","Matemática Discreta I", 0);
    }

    @Test
    void testVisualizarArvoreComMultiplosNiveis(){
        //Criando as disciplinas
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina poo = new Disciplina("POO001", "Programação Orientada a Objetos", 4);
        Disciplina bd = new Disciplina("BD001", "Banco de Dados", 4);
        Disciplina es = new Disciplina("ES001", "Estrutura de Dados", 4);

        //Inserindo as disciplinas
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(poo);
        grade.inserirDisciplina(bd);
        grade.inserirDisciplina(es);

        //Vinculando os pré-requisitos
        grade.vincularPreRequisito("BSI", "LP001"); //LP001 depende de BSI
        grade.vincularPreRequisito("BSI", "POO001"); //POO001 depende de BSI
        grade.vincularPreRequisito("POO001", "BD001"); //BD001 depende de POO001
        grade.vincularPreRequisito("LP001", "ES001"); //ES001 depende de LP001

        //Chamada do método
        String stringArvore = grade.visualizarArvore();

        //Estrutura de saída esperada
        String saidaEsperada = 
            "📁 [BSI] Bacharelado em Sistemas de Informação\n" +
            "├── 📁 [LP001] Lógica de Programação\n" +
            "│   ├── 📁 [ES001] Estrutura de Dados\n" + // ES001 é filho de LP001
            "├── 📁 [POO001] Programação Orientada a Objetos\n" +
            "│   ├── 📁 [BD001] Banco de Dados\n";

        //Comparando a impressão com a saida esperada
        assertEquals(saidaEsperada, stringArvore, "A visualização da árvore não corresponde ao formato esperado.");
    }
}
