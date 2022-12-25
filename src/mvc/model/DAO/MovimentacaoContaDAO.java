/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
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
import mvc.model.Entilies.Execucao;
import mvc.model.Entilies.MovimentacaoConta;

/**
 *
 * /* @ professor Eduardo Silvestre
 *
 * @author William de Sousa Mota
 * @author Lucas da Silva Feitosa
 *
 *
 */
public class MovimentacaoContaDAO {
    
     public List<MovimentacaoConta>  movimentacaoconta = new ArrayList<>(); 
     Util util = new Util();

   
    public MovimentacaoContaDAO() {

    }
     public boolean ehVazio() {
        String sql = "SELECT COUNT(*) AS total FROM movimentacaoconta;";
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
    
    
    
  
     
      public boolean insere(MovimentacaoConta elemento) {

        String sql = "INSERT INTO movimentacaoconta"
                + "(contaCorrente, tipoMov, descricao, valor, dataCriacao, dataModificacao) "
                + "VALUES(?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getConta().getId());
            stmt.setInt(2, elemento.getTipMov());
            stmt.setString(3, elemento.getDescricao());
            stmt.setBigDecimal(4, elemento.getValor());
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
         
   
     


    public void insereMovimentacao(Conta debito, Conta credito, BigDecimal valor, String descricaoMov) {
        if(debito!=null){
            MovimentacaoConta m = new MovimentacaoConta();
            m.setValor(valor);
            m.setConta(debito);
            m.setTipMov(2);
            m.setDescricao(descricaoMov);
            this.insere(m);
        }
        
        if(credito!=null){
            MovimentacaoConta m2 = new MovimentacaoConta();
            m2.setValor(valor);
            m2.setConta(credito);
            m2.setTipMov(1);
            m2.setDescricao(descricaoMov);
            this.insere(m2);
        }
        
    }
    
     public StringBuilder mostrarTodos(ContaDAO contaDAO, ClienteDAO clienteDAO, OrdemDAO ordemDAO) {
        return imprimeLista(buscarTodos(contaDAO, clienteDAO, ordemDAO));
    }

    public List<MovimentacaoConta> buscarTodos(ContaDAO contaDAO, ClienteDAO clienteDAO, OrdemDAO ordemDAO) {
        String sql = "select * from movimentacaoconta";

        List<MovimentacaoConta> movimentacao = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idMovimentacao");
                Long idConta = rs.getLong("contaCorrente");
                int tipoMov = rs.getInt("tipoMov");
                String descricao = rs.getString("descricao");
                BigDecimal valor = rs.getBigDecimal("valor");
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                MovimentacaoConta mov = new MovimentacaoConta();

                mov.setId(id);
                mov.setConta(contaDAO.buscaContaPorId(idConta, clienteDAO));
                mov.setTipMov(tipoMov);
                mov.setDescricao(descricao);
                mov.setValor(valor);
                mov.setDataCriacao(dataCriacao);
                mov.setDataModificacao(dataModificacao);

                movimentacao.add(mov);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movimentacao;
    }
     
     private StringBuilder imprimeLista(List<MovimentacaoConta> movimentacoes) {
        StringBuilder sb = new StringBuilder("");
        for (MovimentacaoConta movimentacao : movimentacoes) {
            sb.append(movimentacao).append("\n");
        }
        return sb;
    }
    
    public boolean remover(int elemento) {
        String sql = "delete from movimentacaoconta where id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, elemento);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

}
