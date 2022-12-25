/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import mvc.model.Entilies.Ativo;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
//import jdk.internal.util.Preconditions;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import mvc.control.ConnectionFactory;
import mvc.control.Util;
import mvc.model.Entilies.Cliente;

/**
 *
 * /* @ professor Eduardo Silvestre
 *
 * @author William de Sousa Mota
 * @author Lucas Feitosa
 *
 */
public class AtivoDAO {

    Util util = new Util();
    Scanner scanner = new Scanner(System.in);


    public boolean adiciona(Ativo elemento) {

        String sql = "INSERT INTO ativos"
                + "(nomeEmpresa, ticker,  precoInicial, precoAtual,  dataCriacao, dataModificacao, tipoUsuario, totalAtivos) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setString(1, elemento.getNomeEmpresa());
            stmt.setString(2, elemento.getTicker());
            stmt.setBigDecimal(3, elemento.getPrecoInicial());
            stmt.setBigDecimal(4, elemento.getPrecoInicial());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setInt(7, elemento.getTipoUser());
            stmt.setInt(8, elemento.getTotalAtivos());

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
        String sql = "SELECT * FROM Ativo WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public boolean ehVazio() {
        String sql = "SELECT COUNT(*) AS total FROM ativos;";
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

    public List<Ativo> buscaTodos() {
        String sql = "select * from ativos";

        List<Ativo> ativos = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idAtivos");
                String nomeEmpresa = rs.getString("nomeEmpresa");
                String ticker = rs.getString("ticker");
                BigDecimal precoInicial = rs.getBigDecimal("precoInicial");
                BigDecimal precoAtual = rs.getBigDecimal("precoAtual");
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));
                int totalAtivos = rs.getInt("totalAtivos");
                

                Ativo ativo = new Ativo();
                ativo.setId(id);
                ativo.setNomeEmpresa(nomeEmpresa);
                ativo.setTicker(ticker);
                ativo.setPrecoInicial(precoInicial);
                ativo.setPrecoAtual(precoAtual);
                ativo.setDataModificacao(dataModificacao);
                ativo.setDataCriacao(dataCriacao);
                ativo.setTotalAtivos(totalAtivos);

                ativos.add(ativo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ativos;
    }
    
    public void imprimeLista() {
        List<Ativo> ativo = buscaTodos();
        for (Ativo a : ativo) {
            System.out.println(a);
        }
    }
    

    public boolean remover(long elemento) {
        String sql = "delete from ativos where idativos = ?";

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

    public Ativo buscaAtivoPorId(long code) {
        List<Ativo> ativos = buscaTodos();
        for (Ativo ativo : ativos) {
            if(ativo.getId()==code)
                return ativo;
        }
        return null;
    }
    
    void alteraPreco(String ticker, BigDecimal valor) {
        List<Ativo> ativos = buscaTodos();
        for (Ativo ativo : ativos) {
            if(ativo.getTicker().equals(ticker)){
                ativo.setPrecoAtual(valor);
            }
        }
    }
    
    public Ativo busca(String ticker) {
        List<Ativo> ativos = buscaTodos();
        for (Ativo ativo : ativos) {
            if (ativo.getTicker().equals(ticker)) {
                return ativo;
            }
        }
        return null;
    }
    
        
    
/*
    
    */

    public Ativo altera(Ativo elemento) {
        String sql = "update ativos set nomeEmpresa = ?, ticker= ?, precoInicial = ?, dataModificacao = ? where idativos = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, elemento.getNomeEmpresa());
            stmt.setString(2, elemento.getTicker());
            stmt.setBigDecimal(3, elemento.getPrecoInicial());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));

            stmt.setLong(5, elemento.getId());

            stmt.execute();

            // System.out.println("Elemento alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return elemento;
    }

    ///////
    public boolean temAtivo(Ativo a) throws SQLException, Exception {
        {
            Ativo respoAtivo = new Ativo();
            List<Ativo> ativos = buscaTodos();

            for (Ativo ativo : ativos) {
                if (ativo != null && ativo.equals(a)) {
                    return true;
                }
            }
            return false;
        }

    }
    
    public boolean remover(int elemento) {
        String sql = "delete from ativos where id = ?";

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

    

   

}
   

