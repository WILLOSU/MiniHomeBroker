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
import mvc.model.Entilies.Ordem;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import mvc.control.ConnectionFactory;
import mvc.control.Util;
import mvc.model.Entilies.Ativo;
import mvc.model.Entilies.AtivoConta;
import mvc.model.Entilies.Cliente;
import mvc.model.Entilies.Conta;
import mvc.model.Entilies.Execucao;
import mvc.model.enums.EstadoOrdem;
import mvc.model.enums.TipoOrdem;

/**
 *
 * /* @ professor Eduardo Silvestre
 *
 * @author William de Sousa Mota
 * @author Lucas Feitosa
 *
 *
 */
public class OrdemDAO {

    Util util = new Util();
    Scanner scanner = new Scanner(System.in);

    public OrdemDAO() {
    }

    public boolean adiciona(Ordem elemento) {

        String sql = "INSERT INTO ordem"
                + "(conta, tipoOrdem, valor, ticker, qtde, estadoOrdem, valorTotal, dataCriacao, dataModificacao) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Boolean result = false;

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, (int) elemento.getConta().getId());
            stmt.setInt(2, elemento.getTipoOrdem());
            stmt.setBigDecimal(3, elemento.getValor());
            stmt.setString(4, elemento.getTicker());
            stmt.setInt(5, elemento.getQtde());
            stmt.setInt(6, elemento.getEstadoOrdem());
            stmt.setBigDecimal(7, elemento.getValorTotal());
            stmt.setTimestamp(8, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));
            stmt.setTimestamp(9, java.sql.Timestamp.valueOf(Util.pegaDataAtual()));

            stmt.execute();
            result = true;

            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }

    public boolean remover(int elemento) {
        String sql = "delete from ordem where idOrdem = ?";

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, elemento);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    public boolean altera(Ordem elemento) {

        String sql = "update ordem set conta = ?, tipoOrdem = ?,  valor = ?, ticker = ?, qtde = ?, estadoOrdem = ?, valorTotal = ?, dataModificacao = ?  where idOrdem = ?";
        Boolean result = false;
        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            // seta os valores
            stmt.setInt(1, (int) elemento.getConta().getId());
            stmt.setInt(2, elemento.getTipoOrdem());
            stmt.setBigDecimal(3, elemento.getValor());
            stmt.setString(4, elemento.getTicker());
            stmt.setInt(5, elemento.getQtde());
            stmt.setInt(6, elemento.getEstadoOrdem());
            stmt.setBigDecimal(7, elemento.getValorTotal());
            stmt.setTimestamp(8, java.sql.Timestamp.valueOf(elemento.getDataModificacao()));
            stmt.setLong(9, elemento.getId());

            stmt.execute();
            result = true;

            //System.out.println("Elemento inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return result;
    }

    /*  CÓDIGO ANTIGO
   
     public String mostrarOrdens() {
     if (!(this.ehVazio())) {
     StringBuilder result = new StringBuilder("");

     for (Ordem aux : ordens) {
     if (aux != null) {
     result.append(aux.toString()).append("\n");
     }
     }
     return result.toString();
     } else {
     return "Nenhuma ordem existente";
     }
     }
    
     public String mostrarOrdensCompra() {
     if (!(this.ehVazio())) {
     StringBuilder result = new StringBuilder("");

     for (Ordem ordem : ordens) {
     if (ordem != null && (ordem.getTipoOrdem() == 1 || ordem.getTipoOrdem() == 0)) {
     result.append(ordem.toString()).append("\n");
     }
     }
     return result.toString();
     } else {
     return "Nenhuma ordem de compra existente";
     }
     }
    
     public String mostrarOrdensVenda() {
     if (!(this.ehVazio())) {
     StringBuilder result = new StringBuilder("");

     for (Ordem aux : ordens) {
     if (aux != null && aux.getTipoOrdem() == 2) {
     result.append(aux.toString()).append("\n");
     }
     }
     return result.toString();
     } else {
     return "Nenhuma ordem de venda existente";
     }
     }
    
     */
    public List<Ordem> buscaTodos(ContaDAO contaDAO, ClienteDAO clienteDAO) {
        String sql = "SELECT * FROM ordem";

        List<Ordem> ordens = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Long id = rs.getLong("idOrdem");
                Long idConta = rs.getLong("conta");
                int tipoOrdem = rs.getInt("tipoOrdem");
                BigDecimal valor = new BigDecimal(rs.getFloat("valor"));
                String ticker = rs.getString("ticker");
                int qtde = rs.getInt("qtde");
                int estadoOrdem = rs.getInt("estadoOrdem");
                BigDecimal valorTotal = new BigDecimal(rs.getFloat("valorTotal"));
                LocalDateTime dataCriacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataCriacao"));
                LocalDateTime dataModificacao = Util.timestampToLocalDateTime(rs.getTimestamp("dataModificacao"));

                Ordem ordem = new Ordem();

                ordem.setId(id);
                ordem.setConta(contaDAO.buscaContaPorId(idConta, clienteDAO));
                ordem.setTipoOrdem(tipoOrdem);
                ordem.setValor(valor); //valor total é setado automaticamente ao se setar a quantidade
                ordem.setTicker(ticker);
                ordem.setQtde(qtde);
                ordem.setEstadoOrdem(estadoOrdem);
                ordem.setDataCriacao(dataCriacao);
                ordem.setDataModificacao(dataModificacao);

                ordens.add(ordem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordens;

    }

    public Ordem buscaOrdemPorId(Long idOrdem, ContaDAO contaDAO, ClienteDAO clienteDAO) {
        List<Ordem> ordens = buscaTodos(contaDAO, clienteDAO);

        for (int i = 0; i < ordens.size(); i++) {
            Ordem ordem = ordens.get(i);
            if (ordem.getId() == idOrdem) {
                return ordens.get(i);
            }

        }
        return null;
    }

    public String mostrarOrdensCompra(ContaDAO contaDAO, ClienteDAO clienteDAO) {
        List<Ordem> ordens = buscaTodos(contaDAO, clienteDAO);

        StringBuilder result = new StringBuilder("");

        for (Ordem ordem : ordens) {
            if (ordem.getTipoOrdem() != 2 && ordem.getEstadoOrdem() != 2) {
                result.append(ordem.toString()).append("\n");
            }
        }
        return result.toString();

    }

    public StringBuilder mostrarOrdensVenda(ContaDAO contaDAO, ClienteDAO clienteDAO) {
        List<Ordem> ordens = buscaTodos(contaDAO, clienteDAO);

        StringBuilder result = new StringBuilder("");

        for (Ordem ordem : ordens) {
            if (ordem.getTipoOrdem() == 2 && ordem.getEstadoOrdem() != 2) {
                result.append(ordem.toString()).append("\n");
            }
        }
        return result;

    }

    public String buscaTickerPorId(long id, ContaDAO contaDAO, ClienteDAO clienteDAO) {
        List<Ordem> ordens = buscaTodos(contaDAO, clienteDAO);
        for (Ordem ordem : ordens) {
            if (ordem != null && ordem.getId() == id) {
                return ordem.getTicker();
            }
        }
        return null;
    }

    public StringBuilder executarOrdens(AtivoContaDAO carteiraAtivos, ExecucaoDAO execucaoDAO, ContaDAO contaDAO, AtivoDAO ativoDAO, MovimentacaoContaDAO mCD, ClienteDAO clienteDAO) {
        List<Ordem> ordens = buscaTodos(contaDAO, clienteDAO);

        StringBuilder sb = new StringBuilder("\n\n");
        int qtdeOrdensTotais = 0;
        int qtdeOrdensParciais = 0;
        int qtdeOrdensNaoExecutadas = 0;
        int qtdeOrdensZero = 0;
        for (Ordem ordemVenda : ordens) {
            //localizar quais são as ordens de venda
            if (ordemVenda.getTipoOrdem() == 2 && ordemVenda.getEstadoOrdem() != 2) {
                for (Ordem ordemCompra : ordens) {
                    //localizar quais são as ordens de compra e verifica se são de clientes diferentes
                    if (ordemCompra.getTipoOrdem() != 2 && !ordemCompra.getConta().equals(ordemVenda.getConta()) && ordemCompra.getEstadoOrdem() != 2) {
                        //localiza par venda/compra com o mesmo ticker
                        if (ordemCompra.getTicker().equals(ordemVenda.getTicker())) {
                            //compara se valor de venda é menor ou igual ao de compra
                            if (ordemVenda.getValor().compareTo(ordemCompra.getValor()) <= 0) {
                                //nesse ponto valor de venda é menor ou igual ao de compra
                                //de um mesmo ativo que é de contas diferentes.
                                Conta cVendedor = ordemVenda.getConta();
                                Conta cComprador = ordemCompra.getConta();
                                BigDecimal valorVenda = ordemVenda.getValor();
                                int qtdeVenda = ordemVenda.getQtde();
                                int qtdeCompra = ordemCompra.getQtde();
                                BigDecimal totalOperacao;
                                boolean ordem0 = false;
                                //verifica se é ordem 0 para dar o desconto de 10% no valor
                                if (ordemCompra.getTipoOrdem() == 0 && Util.pegaClienteLogado().getQtdOrdem0() <= 3) {
                                    totalOperacao = ordemVenda.getValorTotal().multiply(BigDecimal.valueOf(0.9));
                                    ordem0 = true;
                                } else {
                                    totalOperacao = ordemVenda.getValorTotal();
                                }
                                //atualiza valor ativo
                                ativoDAO.alteraPreco(ordemVenda.getTicker(), valorVenda);

                                //execução total das ordens de venda e de compra
                                if (qtdeVenda == qtdeCompra) {
                                    //verifica disponibilidade de saldo do comprador
                                    if (contaDAO.transfere(cComprador, cVendedor, totalOperacao, mCD)) {

                                        ordemVenda.setEstadoOrdem(2);
                                        ordemCompra.setEstadoOrdem(2);
                                        altera(ordemVenda);
                                        altera(ordemCompra);

                                        //nova execução ordem venda
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemVenda, qtdeVenda);

                                        //adiciona nova execução ordem compra
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemCompra, qtdeCompra);

                                        //novo ativo na carteira da conta do comprador
                                        Ativo ativo = ativoDAO.busca(ordemCompra.getTicker());
                                        carteiraAtivos.atualizaCarteira(ativo, cComprador, cVendedor, qtdeCompra, contaDAO, ativoDAO, clienteDAO);

                                        //remover as ordens
//                                        remover((int) ordemVenda.getId());
//                                        remover((int) ordemCompra.getId());

                                        qtdeOrdensTotais += 2;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }

                                    //execução parcial da ordem de venda e total da de compra
                                } else if (qtdeVenda > qtdeCompra) {
                                    //verifica se é ordem 0 para dar o desconto de 10% no valor
                                    if (ordemCompra.getTipoOrdem() == 0 && Util.pegaClienteLogado().getQtdOrdem0() <= 3) {
                                        totalOperacao = ordemVenda.getValor().multiply(BigDecimal.valueOf(qtdeCompra)).multiply(BigDecimal.valueOf(0.9));
                                    } else {
                                        totalOperacao = ordemVenda.getValor().multiply(BigDecimal.valueOf(qtdeCompra));
                                    }

                                    if (contaDAO.transfere(cComprador, cVendedor, totalOperacao, mCD)) {

                                        //criar uptade para banco > setar estado da ordem
                                        ordemVenda.setEstadoOrdem(1);
                                        ordemCompra.setEstadoOrdem(2);
                                        altera(ordemVenda);
                                        altera(ordemCompra);

                                        //nova execução ordem venda
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemVenda, qtdeCompra);

                                        //adiciona nova execução ordem compra
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemCompra, qtdeCompra);

                                        //novo ativo na carteira da conta do comprador
                                        Ativo ativo = ativoDAO.busca(ordemCompra.getTicker());
                                        carteiraAtivos.atualizaCarteira(ativo, cComprador, cVendedor, qtdeCompra, contaDAO, ativoDAO, clienteDAO);

                                        //remover as ordens ou atualizar
                                        //ajustar método para banco > atualizar ordem, setando quantidade
                                        ordemVenda.setQtde(ordemVenda.getQtde() - qtdeCompra);
                                        altera(ordemVenda);
//                                        remover((int) ordemCompra.getId());

                                        qtdeOrdensTotais++;
                                        qtdeOrdensParciais++;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }
                                    //execucao total da ordem de venda e parcial da de compra 
                                } else {
                                    //verifica se é ordem 0 para dar o desconto de 10% no valor
                                    //já foi feito acima antes dos IFs

                                    if (contaDAO.transfere(cComprador, cVendedor, totalOperacao, mCD)) {

                                        ordemVenda.setEstadoOrdem(2);
                                        ordemCompra.setEstadoOrdem(1);
                                        altera(ordemVenda);
                                        altera(ordemCompra);

                                        //nova execução ordem venda
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemVenda, qtdeVenda);

                                        //adiciona nova execução ordem compra
                                        execucaoDAO.novaExecucao(cComprador, cVendedor, ordemCompra, qtdeVenda);

                                        //novo ativo na carteira da conta do comprador
                                        Ativo ativo = ativoDAO.busca(ordemCompra.getTicker());
                                        carteiraAtivos.atualizaCarteira(ativo, cComprador, cVendedor, qtdeVenda, contaDAO, ativoDAO, clienteDAO);

                                        //remover as ordens ou atualizar
//                                        remover((int) ordemVenda.getId());
                                        ordemCompra.setQtde(ordemCompra.getQtde() - qtdeVenda);
                                        altera(ordemCompra);

                                        qtdeOrdensTotais++;
                                        qtdeOrdensParciais++;
                                        if (ordem0) {
                                            qtdeOrdensZero++;
                                        }

                                    } else {
                                        qtdeOrdensNaoExecutadas++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        sb.append("\n __________R E L A T Ó R I O   D E   E X E C U Ç Õ E S ___________");
        sb.append("\n Quantidade de Ordens totalmente executadas              : ").append(qtdeOrdensTotais);
        sb.append("\n Quantidade de Ordens parcialmente executadas            : ").append(qtdeOrdensParciais);
        sb.append("\n Quantidade de Ordens não executadas por falta de saldo  : ").append(qtdeOrdensNaoExecutadas);
        sb.append("\n Quantidade de Ordens Zero                               : ").append(qtdeOrdensZero);

        return sb;
    }


}
