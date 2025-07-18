import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GradeCurricularTest {
    private GradeCurricular<Disciplina> grade;
    private Disciplina disciplina;

    @Before
    public void setUp() {
        grade = new GradeCurricular<>();
        disciplina = new Disciplina("Matemática Discreta I", 0);
    }

    @Test
    public void buscarNodoPorNomeTest() {
        grade.inserirDisciplina(disciplina);
        assertEquals(disciplina, grade.buscarNodo("Matemática Discreta I").getDado());
    }

    @Test
    public void buscarNodoPorCodigoTest() {
        grade.inserirDisciplina(disciplina);
        assertEquals(disciplina, grade.buscarNodo(1).getDado());
    }

    @Test
    public void buscarNodoVazioTest() {
        assertNull(grade.buscarNodo(1));
    }
}
