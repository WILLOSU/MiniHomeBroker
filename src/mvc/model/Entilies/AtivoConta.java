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

/**
 *
 * @author Lucas
 */
public class AtivoConta {
    private static long serial;
    private long id;
    
    private Conta conta;
    private Ativo ativo;
    private int totalAtivos;
    private BigDecimal valorCompra;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    
    Util util = new Util();

    public AtivoConta() {
        this.id = ++AtivoConta.serial;
        this.dataCriacao = this.dataModificacao = Util.pegaDataAtual();
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

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }
    
    

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao() {
        this.dataModificacao = Util.pegaDataAtual();
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public int getTotalAtivos() {
        return totalAtivos;
    }

    public void setTotalAtivos(int totalAtivos) {
        this.totalAtivos = totalAtivos;
    }
    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.conta);
        hash = 53 * hash + Objects.hashCode(this.ativo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AtivoConta other = (AtivoConta) obj;
        if (!Objects.equals(this.conta, other.conta)) {
            return false;
        }
        if (!Objects.equals(this.ativo, other.ativo)) {
            return false;
        }
        return true;
    }

    
    
     @Override
    public String toString() {

        return
            "| " + util.textoColuna("" + id,  3)
            + " | " + util.textoColuna(conta.getCliente().getNome(), 12)
            + " | " + util.textoColuna(ativo.getTicker(), 6)
            + " | " + util.textoColuna("" + totalAtivos, 5)
            + " | " + util.textoColuna("" + this.valorCompra.multiply(new BigDecimal(totalAtivos)), 11)
            + " | " + util.textoColuna("" + ativo.getPrecoAtual(), 13)
            + " | " + util.textoColuna(util.FormataData(dataCriacao), 12) + " |\n";
    }
    
    public String toStringHeader() {
        return
            
            "| " + util.textoColuna("ID",  3)
            + " | " + util.textoColuna("Cliente", 12)
            + " | " + util.textoColuna("Ativo", 6)
            + " | " + util.textoColuna("Total", 5)
            + " | " + util.textoColuna("Valor Total", 11)
            + " | " + util.textoColuna("Cotacao Atual", 13)
            + " | " + util.textoColuna("Data Criação", 12) + " |\n"
            + "-------------------------------------------------------------------------\n";
    }
    
}
