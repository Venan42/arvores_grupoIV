import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import exception.*;

/**
 * Classe de testes para a funcionalidade da {@link GradeCurricular}.
 * Garante que os métodos de manipulação da grade curricular funcionam como esperado.
 */
public class GradeCurricularTest {
    /**
     * Instância da {@link GradeCurricular} para ser testada.
     * Uma nova instância é criada antes de cada método de teste.
     */
    private GradeCurricular<Disciplina> grade;

     /**
     * Instância de uma {@link Disciplina} básica para uso em testes, se necessário.
     */
    private Disciplina disciplina;

    /**
     * Configura o ambiente de teste antes de cada método de teste ser executado.
     * Inicializa uma nova {@link GradeCurricular} e uma {@link Disciplina} padrão.
     */
    @BeforeEach
    public void setUp() {
        grade = new GradeCurricular<>();
        disciplina = new Disciplina("MDI","Matemática Discreta I", 0);
    }

    /**
     * Testa a inserção de uma disciplina na grade curricular.
     * Verifica se a disciplina inserida está presente na grade.
     */
    @Test
    void testInserirDisciplina() {
        // Inserindo disciplina
        Disciplina d1 = new Disciplina("ED001", "Estrutura de Dados I", 4);
        grade.inserirDisciplina(d1);
        assertTrue(grade.contemDisciplina("ED001"), "A disciplina ED001 deve estar presente na grade.");
    }

    /**
     * Testa se o método de inserção de disciplina lança uma {@link IllegalArgumentException}
     * quando uma disciplina com código duplicado é inserida.
     */
    @Test
    void testInserirDisciplinaDuplicadaLancaExcecao() {
        // Inserindo disciplina
        Disciplina d1 = new Disciplina("ED001", "Estrutura de Dados I", 4);
        grade.inserirDisciplina(d1);
        // Testando inserção de disciplina duplicada
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class, () -> grade.inserirDisciplina(new Disciplina(
                        "ED001", "Estrutura de Dados I", 4)), "Deve lançar " +
                        "IllegalArgumentException para disciplinas duplicadas."
        );
    }

    @Test
    void testBuscarDisciplinaExistente(){
        //Cria as disciplinas
        Disciplina d1 = new Disciplina("ED001", "Estrutura de Dados I", 4);
        Disciplina d2 = new Disciplina("LP001", "Lógica de Programação I", 4);

        //Inserindo na árvore
        grade.inserirDisciplina(d1);
        grade.inserirDisciplina(d2);

        //Executando o buscarDisciplina()
        Disciplina disciplinaEncontrada = grade.buscarDisciplina("ED001");

        //Verificação do retorno
        assertNotNull(disciplinaEncontrada, "A disciplina ED001 deve ser encontrada.");
        assertEquals("ED001", disciplinaEncontrada.getCodigo(), "O código da disciplina encontrada deve ser LP001.");
        assertEquals("Estrutura de Dados I", disciplinaEncontrada.getNome(), "O nome da disciplina encontrada deve ser 'Estrutura de Dados I'.");
    }

    @Test
    void testBuscarDisciplinaInexistente(){
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

        //Chama o método e testa
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class, // Esperamos que esta exceção seja lançada
            () -> grade.buscarDisciplina("SO001"), // Código que tentamos executar
            "Deve lançar DisciplineNotFoundException para disciplina inexistente." // Mensagem se o teste falhar
        );
    }

    @Test
    void testRemoverDisciplinaComSucesso() {
        // Inserindo disciplina
        Disciplina d1 = new Disciplina("ED001", "Estrutura de Dados I", 4);
        grade.inserirDisciplina(d1);
        // Teste de Remoção da disciplina
        String resultado = grade.removerDisciplina("ED001");
        assertFalse(grade.contemDisciplina("ED001"), "A disciplina ED001 deve ter sido removida.");
        assertTrue(resultado.contains("Disciplina removida com sucesso"), "Mensagem de sucesso esperada.");
    }

    @Test
    void testRemoverRaizLancaExcecao() {
        // Teste de exceção após tentativa de remoção da raíz
        RootRemovalException thrown = assertThrows(
            RootRemovalException.class,
            () -> grade.removerDisciplina("BSI"),
            "Deve lançar RootRemovalException ao tentar remover a raiz."
        );
    }

    @Test
    void testRemoverDisciplinaInexistenteLancaExcecao() {
        // Teste de exceção após tentativa de remoção de disciplina inexistente
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class,
            () -> grade.removerDisciplina("INEXISTENTE"),
            "Deve lançar DisciplineNotFoundException para disciplina inexistente."
        );
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
            "[BSI] Bacharelado em Sistemas de Informação\n" +
            "├── [LP001] Lógica de Programação\n" +
            "│   ├── [ES001] Estrutura de Dados\n" + // ES001 é filho de LP001
            "├── [POO001] Programação Orientada a Objetos\n" +
            "│   ├── [BD001] Banco de Dados\n";

        //Comparando a impressão com a saida esperada
        assertEquals(saidaEsperada, stringArvore, "A visualização da árvore não corresponde ao formato esperado.");
    }

    @Test
    void testBuscarNodoExistenteNaRaiz() {
        //Busca a disciplina raiz
        Nodo<Disciplina> nodoEncontrado = grade.buscarNodo("BSI");

        //Verificações
        assertNotNull(nodoEncontrado, "O nó da disciplina raiz 'BSI' deve ser encontrado.");
        assertEquals("BSI", nodoEncontrado.getDado().getCodigo(), "O código do dado no nó encontrado deve ser 'BSI'.");
        assertEquals("Bacharelado em Sistemas de Informação", nodoEncontrado.getDado().getNome(), 
                     "O nome do dado no nó encontrado deve ser 'Bacharelado em Sistemas de Informação'.");
    }

    @Test
    void testBuscarNodoExistenteNaoRaiz() {
        //Cria as disciplinas
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina poo = new Disciplina("POO001", "Programação Orientada a Objetos", 4);
        Disciplina bd = new Disciplina("BD001", "Banco de Dados", 4);
        
        //Insere as disciplinas
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(poo);
        grade.inserirDisciplina(bd);

        //Vinculando para criar a estrutura da árvore
        grade.vincularPreRequisito("BSI", "LP001");
        grade.vincularPreRequisito("BSI", "POO001");
        grade.vincularPreRequisito("POO001", "BD001");

        //Testa o método
        Nodo<Disciplina> nodoLP = grade.buscarNodo("LP001");
        Nodo<Disciplina> nodoBD = grade.buscarNodo("BD001");

        //Resultados do teste
        assertNotNull(nodoLP, "O nó da disciplina 'LP001' deve ser encontrado.");
        assertEquals("Lógica de Programação", nodoLP.getDado().getNome(), 
                     "O nome do dado no nó LP001 deve ser 'Lógica de Programação'.");

        assertNotNull(nodoBD, "O nó da disciplina 'BD001' deve ser encontrado.");
        assertEquals("Banco de Dados", nodoBD.getDado().getNome(), 
                     "O nome do dado no nó BD001 deve ser 'Banco de Dados'.");
    }
    
    @Test
    void testBuscarNodoInexistente() {
        //Cria as disciplinas
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina poo = new Disciplina("POO001", "Programação Orientada a Objetos", 4);
        Disciplina bd = new Disciplina("BD001", "Banco de Dados", 4);
        
        //Insere as disciplinas
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(poo);
        grade.inserirDisciplina(bd);

        //Vinculando para criar a estrutura da árvore
        grade.vincularPreRequisito("BSI", "LP001");
        grade.vincularPreRequisito("BSI", "POO001");
        grade.vincularPreRequisito("POO001", "BD001");

        //Testa o método e o lançamento da exceção
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class, // Esperamos que esta exceção seja lançada
            () -> grade.buscarDisciplina("DUMMY001"), // Código que tentamos executar
            "Deve lançar DisciplineNotFoundException para disciplina inexistente." // Mensagem se o teste falhar
        );
    }
}
