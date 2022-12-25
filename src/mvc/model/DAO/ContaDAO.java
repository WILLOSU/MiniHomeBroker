/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import mvc.model.Entilies.Cliente;
import mvc.model.Entilies.Conta;
import java.util.HashSet;
import java.util.Scanner;
import mvc.control.Util;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.model.Entilies.Ativo;
import mvc.model.Entilies.MovimentacaoConta;
import mvc.model.enums.TipoOperacao;
import mvc.model.enums.TipoUsuario;

/* @ professor Eduardo Silvestre
 *  @author William de Sousa Mota
 *  @author Lucas Feitosa

 */
public class ContaDAO {

    public Conta[] contascorrente = new Conta[50]; // TROCAR PARA LIST ??????
    Util util = new Util();
    Scanner scanner = new Scanner(System.in);

    public ContaDAO() {

    }

    public ContaDAO(ClienteDAO clienteDAO) {

    }


    public boolean adiciona(Conta elemento, MovimentacaoContaDAO mCD) {
        String sql = "INSERT INTO conta"
                + "(cliente, contaBolsa, saldo, dataCriacao, dataModificacao) "
                + "VALUES(?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getCliente().getId());
            stmt.setBoolean(2, elemento.isContaBolsa());
            stmt.setBigDecimal(3, elemento.getSaldo());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            
            
            stmt.execute();
            result = true;

            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }
    
    public void creditoInicialConta(ClienteDAO clienteDAO, MovimentacaoContaDAO mCD, Conta novaConta) {
        BigDecimal valor = new BigDecimal(20000.0);
        transferencia(buscaContaBolsa(buscarContas(clienteDAO)), novaConta, valor);
        String descricaoMov = "Boas Vindas de Abertura de Conta";
//        mCD.insereMovimentacao(buscaContaBolsa(buscarContas(clienteDAO)), novaConta, valor, descricaoMov);
    }

    public boolean altera(Conta elemento) {
        boolean result = false;
        String sql = "update conta set saldo = ?, dataModificacao = ?  where idConta = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setBigDecimal(1, elemento.getSaldo());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setLong(3, elemento.getId());
            
            stmt.execute();
            result = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    public void mensalidade(MovimentacaoContaDAO mCD, ClienteDAO clienteDAO){
        Conta contaBolsa = buscaContaBolsa(buscarContas(clienteDAO));
        BigDecimal valor = new BigDecimal(20.0);
        for (Conta conta : contascorrente) {
            if(conta != null){
                transferencia(conta, contaBolsa, valor);
                String descricaoMov = "Mensalidade da conta";
                mCD.insereMovimentacao(conta, contaBolsa, valor, descricaoMov);
            }
        }
    }


    public List<Conta> buscarContas(ClienteDAO clienteDAO) {
        String sql = "select * from conta";

        List<Conta> contas = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idConta");
                Long idCliente = rs.getLong("cliente");
                boolean contaBolsa = false;
                if (rs.getInt("contaBolsa") != 0) contaBolsa = true;
                BigDecimal saldo = new BigDecimal(rs.getFloat("saldo"));
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                Conta contaCorrente = new Conta();

                contaCorrente.setId(id);
                contaCorrente.setCliente(clienteDAO.buscaClientePorId(idCliente));
                contaCorrente.setContaBolsa(contaBolsa);
                contaCorrente.setSaldo(saldo);
                contaCorrente.setDataCriacao(dataCriacao);
                contaCorrente.setDataModificacao(dataModificacao);

                contas.add(contaCorrente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contas;
    }

    public StringBuilder mostrarTodos(ClienteDAO clienteDAO) {

        return imprimeLista(buscarContas(clienteDAO));
    }

    private StringBuilder imprimeLista(List<Conta> contaCorrente) {
        StringBuilder sb = new StringBuilder("");
        for (Conta conta : contaCorrente) {
            sb.append(conta);
        }
        return sb;
    }

    public Conta buscaContaPorId(long code, ClienteDAO clienteDAO) {
        List<Conta> contas = buscarContas(clienteDAO);
        
        for (Conta conta : contas) {
            if(conta.getId() == code)
                return conta;
        }
        return null;
    }


public StringBuilder mostrarTodosSS() {
        boolean temConta = false;
        StringBuilder sb = new StringBuilder();
        sb.append("");

        for (Conta conta : contascorrente) {
            if (conta != null) {
                sb.append(conta.toString()).append("\n");
                temConta = true;
            }
        }

        if (!temConta) {
            sb.append("\n Não existe contas cadastrada\n");
        }
        return sb;
    }

    public StringBuilder listaContas(ClienteDAO clienteDAO) {
        List<Conta> contas = buscarContas(clienteDAO);
        
        StringBuilder sb = new StringBuilder();
        sb.append("");
        
        sb.append("| ").append(util.textoColuna(String.valueOf("ID Conta"), 8)).append(" | ").append(util.textoColuna(String.valueOf("Nome do Cliente"), 20)).append(" |\n");

        for (Conta conta : contas) {
            if (conta != null) {
                sb.append("| ").append(util.textoColuna(String.valueOf(conta.getId()), 8)).append(" | ").append(util.textoColuna(String.valueOf(conta.getCliente().getNome()), 20)).append(" |\n");
            }
        }
        
        return sb;
    }

    public Cliente buscaClientePorIdConta(long id, ClienteDAO clienteDAO) {
        List<Conta> contas = buscarContas(clienteDAO);
        
        for (Conta conta : contas) {
            if (conta.getId() == id) {
                return conta.getCliente();
            }
        }
        return null;
    }

    public Conta buscaContaPorCliente(Cliente cliente, ClienteDAO clienteDAO) {
        List<Conta> contas = buscarContas(clienteDAO);
        
        for (Conta conta : contas) {
            Cliente c1 = conta.getCliente();
            if (c1.equals(cliente)) {
                return conta;
            }

        }
        return null;
    }

    public boolean validaId(long id) {
        for (int i = 0; i < contascorrente.length; i++) {
            if (contascorrente[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Conta buscaContaBolsa(List<Conta> contasCorrente) {
        for (Conta conta : contasCorrente) {
            if (conta.isContaBolsa()) {
                return conta;
            }
        }
        return null;
    }

    public boolean remover(int elemento) {
        String sql = "delete from conta where id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, elemento);

            stmt.execute();

            // System.out.println("Elemento excluído com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    public boolean deposito(Conta destino, BigDecimal valor, MovimentacaoContaDAO mCD){        
        destino.entrada(valor);
        altera(destino);
        String descricaoMov = "Depósito";
        mCD.insereMovimentacao(null, destino, valor, descricaoMov);
        return true;
    }
    
    public boolean saque(Conta origem, BigDecimal valor, MovimentacaoContaDAO mCD){        
        if(origem.getSaldo().compareTo(valor) >= 0){
            origem.retirada(valor);
            altera(origem);
            String descricaoMov = "Saque";
            mCD.insereMovimentacao(origem, null, valor, descricaoMov);
            return true;
        }
        return false;
    }

    public boolean pagamento(Conta origem, BigDecimal valor, MovimentacaoContaDAO mCD, ClienteDAO clienteDAO){
        if(transferencia(origem, this.buscaContaBolsa(buscarContas(clienteDAO)), valor)) {
            String descricaoMov = "Pagamento para Bolsa";
            mCD.insereMovimentacao(origem, this.buscaContaBolsa(buscarContas(clienteDAO)), valor, descricaoMov);
            return true;
        }
        return false;

    }



    public boolean transfere(Conta origem, Conta destino, BigDecimal valor, MovimentacaoContaDAO mCD) {
        if (transferencia(origem, destino, valor)) {
            MovimentacaoConta m = new MovimentacaoConta();
            m.setValor(valor);
            m.setConta(origem);
            m.setTipMov(2);
            m.setDescricao("Transferencia entre contas");
            mCD.insere(m);
            m.setConta(destino);
            m.setTipMov(1);
            mCD.insere(m);

            return true;
        }
        return false;
    }

    public boolean transferencia(Conta origem, Conta destino, BigDecimal valor) {
        if (origem.getSaldo().compareTo(valor) >= 0) {
            origem.retirada(valor);
            destino.entrada(valor);
            altera(destino);
            altera(origem);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean pagaDividendos(Conta origem, Conta destino, BigDecimal valor, MovimentacaoContaDAO mCD) {
        if(transferencia(origem, destino, valor)) {
            String descricaoMov = "Pagamento de Dividendos";
            mCD.insereMovimentacao(origem, destino, valor, descricaoMov);
            
            return true;
        }
        return false;
    }




}



