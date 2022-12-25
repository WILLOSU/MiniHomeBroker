/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.Entilies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import mvc.control.Util;
import mvc.model.enums.EstadoOrdem;
import mvc.model.enums.TipoOrdem;

/* @ professor Eduardo Silvestre
 *  @author William de Sousa Mota
 *  @author Lucas Feitosa

 * 
 * CRUD de Ordem.: id, conta*, tipo da ordem (compra, venda, ordem 0), ticker, quantidade, valor, valor total, estado (parcial, total, não),  data criação e data modificação. 
 */
public class Ordem {

    private static long serial;

    private long id;
    private Conta conta;
    private int tipoOrdem;    //0 ordem 0,       1 compra,  2 venda
    private int estadoOrdem;  //0 nao executada, 1 parcial, 2 completa

    private String ticker;
    private int qtde;
    private BigDecimal valor;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    
    Util util = new Util();

    public Ordem() {
        this.id = ++Ordem.serial;
        this.dataCriacao = this.dataModificacao =  Util.pegaDataAtual();
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public static long getSerial() {
        return serial;
    }

    public long getId() {
        return id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
        this.setValorTotal();
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    private void setValorTotal() {
        valorTotal = valor.multiply(new BigDecimal(qtde));
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao() {
        this.dataModificacao = Util.pegaDataAtual();
    }
    
    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
    /**
     * @param tipoOrdem 0 ordem 0, 1 compra,  2 venda
     */
    public int getTipoOrdem() {
        return tipoOrdem;
    }
    
    /**
     * @param tipoOrdem 0 ordem 0, 1 compra,  2 venda
     */
    public void setTipoOrdem(int tipoOrdem) {
        this.tipoOrdem = tipoOrdem;
    }
    
    /**
     * @param estadoOrdem = 0 nao executada, 1 parcial, 2 completa
     */
    public int getEstadoOrdem() {
        return estadoOrdem;
    }
    
    /**
     * @param estadoOrdem = 0 nao executada, 1 parcial, 2 completa
     */
    public void setEstadoOrdem(int estadoOrdem) {
        this.estadoOrdem = estadoOrdem;
    }
    
    public String buscaTipo(int tipo){
        if (tipo == 0){
            return "Ordem 0";
        }else if (tipo == 1){
            return "Compra";
        }else if (tipo == 2){
            return "Venda";
        }else{
            return null;
        }
    }
    
    public String buscaEstado(int estadoOrdem){
        if (estadoOrdem == 0){
            return "Não Executada";
        }else if (estadoOrdem == 1){
            return "Execução parcial";
        }else if (estadoOrdem == 2){
            return "Execução completa";
        }else{
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 73 * hash + Objects.hashCode(this.conta);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ordem other = (Ordem) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.conta, other.conta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        long idConta = conta.getId();
        return
            "| " + util.textoColuna("ID: " + id, 7)
            + " | " + util.textoColuna("ID Conta: " + idConta, 12)
            + " | " + util.textoColuna("Ticker: " + ticker, 13)
            + " | " + util.textoColuna("Qtde: " + qtde, 10)
            + " | " + util.textoColuna("Valor: " + valor, 13)
            + " | " + util.textoColuna("Valor Total: " + valorTotal, 21) + " |";

    }

    

    
}
