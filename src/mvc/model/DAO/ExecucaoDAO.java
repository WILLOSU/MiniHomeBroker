/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import mvc.model.Entilies.Execucao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mvc.control.ConnectionFactory;
import mvc.control.Util;
import mvc.model.Entilies.Cliente;
import mvc.model.Entilies.Conta;
import mvc.model.Entilies.Ordem;
import mvc.model.enums.TipoOrdem;

/**
 *
 * /* @ professor Eduardo Silvestre
 *
 * @author William de Sousa Mota
 * @author Lucas Feitosa
 *
 */
public class ExecucaoDAO {


    Util util = new Util();
    private Object execucaoDAO;
    private Object execucaoDAO2;

    public ExecucaoDAO() {

    }

    public ExecucaoDAO(ExecucaoDAO execDAO) {

    }

    public boolean adiciona(Execucao elemento) {

        String sql = "INSERT INTO execucao"
                + "(ContaCompra, ContaVenda, quantidade, ordem, dataModificacao, dataCriacao) "
                + "VALUES(?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getContaCompra().getId());
            stmt.setInt(2, (int) elemento.getContaVenda().getId());
            stmt.setInt(3, elemento.getQuantidade());
            stmt.setInt(4, (int) elemento.getOrdem().getId());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));

            stmt.execute();
            result = true;

            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }

    private PreparedStatement createPreparedStatement(Connection con, long id) throws SQLException {
        String sql = "SELECT * FROM Execucao WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public boolean remover(int elemento) {
        String sql = "delete from execucao where id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, elemento);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    public StringBuilder mostrarTodos(ContaDAO contaDAO, ClienteDAO clienteDAO, OrdemDAO ordemDAO) {
        return imprimeLista(buscarTodos(contaDAO, clienteDAO, ordemDAO));
    }
    
    public StringBuilder mostrarTodos(ContaDAO contaDAO, ClienteDAO clienteDAO, OrdemDAO ordemDAO, Conta contaLogada) {
        return imprimeLista(buscarTodos(contaDAO, clienteDAO, ordemDAO), contaLogada);
    }

    public List<Execucao> buscarTodos(ContaDAO contaDAO, ClienteDAO clienteDAO, OrdemDAO ordemDAO) {
        String sql = "select * from execucao";

        List<Execucao> execucao = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idExecucao");
                Long idContaCompra = rs.getLong("ContaCompra");
                Long idContaVenda = rs.getLong("ContaVenda");
                int qtde = rs.getInt("quantidade");
                Long idOrdem = rs.getLong("ordem");
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                Execucao exec = new Execucao();

                exec.setId(id);
                exec.setContaCompra(contaDAO.buscaContaPorId(idContaCompra, clienteDAO));
                exec.setContaVenda(contaDAO.buscaContaPorId(idContaVenda, clienteDAO));
                exec.setQuantidade(qtde);
                exec.setOrdem(ordemDAO.buscaOrdemPorId(idOrdem, contaDAO, clienteDAO));
                exec.setDataCriacao(dataCriacao);
                exec.setDataModificacao(dataModificacao);

                execucao.add(exec);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return execucao;
    }
    
    
    
    public boolean ehVazio() {
        String sql = "SELECT COUNT(*) AS total FROM execucao;";
        boolean result;
        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {
            result = rs.getInt("total") != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private StringBuilder imprimeLista(List<Execucao> execucoes) {
        StringBuilder sb = new StringBuilder("");
        for (Execucao execucao : execucoes) {
            sb.append(execucao.toString()).append("\n");
        }
        return sb;
    }
    
    private StringBuilder imprimeLista(List<Execucao> execucoes, Conta contaLogada) {
        StringBuilder sb = new StringBuilder("");
        for (Execucao execucao : execucoes) {
            if(execucao.getContaCompra().equals(contaLogada) || execucao.getContaVenda().equals(contaLogada)) {
                sb.append(execucao).append("\n");
            }
        }
        return sb;
    }
    
    public boolean novaExecucao(Conta cComprador, Conta cVendedor, Ordem ordem, int qtdeVenda) {
        Execucao u = new Execucao();
        u.setContaCompra(cComprador);
        u.setContaVenda(cVendedor);
        u.setOrdem(ordem);
        u.setQuantidade(qtdeVenda);
        if(this.adiciona(u)){
            return true;
        }else return false;
    }

    

}
