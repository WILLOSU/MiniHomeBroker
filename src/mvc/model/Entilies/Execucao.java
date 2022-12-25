/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.Entilies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import mvc.control.Util;

/* @ professor Eduardo Silvestre
*  @ author William de Sousa Mota
 * @ author Lucas Feitosa

 * CRUD de Ordem Execução. id, ordem, conta compra*, conta venda*,  quantidade,  data criação e data modificação.
 */

public class Execucao {
    
    private static long serial;
    
    private long id;
    private Ordem ordem;
    private Conta contaCompra;
    private Conta contaVenda;
    private int quantidade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    
    Util util = new Util();
 
    public Execucao() {
        this.id = ++Execucao.serial;
        this.dataCriacao = this.dataModificacao =  Util.pegaDataAtual();
    }
   

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Conta getContaCompra() {
        return contaCompra;
    }

    public void setContaCompra(Conta contaCompra) {
        this.contaCompra = contaCompra;
    }

    public Conta getContaVenda() {
        return contaVenda;
    }
    
    public void setContaVenda(Conta contaVenda) {
        this.contaVenda = contaVenda;
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataModificacao() {
        this.dataModificacao = Util.pegaDataAtual();
    }
    
    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }
    

    public Ordem getOrdem() {
        return ordem;
    }
 
    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.ordem);
        hash = 67 * hash + this.quantidade;
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
        final Execucao other = (Execucao) obj;
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (!Objects.equals(this.ordem, other.ordem)) {
            return false;
        }
        return true;
    }

    
    
    

    @Override
    public String toString() {
//            String tipo = ordem.buscaTipo(ordem.getTipoOrdem());
//            String estado = ordem.buscaEstado(ordem.getEstadoOrdem());
            return "-----------------------------------------------------------------------\n"
               + "Identificacao (id): " + id + "\n"
//               + "Ordem             : " + ordem.toString() + "\n"
//               + "Ordem Tipo        : " + tipo + "\n"
//               + "Estado Ordem      : " + estado + "\n"
               + "Conta Compra      : " + contaCompra.getId() + "      " 
               + "Conta Venda       : " + contaVenda.getId() + "\n" 
               + "Quantidade        : " + quantidade + "\n"
               + "Data Criacao      : " + util.FormataData(dataCriacao) + " "
               + "Data Modificacao  : " + util.FormataData(dataModificacao) + "\n";
    
    }

    
    
    
}
