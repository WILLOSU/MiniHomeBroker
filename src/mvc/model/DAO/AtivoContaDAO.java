/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.DAO;

import mvc.model.Entilies.AtivoConta;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import mvc.control.ConnectionFactory;
import mvc.control.Util;
import mvc.model.Entilies.Ativo;
import mvc.model.Entilies.Cliente;
import mvc.model.Entilies.Conta;

/**
 *
 * @author Lucas
 */
public class AtivoContaDAO {

    // List<AtivoConta> ativoContas = new ArrayList<>();
    public AtivoConta[] ativoContas = new AtivoConta[100];
    Util util = new Util();
    Scanner scanner = new Scanner(System.in);

    final int nAtivoConta = 100;
    private final AtivoConta[] relacao = new AtivoConta[nAtivoConta];
    private BigDecimal valorCompra;

    public AtivoContaDAO() {

    }

    public boolean adiciona(AtivoConta elemento) {

        String sql = "INSERT INTO ativoconta"
                + "(conta, ativo,  totalAtivos, valorCompra, dataCriacao, dataModificacao) "
                + "VALUES(?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getConta().getId());
            stmt.setInt(2, (int) elemento.getAtivo().getId());
            stmt.setInt(3, elemento.getTotalAtivos());
            stmt.setBigDecimal(4, elemento.getValorCompra());
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

    public boolean altera(AtivoConta elemento) {
        
        Boolean result = false;
        String sql = "update ativoconta set conta = ?, ativo = ?,  totalAtivos = ?, valorCompra = ?, dataModificacao = ?  where idAtivoConta = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getConta().getId());
            stmt.setInt(2, (int) elemento.getAtivo().getId());
            stmt.setInt(3, elemento.getTotalAtivos());
            stmt.setBigDecimal(4, elemento.getValorCompra());
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setLong(6, elemento.getId());
            

            stmt.execute();
            result = true;
            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }

    public void pagarDividendos(String ticker, BigDecimal valorPorAcao, ContaDAO contaDAO, MovimentacaoContaDAO mCD, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);
        for (AtivoConta aux : carteira) {
            if (!aux.getConta().equals(contaDAO.buscaContaBolsa(contaDAO.buscarContas(clienteDAO)))) {
                if (aux.getAtivo().getTicker().equals(ticker)) {
                    BigDecimal valorAPagar = valorPorAcao.multiply(new BigDecimal(aux.getTotalAtivos()));
                    contaDAO.pagaDividendos(contaDAO.buscaContaBolsa(contaDAO.buscarContas(clienteDAO)), aux.getConta(), valorAPagar, mCD);
                }
            }
        }
    }

    public boolean ehVazio() {
        for (AtivoConta ativoConta : ativoContas) {
            if (ativoConta != null) {
                return false;
            }
        }
        return true;
    }

    public boolean ehCheio() {
        for (AtivoConta aux : ativoContas) {
            if (aux == null) {
                return false;
            }
        }
        return true;
    }

    public List<AtivoConta> buscarTodos(ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {

        String sql = "select * from ativoconta";

        List<AtivoConta> ativoConta = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idAtivoConta");
                Long idConta = rs.getLong("conta");
                Long idAtivo = rs.getLong("ativo");
                int totalAtivos = rs.getInt("totalAtivos");
                BigDecimal valorCompra = new BigDecimal(rs.getFloat("valorCompra"));
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                AtivoConta ac = new AtivoConta();

                ac.setId(id);
                ac.setConta(contaDAO.buscaContaPorId(idConta, clienteDAO));
                ac.setAtivo(ativoDAO.buscaAtivoPorId(idAtivo));
                ac.setTotalAtivos(totalAtivos);
                ac.setValorCompra(valorCompra);
                ac.setDataCriacao(dataCriacao);
                ac.setDataModificacao(dataModificacao);

                ativoConta.add(ac);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ativoConta;
    }

    private StringBuilder imprimeLista(List<AtivoConta> ativoConta) {
        StringBuilder sb = new StringBuilder("");
        for (AtivoConta ac : ativoConta) {
            sb.append(ac).append("\n");
        }
        return sb;
    }

    public StringBuilder mostrarTodos(Cliente cliente, ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);

        StringBuilder sb = new StringBuilder("");
        boolean headerImpress = false;
        BigDecimal saldoEmAtivos = new BigDecimal(BigInteger.ZERO);
        BigDecimal saldoEmAtivosPrecoInicial = new BigDecimal(BigInteger.ZERO);
        BigDecimal saldoEmAtivosPrecoCompra = new BigDecimal(BigInteger.ZERO);

        for (AtivoConta ativoConta : carteira) {
            if (buscaCliente(ativoConta, contaDAO, ativoDAO, clienteDAO).equals(cliente)) {
                if (!headerImpress) {
                    sb.append(ativoConta.toStringHeader());
                    headerImpress = true;
                }
                sb.append(ativoConta);
                BigDecimal totalDoAtivo = ativoConta.getAtivo().getPrecoAtual().multiply(new BigDecimal(ativoConta.getTotalAtivos()));
                BigDecimal totalDoAtivoPrecoInicial = ativoConta.getAtivo().getPrecoInicial().multiply(new BigDecimal(ativoConta.getTotalAtivos()));
                BigDecimal totalDoAtivoPrecoCompra = ativoConta.getValorCompra().multiply(new BigDecimal(ativoConta.getTotalAtivos()));
                saldoEmAtivos = saldoEmAtivos.add(totalDoAtivo);
                saldoEmAtivosPrecoInicial = saldoEmAtivosPrecoInicial.add(totalDoAtivoPrecoInicial);
                saldoEmAtivosPrecoCompra = saldoEmAtivosPrecoCompra.add(totalDoAtivoPrecoCompra);
            }
        }

        BigDecimal lucroPrejuizo = saldoEmAtivos.subtract(saldoEmAtivosPrecoCompra);
        BigDecimal percentual = calculaPercentual(saldoEmAtivos, saldoEmAtivosPrecoCompra);
        sb.append("\n Valor total em ativos: R$ ").append(saldoEmAtivos);
        sb.append("\n O Lucro / prejuízo (preço de compra)  total é: R$ ").append(lucroPrejuizo).append(" Que corresponde a ").append(percentual).append("%\n");

        lucroPrejuizo = saldoEmAtivos.subtract(saldoEmAtivosPrecoInicial);
        percentual = calculaPercentual(saldoEmAtivos, saldoEmAtivosPrecoInicial);
        sb.append("\n O Lucro / prejuízo (preço inicial IPO)total é: R$ ").append(lucroPrejuizo).append(" Que corresponde a ").append(percentual).append("%\n");

        return sb;
    }
    
    public StringBuilder mostrarTodos(ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);

        StringBuilder sb = new StringBuilder("");
        boolean headerImpress = false;
        
        for (AtivoConta ativoConta : carteira) {
            
                if (!headerImpress) {
                    sb.append(ativoConta.toStringHeader());
                    headerImpress = true;
                }
                sb.append(ativoConta);
                 
        }

        return sb;
    }

    private Cliente buscaCliente(AtivoConta alvo, ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);

        for (AtivoConta ativoConta : carteira) {
            if (ativoConta.equals(alvo)) {
                return ativoConta.getConta().getCliente();
            }
        }
        return null;
    }

    private BigDecimal calculaPercentual(BigDecimal saldoEmAtivos, BigDecimal saldoEmAtivosPrecoInicial) {
        MathContext mc = new MathContext(2, RoundingMode.CEILING);
        BigDecimal divisaoAtualPorInicial = saldoEmAtivos.divide(saldoEmAtivosPrecoInicial, mc);
        BigDecimal divisaoMenosUm = divisaoAtualPorInicial.subtract(BigDecimal.ONE);
        BigDecimal percentual = divisaoMenosUm.multiply(BigDecimal.valueOf(100));
        return percentual;
    }

    public String buscaTickerPorId(long id, ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);

        for (AtivoConta ativoConta : carteira) {
            if (ativoConta != null && ativoConta.getId() == id) {
                return ativoConta.getAtivo().getTicker();
            }
        }
        return null;
    }

    /*
     public StringBuilder buscaTodos() {
     StringBuilder sb = new StringBuilder();
     sb.append("");
     if (!this.ehVazio()) {
     for (AtivoConta ativoConta : ativoContas) {
     if (ativoConta != null) {
     sb.append(ativoConta);
     }
     }
     } else {
     sb.append("\n Não existe AtivosConta cadastrado\n");
     }
     return sb;
     }
     */
    public int nAtivos(Conta atual, Ativo alvo) {
        int cont = 0;
        for (AtivoConta aux : relacao) {
            if (aux != null) {
                if (aux.getConta().equals(atual)) {
                    if (aux.getAtivo().equals(alvo)) {
                        cont++;
                    }
                }
            }
        }
        return cont;
    }

    public void delete(String ticker, Conta dono, int qtd) {
        if (!(this.ehVazio())) {
            for (int i = 0, cont = 1; i < nAtivoConta && cont <= qtd; i++, cont++) {
                if (relacao[i] != null) {
                    if (relacao[i].getAtivo().getTicker().equals(ticker) && relacao[i].getConta().equals(dono)) {
                        relacao[i] = null;
                    }
                }
            }
        }
    }

    // VER SE É ESTE
    public boolean delete(int elemento) {
        String sql = "delete from ativoConta where id = ?";

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

    /*
     public void create(AtivoConta novo, int quantidade) {
     if (!(this.ehCheio())) {
     for (int i = this.proximaPosicaoLivre(), cont = 1; i < nAtivoConta && cont <= quantidade; i++, cont++) {
     relacao[this.proximaPosicaoLivre()] = novo;
     }
     }
     }
     */
    public String read(Conta atual) {
        if (!(this.ehVazio())) {
            StringBuilder result = new StringBuilder("");
            int tam = 10;

            String[] ativos = new String[tam];
            Integer[] cont = new Integer[tam];

            for (int i = 0; i < nAtivoConta; i++) {
                if (relacao[i] != null) {
                    if (relacao[i].getConta().equals(atual)) {
                        boolean existe = false;

                        for (int j = 0; j < tam; j++) {
                            if (ativos[j] != null) {
                                if (ativos[j].equals(relacao[i].getAtivo().getTicker())) {
                                    existe = true;
                                    cont[j]++;
                                }
                            }
                        }

                        if (existe == false) {
                            for (int j = 0; j < tam; j++) {
                                if (ativos[j] == null) {
                                    ativos[j] = relacao[i].getAtivo().getTicker();
                                    cont[j] = 1;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // monta a relacao de ativos
            for (int i = 0; i < tam; i++) {
                if (ativos[i] != null) {
                    result.append("Ativo : ").append(ativos[i]).append(" | Quantidade : ").append(cont[i]).append("\n");
                }
            }

            //System.out.println(result);
            return result.toString();
        } else {
            return "Nenhum ativo existente na conta";
        }
    }

    public boolean remove(int elemento) {
        String sql = "delete from ativoconta where id = ?";

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

    public void atualizaCarteira(Ativo ativo, Conta cComprador, Conta cVendedor, int qtdeAtivos, ContaDAO contaDAO, AtivoDAO ativoDAO, ClienteDAO clienteDAO) {
        AtivoConta aCCom = novoAC(ativo, cComprador, qtdeAtivos);
        AtivoConta aCVen = novoAC(ativo, cVendedor, qtdeAtivos);

        List<AtivoConta> carteira = buscarTodos(contaDAO, ativoDAO, clienteDAO);

        //incrementar qtd ativo ou adicionar novo na carteira do comprador
        boolean achou = false;
        for (AtivoConta ativoConta : carteira) {
            if (ativoConta.equals(aCCom)) {
                //criar método de update banco
                ativoConta.setTotalAtivos(ativoConta.getTotalAtivos() + qtdeAtivos);
                altera(ativoConta);
                achou = true;
            }
        }
        if (!achou) {
            this.adiciona(aCCom);
        }

        //remover ativo ou diminuir qtd carteira do ventedor
        achou = false;
        for (AtivoConta ativoConta : carteira) {
            if (ativoConta.equals(aCVen) && ativoConta.getTotalAtivos() > qtdeAtivos) {
                ativoConta.setTotalAtivos(ativoConta.getTotalAtivos() - qtdeAtivos);
                altera(ativoConta);
                achou = true;
            }
        }
        if (!achou) {
            this.remove((int) aCVen.getId());
        }
    }

    private AtivoConta novoAC(Ativo ativo, Conta conta, int qtdeAtivo) {
        AtivoConta aC = new AtivoConta();
        aC.setAtivo(ativo);
        aC.setConta(conta);
        aC.setTotalAtivos(qtdeAtivo);
        aC.setValorCompra(ativo.getPrecoAtual());
        return aC;
    }
}
