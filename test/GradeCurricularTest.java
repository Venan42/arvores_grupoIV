import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class GradeCurricularTest {
    private GradeCurricular<Disciplina> grade;
    private Disciplina disciplina;

    @Before
    public void setUp() {
        grade = new GradeCurricular<>();
        disciplina = new Disciplina("MDI","Matemática Discreta I", 0);
    }
}
