/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import mvc.model.Entilies.Cliente;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import mvc.control.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mvc.control.ConnectionFactory;
import mvc.model.Entilies.Ativo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * /* @ professor Eduardo Silvestre
 *
 * @author William de Sousa Mota
 * @author Lucas Feitosa
 *
 */
public class ClienteDAO {

    Util util = new Util();
    Scanner scanner = new Scanner(System.in);

    public boolean adiciona(Cliente elemento) {

        String sql = "INSERT INTO clientes"
                + "(nome, endereco, cpf, telefone, login, senha, tipoUsuario, dataCriacao, dataModificacao, temConta) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setString(1, elemento.getNome());
            stmt.setString(2, elemento.getEndereco());
            stmt.setString(3, elemento.getCPF());
            stmt.setString(4, elemento.getTelefone());
            stmt.setString(5, elemento.getLogin());
            stmt.setString(6, elemento.getSenha());
            stmt.setInt(7, elemento.getTipoUsuario());
            stmt.setTimestamp(8, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setTimestamp(9, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setBoolean(10, elemento.isTemConta());

            stmt.execute();

            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }

    private PreparedStatement createPreparedStatement(Connection con, long id) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public boolean ehVazio() {
        String sql = "SELECT COUNT(*) AS total FROM clientes;";
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
    
    public List<Cliente> buscarTodos() {
        String sql = "select * from clientes";

        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int tipoUsuario = rs.getInt("tipoUsuario");
                Date dataCriacao = rs.getDate("dataCriacao");
                Date dataModificao = rs.getDate("dataModificacao");
                
                boolean temConta = false;
                if (rs.getInt("temConta") != 0) temConta = true;

                Cliente cliente = new Cliente();
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setCPF(cpf);
                cliente.setTelefone(telefone);
                cliente.setLogin(login);
                cliente.setSenha(senha);
                cliente.setTipoUsuario(tipoUsuario);
                cliente.setTemConta(temConta);

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public StringBuilder mostrarTodos() {
        return imprimeLista(buscarTodos());
    }
    
    //mudar para banco
    public StringBuilder mostrarClientesDisponiveis() {
        String sql = "select * from clientes";

        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int tipoUsuario = rs.getInt("tipoUsuario");
                Date dataCriacao = rs.getDate("dataCriacao");
                Date dataModificao = rs.getDate("dataModificacao");
                boolean temConta = false;
                if (rs.getInt("temConta") != 0) temConta = true;

                Cliente cliente = new Cliente();
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setEndereco(endereco);
                cliente.setCPF(cpf);
                cliente.setTelefone(telefone);
                cliente.setLogin(login);
                cliente.setSenha(senha);
                cliente.setTipoUsuario(tipoUsuario);
                cliente.setTemConta(temConta);

                if (!cliente.isTemConta()) clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imprimeLista(clientes);
    }

    private StringBuilder imprimeLista(List<Cliente> cliente) {
        StringBuilder sb = new StringBuilder("");
        for (Cliente c : cliente) {
            sb.append(c).append("\n");
        }
        return sb;
    }

    public boolean remover(int elemento) {
        String sql = "delete from clientes where id = ?";

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

    public Cliente buscaClientePorId(long code) {
        List<Cliente> clientes = buscarTodos();
        for (Cliente cliente : clientes) {
            if(cliente.getId()==code)
                return cliente;
        }
        return null;
    }

    public Cliente altera(Cliente elemento) {

        String sql = "update clientes set nome = ?, endereco = ?,  cpf = ?, telefone = ?, login = ?, senha = ?, tipoUsuario = ?, dataModificacao = ?, temConta = ?  where id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, elemento.getNome());
            stmt.setString(2, elemento.getEndereco());
            stmt.setString(3, elemento.getCPF());
            stmt.setString(4, elemento.getTelefone());
            stmt.setString(5, elemento.getLogin());
            stmt.setString(6, elemento.getSenha());
            stmt.setInt(7, elemento.getTipoUsuario());

            stmt.setTimestamp(8, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setBoolean(9, elemento.isTemConta());
            
            stmt.setLong(10, elemento.getId());

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return elemento;
    }

    public Cliente alterarSenhaCliente(Cliente elemento) {

        String sql = "update clientes set nome = ?, endereco = ?,  cpf = ?, telefone = ?, login = ?, senha = ?, tipoUsuario = ?, dataCriacao = ?, dataModificacao = ?, temconta = ? where id = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, elemento.getNome());
            stmt.setString(2, elemento.getEndereco());
            stmt.setString(3, elemento.getCPF());
            stmt.setString(4, elemento.getTelefone());
            stmt.setString(5, elemento.getLogin());
            stmt.setString(6, elemento.getSenha());
            stmt.setInt(7, elemento.getTipoUsuario());
            stmt.setTimestamp(8, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBoolean(9, elemento.isTemConta());
            stmt.setLong(10, elemento.getId());

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return elemento;
    }

    public Cliente buscaClienteLogin(Cliente temp) {
        String sql = "SELECT * FROM clientes where login = ? and senha = ?";
        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, temp.getLogin());
            ps.setString(2, temp.getSenha());

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String nome = rs.getString("nome");
                    String endereco = rs.getString("endereco");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String login = rs.getString("login");
                    String senha = rs.getString("senha");
                    int tipoUsuario = rs.getInt("tipoUsuario");
                    Date dataModificacao = rs.getDate("dataModificacao");

                    Cliente a = new Cliente();

                    a.setNome(nome);
                    a.setEndereco(endereco);
                    a.setCPF(cpf);
                    a.setTelefone(telefone);
                    a.setLogin(login);
                    a.setSenha(senha);
                    a.setTipoUsuario(tipoUsuario);
                    a.setDataModificacao();

                    return a;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }


   
}
