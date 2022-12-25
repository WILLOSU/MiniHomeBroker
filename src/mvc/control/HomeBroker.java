package mvc.control;

import java.util.Scanner;
import mvc.model.DAO.AtivoDAO;
import mvc.model.DAO.ClienteDAO;
import mvc.model.DAO.ContaDAO;
import mvc.model.DAO.AtivoContaDAO;
import mvc.model.DAO.MovimentacaoContaDAO;
import mvc.model.DAO.OrdemDAO;
import mvc.model.DAO.ExecucaoDAO;

import mvc.model.Entilies.Conta;
import mvc.model.Entilies.Ativo;
import mvc.model.Entilies.AtivoConta;
import mvc.model.Entilies.Cliente;
import mvc.model.Entilies.Conta;
import mvc.model.Entilies.MovimentacaoConta;
import mvc.model.Entilies.Ordem;
import mvc.model.Entilies.Execucao;

import mvc.model.enums.EstadoOrdem;
import mvc.model.enums.MeioOperacao;
import mvc.model.enums.TipoOrdem;
import mvc.model.enums.TipoOperacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import mvc.view.Telas;

/**
 * @ professor Eduardo Silvestre
 * @author william de Sousa MotaW
 */
public class HomeBroker {

    ClienteDAO clienteDAO = new ClienteDAO();
    ContaDAO contaDAO = new ContaDAO();
    MovimentacaoContaDAO movContaDAO = new MovimentacaoContaDAO();
    AtivoDAO ativoDAO = new AtivoDAO();
    OrdemDAO ordemDAO = new OrdemDAO();
    ExecucaoDAO execucaoDAO = new ExecucaoDAO();
    AtivoContaDAO carteira = new AtivoContaDAO();
    Cliente clienteLogado;
    String user;

    private Conta contaAtual = null;

    Util util = new Util();

    Telas telas;

    Scanner scanner = new Scanner(System.in);

    /**
     * @throws java.lang.Exception
     */
    public HomeBroker() throws Exception {

        this.telas = new Telas();
        telas.inicio();
        
        fazerLogin();
          
        long id = Util.pegaClienteLogado().getId();
        int opcaoMenu;
        do {
            opcaoMenu = telas.menuPrin(clienteDAO, ativoDAO, contaDAO);
            switch (opcaoMenu) {
                case 1:
                    caseMenuCad(id);
                    break;
                case 2:
                    System.out.println("Ordens de compra\n" + ordemDAO.mostrarOrdensCompra(contaDAO, clienteDAO));
                    System.out.println("Ordens de Venda\n" + ordemDAO.mostrarOrdensVenda(contaDAO, clienteDAO));
                    telas.pausaDeTela();
                    break;
                case 3:
                    System.out.println(ordemDAO.executarOrdens(carteira, execucaoDAO, contaDAO, ativoDAO, movContaDAO, clienteDAO));
                    telas.pausaDeTela();
                    break;
                case 4:
                    telas.alterarData(contaDAO, movContaDAO, clienteDAO);
                    break;
                case 5:
                    if(usuarioEhADM()) {
                        telas.processarDividendos(carteira, ativoDAO, contaDAO, movContaDAO, clienteDAO);
                        
                    }else System.out.println("Opção apenas para administradores...");
                    telas.pausaDeTela();
                    break;
                case 6:
                    fazerLogin();
                    id = Util.pegaClienteLogado().getId();
                    break;
                case 0:
                    int confirm = JOptionPane.showConfirmDialog(null, "Deseja mesmo encerrar o programa?");
                    if(confirm != 0) opcaoMenu = -1;
                    break;

            }
        } while (opcaoMenu != 0);
        System.out.println("\n\n Obrigado por utilizar nosso Home Broker!\n Home Broker Finalizado.");
        System.exit(0);

    }

    /**
     * Método que manipula o case no menu cadastro
     *
     * @param id
     */
    public void caseMenuCad(long id) throws Exception {
        int opcaoMenu;

        do {
            opcaoMenu = telas.menuCadastro();
            switch (opcaoMenu) {
                case 1:
                    caseMenuCliente();
                    break;

                case 2:
                    if (usuarioEhADM()) {
                        caseMenuAtivos();
                    } else {
                        System.out.println("Opção apenas para administradores...");
                        telas.pausaDeTela();
                    }
                    break;

                case 3:
                    caseMenuConta(clienteDAO, contaDAO, id);
                    break;

                case 4:
                    caseOrdem();
                    break;

                case 5:
                   System.out.println(" Listagem de Execucao(oes) do sistema:");
                    if(usuarioEhADM()){
                        System.out.println(execucaoDAO.mostrarTodos(contaDAO, clienteDAO, ordemDAO));
                    }else{
                        Conta contaLogada = this.contaDAO.buscaContaPorCliente(Util.pegaClienteLogado(), clienteDAO);
                        System.out.println(execucaoDAO.mostrarTodos(contaDAO, clienteDAO, ordemDAO, contaLogada));
                    }
                    
                    telas.pausaDeTela();
//                    caseMenuExecucao(clienteDAO, contaDAO);
                    break;

                case 6:
                    System.out.println(carteira.mostrarTodos(Util.pegaClienteLogado(), contaDAO, ativoDAO, clienteDAO));
                    telas.pausaDeTela();
                    break;

                case 7:
                    System.out.println("teste");
                    break;

            }
        } while (opcaoMenu != 0);
        //        menuPrincipal(id);
    }

    // CASE CLIENTE
    public void caseMenuCliente() {
        long elementoASerAlterado;
        long elementoASerExcluido;

        int opcaoMenu;
        do {
            opcaoMenu = telas.menuCliente();
            switch (opcaoMenu) {
                case 1:

                    if (usuarioEhADM()) {
                        Cliente c = telas.criaCliente();
                        clienteDAO.adiciona(c);

                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                    }
                    telas.pausaDeTela();
                    break;

                case 2:
                    System.out.println(" Listagem de Cliente(s) do sistema:");
                    System.out.println(clienteDAO.mostrarTodos());
                   // System.out.print(" Qual Cliente (id) deseja Alterar: ");
                   // elementoASerAlterado = Integer.parseInt(scanner.nextLine());

                    break;

                case 3:
                    if (usuarioEhADM()) {
                        clienteDAO.mostrarTodos();

                        System.out.print(" Qual id de Cliente que deseja Alterar: ");
                        elementoASerAlterado = Integer.parseInt(scanner.nextLine());

                        if (telas.alterarCliente(clienteDAO.buscaClientePorId(elementoASerAlterado)) != null) {
                            System.out.println(" Cliente alterado com sucesso!");
                        } else {
                            System.out.println(" ATENÇÃO: Cliente não alterado.");
                        }
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    }

                case 4:

                    if (usuarioEhADM()) {
                        clienteDAO.mostrarTodos();

                        System.out.print(" Qual id da Orientação que deseja excluir: ");
                        elementoASerExcluido = Integer.parseInt(scanner.nextLine());

                        if (clienteDAO.remover((int) elementoASerExcluido)) {
                            System.out.println(" Orientação excluida com sucesso!");
                        } else {
                            System.out.println(" ATENÇÃO: Orientação inexistente.");
                        }
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    }

            }
        } while (opcaoMenu != 0);
        // menuCad(clienteDAO, ativoDAO, null, id);
    }

    // CASE ATIVOS
    public void caseMenuAtivos() throws Exception {
        long elementoASerAlterado;
        long elementoASerExcluido;
        int opcaoMenu;

        do {
            opcaoMenu = telas.menuAtivo();
            switch (opcaoMenu) {
                case 1:
                    if (usuarioEhADM()) {
                        Ativo a = telas.criaAtivo();
                        Conta contaBolsa = contaDAO.buscaContaBolsa(contaDAO.buscarContas(clienteDAO));

                        telas.validaInsercaoAtivo(ativoDAO, a, carteira, contaBolsa, ordemDAO);

                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");

                    }
                    telas.pausaDeTela();
                    break;

                case 2:
                    if (usuarioEhADM()) {
                        System.out.println(" Listagem do(s) Ativos:");
                        ativoDAO.imprimeLista();
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;

                    } else {
                        System.out.println(" \n\nAtenção: Essa opção é Liberada apenas para Administradores.");
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    }

                case 3:
                    if (usuarioEhADM()) {
                        ativoDAO.buscaTodos();

                        System.out.print(" Qual Ativo (id) deseja Alterar: ");
                        elementoASerAlterado = Long.parseLong(scanner.nextLine());

                        if (this.alterarAtivo(ativoDAO.buscaAtivoPorId(elementoASerAlterado)) != null) {
                            System.out.println(" Ativo alterado com sucesso!");
                        } else {
                            System.out.println(" ATENÇÃO: Atividade não alterado.");
                        }
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    }

                case 4:

                    if (usuarioEhADM()) {
                        ativoDAO.buscaTodos();

                        System.out.print(" Qual id do Ativo que deseja excluir: ");
                        elementoASerExcluido = Long.parseLong(scanner.nextLine());

                        if (ativoDAO.remover(elementoASerExcluido)) {
                            System.out.println(" Ativo excluido com sucesso!");
                        } else {
                            System.out.println(" Atenção: Ativo inexistente.");
                        }
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                        System.out.println(" Pressione Enter para continuar...");
                        scanner.nextLine();
                        break;

                    }
            }
        } while (opcaoMenu != 0);
        //menuCad(id);
    }

    // CASE CONTA CORRENTE
    public void caseMenuConta(ClienteDAO clienteDAO, ContaDAO contaDAO, long id) {
        int elementoASerExcluido;
        int opcaoMenu;

        do {
            opcaoMenu = telas.menuConta(id);
            switch (opcaoMenu) {
                case 1:
                    if (usuarioEhADM()) {
                        telas.validaInsercaoConta(clienteDAO, contaDAO, movContaDAO);

                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");
                    }
                    telas.pausaDeTela();
                    break;

                case 2:
                    System.out.println(" Listagem da(s) contas no sistema:\n");
                    System.out.println(contaDAO.mostrarTodos(clienteDAO));
                    telas.pausaDeTela();
                    break;

                case 3:
                    if (usuarioEhADM()) {
                        System.out.println(contaDAO.listaContas(clienteDAO));
                        telas.validaExclusaoConta(contaDAO);

                    } else {
                        System.out.println(" \n\nATENÇÃO: Essa opção é Liberada apenas para Administradores.");

                    }
                    telas.pausaDeTela();
                    break;

                case 4:
                    if (usuarioEhADM()) {
                        System.out.println(contaDAO.listaContas(clienteDAO));

                        System.out.print(" Qual conta você deseja operar: ");
                        long contaAOperar = Integer.parseInt(scanner.nextLine());

                        caseMenuOperacoes(clienteDAO, contaDAO, id, contaAOperar);
                    } else {
                        caseMenuOperacoes(clienteDAO, contaDAO, id, id);
                    }
                    break;

            }
        } while (opcaoMenu != 0);
//        menuCad(clienteDAO, null, contaDAO, id);
    }

    // MÉTODO OPERAÇÕES NA CONTA
    private void caseMenuOperacoes(ClienteDAO clienteDAO, ContaDAO contaDAO, long id, long contaAOperar) {
        int opcaoMenu;

        do {
            opcaoMenu = telas.menuOperacoes();
            switch (opcaoMenu) {
                // DEPÓSTICO
                case 1:
                    System.out.println(" Qual valor deseja depositar?");
                    BigDecimal valor = new BigDecimal(scanner.nextLine());
                    contaDAO.deposito(contaDAO.buscaContaPorId(contaAOperar, clienteDAO), valor, movContaDAO);
                    System.out.println(" Depósito realizado com sucesso!!");
                    System.out.println(" Pressione Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 2:
                    System.out.println(" Qual valor deseja sacar?");
                    valor = new BigDecimal(scanner.nextLine());
                    if (contaDAO.saque(contaDAO.buscaContaPorId(contaAOperar, clienteDAO), valor, movContaDAO)) {
                        System.out.println(" Saque efetuado com sucesso!!");
                    } else {
                        System.out.print("\n\n Saque não realizado!!\n Saldo disponpivel: ");
                        System.out.println(contaDAO.buscaContaPorId(contaAOperar, clienteDAO).getSaldo());
                    }
                    System.out.println(" Pressione Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 3:
                    System.out.println(" Qual valor do pagamento?");
                    valor = new BigDecimal(scanner.nextLine());
                    if (contaDAO.pagamento(contaDAO.buscaContaPorId(contaAOperar, clienteDAO), valor, movContaDAO, clienteDAO)) {
                        System.out.println(" Pagamento efetuado com sucesso!!");
                    } else {
                        System.out.print("\n\n Pagamento não realizado!!\n Saldo disponpivel: ");
                        System.out.println(contaDAO.buscaContaPorId(contaAOperar, clienteDAO).getSaldo());
                    }
                    System.out.println(" Pressione Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.println(contaDAO.listaContas(clienteDAO));
                    System.out.println(" Qual id da conta destino?");
                    int idDestino = Integer.parseInt(scanner.nextLine());
                    System.out.println(" Qual valor do pagamento?");
                    valor = new BigDecimal(scanner.nextLine());
                    if (contaDAO.transfere(contaDAO.buscaContaPorId(contaAOperar, clienteDAO), contaDAO.buscaContaPorId(idDestino, clienteDAO), valor, movContaDAO)) {
                        System.out.println(" Transferencia efetuado com sucesso!!");
                    } else {
                        System.out.print("\n\n Transferencia não realizado!!\n Saldo disponpivel: ");
                        System.out.println(contaDAO.buscaContaPorId(contaAOperar, clienteDAO).getSaldo());
                    }
                    System.out.println(" Pressione Enter para continuar...");
                    scanner.nextLine();
                    break;

                case 5:
                    Conta conta = contaDAO.buscaContaPorId(contaAOperar, clienteDAO);
                    System.out.println(movContaDAO.mostrarTodos(contaDAO, clienteDAO, ordemDAO));
                    telas.pausaDeTela();

                    break;
            }

        } while (opcaoMenu != 0);
//        menuCad(clienteDAO, null, contaDAO, id);
    }
    
    private void caseOrdem() {
        int opcaoMenu;

        do {
            opcaoMenu = telas.menuOrdem();
            Conta cc = contaDAO.buscaContaPorCliente(Util.pegaClienteLogado(), clienteDAO);
            if(cc != null){
                switch (opcaoMenu) {
                    case 1: {
                        // verifica se existe ativos disponíveis para compra
                        

                        System.out.println("Ordens de Venda\n" + ordemDAO.mostrarOrdensVenda(contaDAO, clienteDAO));

                        Ordem nova = new Ordem();
                        nova.setConta(cc);

                        System.out.print(" Informe o ID da ordem do ativo desejado: ");
                        nova.setTicker(ordemDAO.buscaTickerPorId(Long.parseLong(scanner.nextLine()), contaDAO, clienteDAO));

                        nova.setTipoOrdem(telas.pegaTipoOrdem());
                        nova.setValor(telas.pegaValorAtivo());
                        nova.setQtde(telas.pegaQtdeAtivo());

                        if(ordemDAO.adiciona(nova)){
                            contaDAO.pagamento(cc, BigDecimal.valueOf(10.00), movContaDAO, clienteDAO);
                        }

                        
                        telas.pausaDeTela();
                        break;
                    }

                    // mostra os ativos disponiveis do usuario para venda
                    case 2: {
                        // verifica se existe ativos disponíveis para venda
                        if (!cc.isContaBolsa()){
                        
                            

                            System.out.println("Meus ativos\n" + carteira.mostrarTodos(Util.pegaClienteLogado(), contaDAO, ativoDAO, clienteDAO));

                            Ordem nova = new Ordem();
                            nova.setConta(cc);

                            System.out.print(" Informe o ID da ativo desejado: ");
                            long idBusca = Long.parseLong(scanner.nextLine());
                            nova.setTicker(carteira.buscaTickerPorId(idBusca, contaDAO, ativoDAO, clienteDAO));

                            nova.setTipoOrdem(2);
                            nova.setValor(telas.pegaValorAtivo());
                            nova.setQtde(telas.pegaQtdeAtivo());

                            if(ordemDAO.adiciona(nova)){
                                contaDAO.pagamento(cc, BigDecimal.valueOf(10.00), movContaDAO, clienteDAO);
                            }

                            
                        } else System.out.println("A bolsa já vende automaticamente todos seus ativos");
                        telas.pausaDeTela();
                        break;
                    }

                }
            } else{
                System.out.println("Cliente não possui conta corrente... abra uma conta primeiro");
            }

        } while (opcaoMenu != 0);
    }

    private Ativo alterarAtivo(Ativo ativo) {

        System.out.print("\nNome da Empresa: ");
        String nomeEmpresa = scanner.nextLine();

        System.out.print("Ticker: ");
        String Ticker = scanner.nextLine();

        System.out.print("Favor inserir o novo valor do Ativo: ");
        BigDecimal precoInicial = new BigDecimal(scanner.nextLine());

        ativo.setNomeEmpresa(nomeEmpresa);
        ativo.setTicker(Ticker);

        ativo.setPrecoInicial(precoInicial);
        ativoDAO.altera(ativo);

        return ativo;
    }

    private boolean usuarioEhADM() {
        return 1 == Util.pegaClienteLogado().getTipoUsuario();
    }

    public static void main(String[] args) throws Exception {
        new HomeBroker();
    }

    private void fazerLogin() {
        do {
            Cliente temp = pegaPossivelCliente();
            clienteLogado = clienteDAO.buscaClienteLogin(temp);

            if (clienteLogado != null) {
                Util.registraCliente(clienteLogado);
            } else {
                System.out.println("Usuario não encontrado");
            }
        } while (clienteLogado == null);
    }
    
    private Cliente pegaPossivelCliente() {
        Cliente c = new Cliente();

        System.out.print(" Login do Usuário : ");
        c.setLogin(scanner.nextLine());
        System.out.print(" Senha : ");
        c.setSenha(scanner.nextLine());
        return c;
    }

}
