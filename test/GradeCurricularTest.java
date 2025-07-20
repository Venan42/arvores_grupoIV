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
        grade = new GradeCurricular<>("BSI", "Bacharelado em Sistemas de Informação");
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

    /**
     * Testa a busca por uma disciplina existente na grade curricular.
     * Verifica se a disciplina encontrada corresponde aos dados esperados.
     */
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

    /**
     * Testa se o método {@code buscarDisciplina} lança uma {@link DisciplineNotFoundException}
     * quando tenta buscar uma disciplina que não existe na grade curricular.
     */
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

    /**
     * Testa a remoção bem-sucedida de uma disciplina da grade curricular.
     * Verifica se a disciplina não está mais presente e se a mensagem de sucesso é retornada.
     */
    @Test
    void testRemoverDisciplina() {
        // Inserindo disciplina
        Disciplina d1 = new Disciplina("ED001", "Estrutura de Dados I", 4);
        grade.inserirDisciplina(d1);
        // Teste de Remoção da disciplina
        String resultado = grade.removerDisciplina("ED001");
        assertFalse(grade.contemDisciplina("ED001"), "A disciplina ED001 deve ter sido removida.");
        assertTrue(resultado.contains("Disciplina removida com sucesso"), "Mensagem de sucesso esperada.");
    }

    /**
     * Testa se o método {@code removerDisciplina} lança uma {@link RootRemovalException}
     * ao tentar remover a disciplina raiz da grade curricular.
     */
    @Test
    void testRemoverRaizLancaExcecao() {
        // Teste de exceção após tentativa de remoção da raíz
        RootRemovalException thrown = assertThrows(
            RootRemovalException.class,
            () -> grade.removerDisciplina("BSI"),
            "Deve lançar RootRemovalException ao tentar remover a raiz."
        );
    }

    /**
     * Testa se o método {@code removerDisciplina} lança uma {@link DisciplineNotFoundException}
     * ao tentar remover uma disciplina que não existe na grade curricular.
     */
    @Test
    void testRemoverDisciplinaInexistenteLancaExcecao() {
        // Teste de exceção após tentativa de remoção de disciplina inexistente
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class,
            () -> grade.removerDisciplina("INEXISTENTE"),
            "Deve lançar DisciplineNotFoundException para disciplina inexistente."
        );
    }

    /**
     * Testa se o método {@code removerDisciplina} lança uma {@link DisciplineWithoutParentException}
     * ao tentar remover uma disciplina que não possui pai (genitor).
     */
    @Test
    void testRemoverDisciplinaSemPaiLancaExcecao() {
        // Cria disciplina órfã não vinculada à árvore
        Disciplina orfa = new Disciplina("ORFA", "Órfã", 2);
        Nodo<Disciplina> nodoOrfa = new Nodo<>(orfa);
        // Força a inserção simulando um nó sem pai
        // Adiciona na árvore para que buscarNodo funcione
        grade.buscarNodo("BSI").addFilho(nodoOrfa);
        // Remove o genitor
        nodoOrfa.setGenitor(null);
        DisciplineWithoutParentException thrown = assertThrows(
            DisciplineWithoutParentException.class,
            () -> grade.removerDisciplina("ORFA"),
            "Deve lançar DisciplineWithoutParentException para disciplina sem pai."
        );
    }

    /**
     * Testa a remoção de uma disciplina que possui filhos,
     * verificando se toda a subárvore (disciplina e descendentes) é removida corretamente.
     */
    @Test
    void testRemoverDisciplinaComFilhosRemoveSubarvore() {
        // Inserindo as disciplinas
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina es = new Disciplina("ES001", "Estrutura de Dados", 4);
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(es);
        // Estrutura de Dados é filha de Lógica de Programação
        grade.vincularPreRequisito("LP001", "ES001");
        grade.vincularPreRequisito("BSI", "LP001");

        // Remove Lógica de Programação que tem Estrutura de Dados como filha
        String resultado = grade.removerDisciplina("LP001");

        // Verifica que ambos foram removidos
        assertFalse(grade.contemDisciplina("LP001"), "LP001 deve ter sido removida.");
        assertFalse(grade.contemDisciplina("ES001"), "ES001 (filha) também deve ter sido removida.");
        assertTrue(resultado.contains("Disciplina removida com sucesso"), "Mensagem de sucesso esperada.");
        assertTrue(resultado.contains("Lógica de Programação"), "A subárvore removida deve ser exibida.");
        assertTrue(resultado.contains("Estrutura de Dados"), "A subárvore removida deve ser exibida.");
    }

    /**
     * Testa o método {@code contemDisciplina} para verificar se identifica corretamente
     * a presença ou ausência de disciplinas na grade curricular.
     */
    @Test
    void testContemDisciplina() {
        // Insere disciplinas na grade
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina bd = new Disciplina("BD001", "Banco de Dados", 4);
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(bd);

        // Verifica disciplinas existentes
        assertTrue(grade.contemDisciplina("LP001"), "A grade deve conter a disciplina LP001.");
        assertTrue(grade.contemDisciplina("BD001"), "A grade deve conter a disciplina BD001.");

        // Verifica disciplina inexistente
        assertFalse(grade.contemDisciplina("ES001"), "A grade não deve conter a disciplina ES001.");
    }

    /**
     * Testa a visualização da árvore de disciplinas com múltiplos níveis,
     * garantindo que a saída formatada está correta.
     */
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

    /**
     * Testa a busca por um {@link Nodo} existente que representa a disciplina raiz.
     * Verifica se o nó encontrado e seus dados correspondem à raiz esperada.
     */
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

    /**
     * Testa a busca por um {@link Nodo} de uma disciplina existente que não é a raiz.
     * Garante que a busca em profundidade funciona corretamente.
     */
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

    /**
     * Testa se o método {@code vincularPreRequisito} lança uma {@link DisciplineNotFoundException}
     * ao tentar vincular um pré-requisito cujo pai não existe.
     */
    @Test
    void testVincularPreRequisitoPaiInexistenteLancaExcecao() {
        Disciplina filho = new Disciplina("ED001", "Estrutura de Dados", 4);
        grade.inserirDisciplina(filho);
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class,
            () -> grade.vincularPreRequisito("INEXISTENTE", "ED001"),
            "Deve lançar DisciplineNotFoundException para pai inexistente."
        );
    }

    /**
     * Testa se o método {@code vincularPreRequisito} lança uma {@link DisciplineNotFoundException}
     * ao tentar vincular um pré-requisito cujo filho não existe.
     */
    @Test
    void testVincularPreRequisitoFilhoInexistenteLancaExcecao() {
        Disciplina pai = new Disciplina("LP001", "Lógica de Programação", 4);
        grade.inserirDisciplina(pai);
        DisciplineNotFoundException thrown = assertThrows(
            DisciplineNotFoundException.class,
            () -> grade.vincularPreRequisito("LP001", "INEXISTENTE"),
            "Deve lançar DisciplineNotFoundException para filho inexistente."
        );
    }

    /**
     * Testa se o método {@code vincularPreRequisito} impede a criação de ciclos na árvore,
     * retornando {@code false} ao tentar criar um ciclo.
     */
    @Test
    void testVincularPreRequisitoCriaCicloRetornaFalse() {
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina es = new Disciplina("ES001", "Estrutura de Dados", 4);
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(es);
        grade.vincularPreRequisito("LP001", "ES001");
        // Tentar fazer Lógica de Programação depender de Estrutura de Dados
        boolean resultado = grade.vincularPreRequisito("ES001", "LP001");
        assertFalse(resultado, "Não deve permitir criar ciclo na árvore.");
    }

    /**
     * Testa se o método {@code vincularPreRequisito} permite a mudança de genitor (pai)
     * de uma disciplina já existente na árvore.
     */
    @Test
    void testVincularPreRequisitoMudaGenitorCorretamente() {
        Disciplina lp = new Disciplina("LP001", "Lógica de Programação", 4);
        Disciplina es = new Disciplina("ES001", "Estrutura de Dados", 4);
        Disciplina bd = new Disciplina("BD001", "Banco de Dados", 4);
        grade.inserirDisciplina(lp);
        grade.inserirDisciplina(es);
        grade.inserirDisciplina(bd);

        grade.vincularPreRequisito("LP001", "ES001");
        grade.vincularPreRequisito("BSI", "LP001");
        grade.vincularPreRequisito("BSI", "BD001");

        // Agora, muda o genitor de Estrutura de Dado para Banco de Dados
        boolean resultado = grade.vincularPreRequisito("BD001", "ES001");
        assertTrue(resultado, "A mudança de genitor deve ser permitida.");
        Nodo<Disciplina> nodoES = grade.buscarNodo("ES001");
        assertEquals("BD001", nodoES.getGenitor().getDado().getCodigo(), "O novo genitor de ES001 deve ser BD001.");
    }
    
    /**
     * Testa se o método {@code buscarDisciplina} (o método que usa {@code buscarNodo} internamente)
     * lança uma {@link DisciplineNotFoundException} ao buscar uma disciplina inexistente.
     * Este teste valida a exceção lançada ao buscar por uma disciplina que não está na árvore.
     */
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
