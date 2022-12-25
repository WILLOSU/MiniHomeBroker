package mvc.view;

import java.math.BigDecimal;
import mvc.model.Entilies.Ativo;
import mvc.model.DAO.AtivoDAO;
import mvc.model.Entilies.Cliente;
import mvc.model.DAO.ClienteDAO;
import mvc.model.Entilies.Conta;
import mvc.model.DAO.ContaDAO;
import mvc.model.Entilies.Execucao;
import mvc.model.DAO.ExecucaoDAO;
import mvc.model.Entilies.MovimentacaoConta;
import mvc.model.DAO.MovimentacaoContaDAO;
import mvc.model.Entilies.Ordem;
import mvc.model.DAO.OrdemDAO;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;

import mvc.control.Util;
import java.util.Scanner;
import javax.swing.JOptionPane;
import mvc.model.enums.EstadoOrdem;
import mvc.model.enums.MeioOperacao;
import mvc.model.enums.TipoOperacao;
import mvc.model.enums.TipoOrdem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.DAO.AtivoContaDAO;
import mvc.model.Entilies.AtivoConta;
/* @ professor Eduardo Silvestre
 * @author william de Sousa Mota/*
 */

public class Telas {

    Scanner scanner = new Scanner(System.in);
    Util util = new Util();
    ExecucaoDAO execucaoDAO = new ExecucaoDAO();
    AtivoDAO ativoDAO = new AtivoDAO();
    ClienteDAO clientesDAO = new ClienteDAO();
    Cliente Login;

    StringBuilder builder = new StringBuilder("");

    private String CPF;
    private Object clienteDAO;

    private Cliente atual = null;
    private Conta contaAtual = null;

    public void menuPrincipal(long id) {

    }

    // VALIDAÇÃO DE OPÇÃO DE CASE
    public int validaOpcao(int fim) {
        int opcao;
        do {
            System.out.print("Opção: ");
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextInt()) {
                System.out.println("Opção invalida!\n");
                System.out.print("Opção: ");
                scanner.next();
            }
            opcao = scanner.nextInt();

            if (opcao < 0 || opcao > fim) {
                System.out.println("Opção invalida!\n");
            }
        } while (opcao < 0 || opcao > fim);

        return opcao;
    }

    // MENU INICIO
    public void inicio() {
        StringBuilder builder = new StringBuilder("");

        builder.append(" ======================================");
        builder.append(" Home Broker - Login ");
        builder.append(" ====================================\n");
        builder.append("\n Entre com seus dados:\n");

        System.out.println(builder.toString());

    }

    // MENU PRINCIPAL 
    public int menuPrin(ClienteDAO clienteDAO, AtivoDAO ativoDAO, ContaDAO contaDAO) {

        StringBuilder builder = new StringBuilder("");

        builder.append("\n\n\n\n\n\n\n =============================================================\n");
        builder.append(" Home Broker \n");
        builder.append(" ========================================================/Home\n");
        builder.append(" Seja bem vindo ").append(pegaNomeETipoUsuarioLogado(clienteDAO)).append("\n");
//        builder.append(" Identificação: ").append(Util.pegaClienteLogado().getId()).append("\n");
        builder.append("\n Escolha a opcão desejada\n");
        builder.append(" 1 - Cadastros\n");
        builder.append(" 2 - Book de Ofertas\n");
        builder.append(" 3 - Executar Ordens\n");
        builder.append(" 4 - Calendario\n");
        builder.append(" 5 - Dividendos\n");
        builder.append(" 6 - Trocar Usuário\n\n");

        builder.append(" 0 - Sair do programa\n");

        System.out.println(builder.toString());

        int opcao = validaOpcao(6);
        return opcao;

    }

    // MENU CADASTRO
    public int menuCadastro() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Cadastros\n");
        builder.append(" ===========================================================/Home/Clientes\n");
        builder.append("\n Escolha a opcão desejada\n");
        builder.append("  1 - Clientes\n");
        builder.append("  2 - Ativos\n");
        builder.append("  3 - Conta Corrente\n");
        builder.append("  4 - Ordem\n");
        builder.append("  5 - Ordem Execuçao\n");
//        builder.append("  5 - Ultima Negociação\n");
        builder.append("  6 - Meus Ativos\n");
        builder.append("  7 - Estado de um Ativo\n\n");
        builder.append("  0 - Voltar para o menu\n");

        System.out.println(builder.toString());

        int opcao = validaOpcao(7);
        return opcao;

    }

    // MENU CLIENTE
    public int menuCliente() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" =================================================/Home/Cadastros/Clientes\n");
        builder.append("\n Escolha a opcão desejada\n\n");
        builder.append("  1 - Criar Cliente\n");
        builder.append("  2 - Listar Clientes\n");
        builder.append("  3 - Alterar Cliente\n");
        builder.append("  4 - Deletar Cliente\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(4);
        return opcao;
    }

    // MENU CLIENTE
    public int menuConta() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" =================================================/Home/Cadastros/Conta\n");
        builder.append("\n Escolha a opcão desejada\n\n");
        builder.append("  1 - Criar   Conta\n");
        builder.append("  2 - Listar  Conta\n");
        builder.append("  3 - Deletar Conta\n");
        builder.append("  4 - Operações na conta\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(4);
        return opcao;
    }
    
    // MENU CONTA
    public int menuConta(long id) {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" ====================================================/Home/Cadastros/Conta\n");
        builder.append("\n Escolha a opcão desejada\n\n");
        builder.append("  1 - Criar   Conta\n");
        builder.append("  2 - Listar  Conta\n");
        builder.append("  3 - Deletar Conta\n");
        builder.append("  4 - Operações na conta\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(4);
        return opcao;
    }

    // MENU Ativo
    public int menuAtivo() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" =================================================/Home/Cadastros/Ativos\n");
        builder.append("\n Escolha a opcão desejada\n\n");
        builder.append("  1 - Criar   Ativos\n");
        builder.append("  2 - Listar  Ativos\n");
        builder.append("  3 - Alterar Ativos\n");
        builder.append("  4 - Deletar Ativos\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(4);
        return opcao;
    }

    // MENU EXECUÇÃO
    public int menuExecucao() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" =================================================/Home/Cadastros/Execucao\n");
        builder.append("\n Escolha a opcão desejada\n\n");
        builder.append("  1 - Criar   Execuçao\n");
        builder.append("  2 - Listar  Execução\n");
        builder.append("  3 - Alterar Execução\n");
        builder.append("  4 - Deletar Execução\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(4);
        return opcao;
    }

    // MENU OPERAÇÕES NA CONTA
    public int menuOperacoes() {

        StringBuilder builder = new StringBuilder("");

        builder.append(" =========================================================================\n");
        builder.append(" Home Broker - Clientes\n");
        builder.append(" =================================================/Home/Cadastros/Operações\n");
        builder.append("\n Escolha a opcão desejada\n");
        builder.append("  1 - Depósito\n");
        builder.append("  2 - Saque\n");
        builder.append("  3 - Pagamento\n");
        builder.append("  4 - Transferência\n");
        builder.append("  5 - Extrato\n\n");
        builder.append("  0 - Voltar para o menu anterior\n");

        System.out.println(builder);

        int opcao = validaOpcao(5);
        return opcao;
    }

    public int menuExtrato() {

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(" =========================================================================\n");
        sb.append(" Home Broker - Clientes\n");
        sb.append(" ========================================/Home/Cadastros/Operações/Extrato\n");
        sb.append("\n Escolha a opcão desejada\n\n");
        sb.append("  1 - Últimos 5 dias\n");
        sb.append("  2 - Últimos 30 dias\n");
        sb.append("  3 - Por período\n\n");

        sb.append("  0 - Voltar para o menu anterior\n");

        System.out.println(sb);

        return validaOpcao(3);
    }

    public int menuOrdem() {

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(" =========================================================================\n");
        sb.append(" Home Broker - Clientes\n");
        sb.append(" ========================================/Home/Ativos & Ordens\n");
        sb.append("\n Escolha a opcão desejada\n\n");
        sb.append("  1 - Comprar Ativos\n");
        sb.append("  2 - Vender Ativos\n\n");

        
        sb.append("  0 - Voltar para o menu anterior\n");

        System.out.println(sb);

        return validaOpcao(2);

    }

    private StringBuilder pegaNomeETipoUsuarioLogado(ClienteDAO clienteDAO) {
        StringBuilder sb = new StringBuilder("");
        Cliente cliLog = Util.pegaClienteLogado();
        
        sb.append(cliLog.getNome());
        if(cliLog.getTipoUsuario()==1)
            sb.append(" - Administrador");
        else
            sb.append(" - Cliente");
        return sb;
    }

    // MÉTODO CRIANDO CLIENTES 
    public Cliente criaCliente() {
        Cliente c = new Cliente();

        System.out.print("\n Nome: ");
        String nome = scanner.nextLine();

        System.out.print(" Endereço: ");
        String endereco = scanner.nextLine();

        System.out.print(" Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print(" CPF: ");
        String CPF = scanner.nextLine();

        System.out.print(" Tipo (1 - Administrador / 2 - Cliente): ");
        int tipo = Integer.parseInt(scanner.nextLine());

        System.out.print(" Login: ");
        String login = scanner.nextLine();

        System.out.print(" Senha: ");
        String senha = scanner.nextLine();

        c.setNome(nome);
        c.setEndereco(endereco);
        c.setTelefone(telefone);
        c.setCPF(CPF);
        c.setTipoUsuario(tipo);
        c.setLogin(login);
        c.setSenha(senha);
        c.setDataModificacao();

        return c;
    }

    // MÉTODOS CRIANDO ATIVOS
    public Ativo criaAtivo() {
        Ativo a = new Ativo();

        System.out.print("\n Nome da Empresa: ");
        String nome = scanner.nextLine();

        System.out.print(" Ticher: ");
        String ticker = scanner.nextLine();

        System.out.print(" Quantidade de Ativos: ");
        int totalAtivos = Integer.parseInt(scanner.nextLine());

        System.out.print("Favor inserir o novo valor do Ativo: ");
        BigDecimal precoInicial = new BigDecimal(scanner.nextLine());
        
       
        a.setNomeEmpresa(nome);
        a.setTicker(nome);
        a.setTicker(ticker);
        a.setTotalAtivos(totalAtivos);
        a.setPrecoInicial(precoInicial);

        return a;
    }

    // MÉTODO ALTERA ATIVO
    //MÉTODO CRIA CONTA
    public Conta criaConta() {
        Conta a = new Conta();

        System.out.print("\n Nome do Cliente: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String CPF = scanner.nextLine();

        //a.setCliente(nome);
        // a.setCPF(CPF);
        return a;
    }

    //MÉTODO ALTERA CALENDARIO
    public void alterarData(ContaDAO contaDAO, MovimentacaoContaDAO mCD, ClienteDAO clienteDAO) {
        System.out.println("Informe a quantidade de dias a aumentar na data atual: " + util.FormataData(Util.pegaDataAtual()));
        long diasIncremento = Long.parseLong(scanner.nextLine());
        LocalDateTime dateTime;
        do {
            if (Util.pegaDataAtual().getDayOfMonth() == 15) {
                contaDAO.mensalidade(mCD, clienteDAO);
            }
            dateTime = Util.incrementaDias(1);
            diasIncremento--;
        } while (diasIncremento != 0);

        System.out.println("Data Alterada com sucesso!\nNova data: " + util.FormataData(dateTime) + "\n\nPressione Enter para continuar ...");
        scanner.nextLine();
    }

    //MÉTODO ALTERA EXECUCAO
    /*
    public boolean alterarExecucao(ExecucaoDAO execDAO, long id) {
        for (int i = 0; i < execDAO.execucao.length; i++) {
            if (execDAO.execucao[i] != null && execDAO.execucao[i].getId() == id) {

                System.out.print("\n Ordem: ");
                String tipoOrdem = scanner.nextLine();

                System.out.print(" Conta Compra: ");
                String contaCompra = scanner.nextLine();

                System.out.print(" Conta Venda: ");
                String contaVenda = scanner.nextLine();

                System.out.print(" Quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());

                System.out.print(" Tipo (1 - Administrador / 2 - Servidor): ");
                int tipo = Integer.parseInt(scanner.nextLine());

                if (execDAO.execucao.equals(i)) {
                    return false;
                } else {
                    execDAO.execucao[i].setTipoOrdem(tipoOrdem);
                    execDAO.execucao[i].setContaCompra(contaCompra);
                    execDAO.execucao[i].setContaVenda(contaVenda);
                    execDAO.execucao[i].setQuantidade(quantidade);

                    //ativoDAO.ativos[i].setDataModificacao();
                    return true;
                }
            }
        }

        return false;

    }

    */
    

    public void pausaDeTela() {
        System.out.println(" Pressione Enter para continuar...");
        scanner.nextLine();
    }

    public void validaAlteracaoSenhaCliente(ClienteDAO clienteDAO) {
        if (alterarSenhaCliente(clienteDAO)) {
            System.out.println(" Senha alterada com sucesso!");
        } else {
            System.out.println(" ATENÇÃO: Senha não alterada");
        }
    }
   

    public void validaExclusaoConta(ContaDAO contaDAO) {

        System.out.print(" Qual conta você deseja excluir: ");
        int elementoASerExcluido = Integer.parseInt(scanner.nextLine());
        long id = Util.pegaClienteLogado().getId();

//        if (contaDAO.remover(elementoASerExcluido, id)) {
//            System.out.println(" Conta Excluída com com sucesso!");
//        } else {
//            System.out.println(" ATENÇÃO: conta inexistente.");
//        }
    }

    // VER ATIVOS
    public void verAtivo(String ativos) {
        System.out.println("Ativos existente: ");
    }

    public int getId() {
        final int id = 0;
        System.out.println("Id: " + id);

        return id;
    }

    public BigDecimal setValor() {

        final BigDecimal valor = new BigDecimal(scanner.nextLine());

        return valor;
    }

    public Ordem novaOrdem(Conta atual, Ativo ativo, int nAtivo) {
        // recebe as informacoes da ordem
        System.out.print(" TIPO: ");
        String tipo = scanner.nextLine();

        BigDecimal valor;
        if (tipo.equals(TipoOrdem.ZERO)) {
            valor = ativo.getPrecoInicial();
        } else {
            System.out.print("Favor inserir o novo valor do Ativo: ");

            valor = new BigDecimal(scanner.nextLine());

            System.out.print(" Quantidade: ");
            int qtd = Integer.parseInt(scanner.nextLine());

            // verifica o saldo e a quantidade de ativos disponivel
            if (atual.getSaldo().subtract(valor.multiply(BigDecimal.valueOf(qtd))).compareTo(BigDecimal.ZERO) >= 0) {
                if (tipo.equals(TipoOrdem.ZERO)) {
                    // verifica se possui a quantidade necessaria para a compra do tipo ordem zero
                    if (ativo.getTotalAtivos() - qtd >= 0) {
                        Ordem novo = new Ordem();

                        novo.setConta(atual);
                        novo.setTicker(ativo.getTicker());

                        System.out.println("Ordem Criada!!");

                        return novo;
                    } else {
                        System.out.println("Nao possui essa quantidade de ativos disponivel");
                    }
                } else {
                    Ordem novo = new Ordem();

                    novo.setConta(atual);
                    novo.setTicker(ativo.getTicker());

                    if (tipo.equals(TipoOrdem.VENDA)) {
                        if (qtd <= nAtivo) {
                            System.out.println("Ordem Criada");

                            return novo;
                        } else {
                            System.out.println("Nao possui essa quantidade de ativos disponivel");

                        }
                    } else {
                        System.out.println("Ordem Criada");
                        return novo;
                    }
                }
            } else {
                System.out.println("Saldo Insuficiente!");
            }

            return null;
        }
        return null;

    }

    public String getTicker() {
        System.out.print(" Ticher: ");
        String ticker = scanner.nextLine();

        return ticker;
    }

    public void verOrdem(String ordens) {
        String Ordens = scanner.nextLine();
    }

    public void verContaSaldo(String saldo) {
        System.out.print(" Saldo: ");
        String Saldo = scanner.nextLine();

    }

    public int pegaTipoOrdem() {
        if(Util.pegaClienteLogado().getQtdOrdem0()<3) {
            System.out.print("Informe o tipo de ordem\n0 - ordem 0\n1 - Compra\n");
            int opc = validaOpcao(1);
            if(opc==0) Util.pegaClienteLogado().setQtdOrdem0(Util.pegaClienteLogado().getQtdOrdem0()+1);
            return opc;
        } else return 1;
    }

    public BigDecimal pegaValorAtivo() {
        System.out.print("Informe o valor: ");
        return new BigDecimal(scanner.nextLine());
    }

    public int pegaQtdeAtivo() {
        System.out.print("Informe a quantidade que deseja: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public boolean alterarSenhaCliente(ClienteDAO clienteDAO) {

        return false;
    }
   // MÉTODO ALTERA CLIENTES   
    public Cliente alterarCliente(Cliente cliente) {
     
          //Cliente c = new Cliente();
          
        System.out.print("\n Nome: ");
        String nome = scanner.nextLine();

        System.out.print(" Endereco: ");
        String endereco = scanner.nextLine();

        System.out.print(" CPF: ");
        String cpf = scanner.nextLine();

        System.out.print(" Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print(" Login: ");
        String login = scanner.nextLine();

        System.out.print(" Senha: ");
        String senha = scanner.nextLine();

        System.out.print(" Tipo (1 - Administrador / 2 - Cliente): ");
        int tipo = Integer.parseInt(scanner.nextLine());

        
            cliente.setNome(nome);
            cliente.setEndereco(endereco);
            cliente.setCPF(cpf);
            cliente.setTelefone(telefone);
            cliente.setLogin(login);
            cliente.setSenha(senha);
            cliente.setTipoUsuario(tipo);
            
            clientesDAO.altera(cliente);
            return cliente;

        }

   public void validaInsercaoAtivo(AtivoDAO ativoDAO, Ativo a, AtivoContaDAO relacaoAtivoConta, Conta contaBolsa, OrdemDAO ordemDAO) throws Exception {
        if (ativoDAO.temAtivo(a)) {
            System.out.println(" ATENÇÃO: Ativo não inserido. Ativo já cadastrado.");
        } else if (ativoDAO.adiciona(a)) {
            System.out.println(" Ativo inserido com sucesso!");
            int totalAtivos = a.getTotalAtivos();
            
            //essa parte irá inserir automaticamente cada novo ativo crado diretamente na carteira da bolsa
            AtivoConta ac = criaAtivoConta(a, contaBolsa,  totalAtivos);
            relacaoAtivoConta.adiciona(ac);
            //essa parte irá inserir cada ativo criado diretamente no book de oferta
            Ordem o = criaOrdem(ordemDAO, contaBolsa, a);
            ordemDAO.adiciona(o);

        } else {
            System.out.println(" ATENÇÃO: Ativo não inserido. Limite de cadastro excedido.");
        }
    }
   
    public void validaInsercaoConta(ClienteDAO clienteDAO, ContaDAO contaDAO, MovimentacaoContaDAO mCD) {
        System.out.println(clienteDAO.mostrarClientesDisponiveis());
        System.out.print(" Informe o ID do cliente a criar conta: ");
        long idCli = Long.parseLong(scanner.nextLine());
        Conta cc = new Conta();
        cc.setCliente(clienteDAO.buscaClientePorId(idCli));
        cc.getCliente().setTemConta(true);
        clienteDAO.altera(cc.getCliente());
        contaDAO.creditoInicialConta(clienteDAO, mCD, cc);
        contaDAO.adiciona(cc, mCD);
    }
    
    public Conta criaClienteConta(ClienteDAO clienteDAO) {
        Conta c = new Conta();

        System.out.println("Cliente: ");
        clienteDAO.mostrarClientesDisponiveis();

        System.out.println("Digite o ID: ");
        long idLido = Long.parseLong(scanner.nextLine());
        Cliente a = clienteDAO.buscaClientePorId(idLido);
        a.setTemConta(true);

        c.setCliente(a);

        return c;
    }

    public AtivoConta criaAtivoConta(Ativo a, Conta contaAtivo, int totalAtivos) {
        AtivoConta novo = new AtivoConta();
        
        novo.setAtivo(a);
        novo.setConta(contaAtivo);
        novo.setTotalAtivos(totalAtivos);
        novo.setValorCompra(a.getPrecoAtual());
        
        return novo;
    }
     
     private Ordem criaOrdem(OrdemDAO ordemDAO, Conta contaAtivo, Ativo a) {
        Ordem nova = new Ordem();
        
        nova.setConta(contaAtivo);
        nova.setTicker(a.getTicker());
        nova.setTipoOrdem(2);
        nova.setValor(a.getPrecoInicial());
        nova.setQtde(a.getTotalAtivos());
        
        return nova;
         
    }
     
     public void processarDividendos(AtivoContaDAO acDAO, AtivoDAO ativoDAO, ContaDAO contaDAO, MovimentacaoContaDAO movContaDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = acDAO.buscarTodos(contaDAO, ativoDAO, clienteDAO);
        
        System.out.println(acDAO.mostrarTodos(contaDAO, ativoDAO, clienteDAO));
        System.out.print("\n\n Informe o ID que contem o ativo a pagar: ");
        long idCarteira = Long.parseLong(scanner.nextLine());
        System.out.println("\n Informe o valor a ser pago em dividendos: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());

        String ticker = acDAO.buscaTickerPorId(idCarteira, contaDAO, ativoDAO, clienteDAO);

        acDAO.pagarDividendos(ticker, valor, contaDAO, movContaDAO, ativoDAO, clienteDAO);
    }
   
}

   


