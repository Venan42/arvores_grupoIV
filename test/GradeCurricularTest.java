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
}
