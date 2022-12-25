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

import mvc.model.enums.MeioOperacao;
import mvc.model.enums.TipoOperacao;

/**
 * @ professor Eduardo Silvestre
 * @ author William de Sousa Mota
 * @ author Lucas Feitosa
 *
 *
 *
 * CRUD de movimentação de conta. Isso ajuda gerar o extrato. Informações
 * importantes: id, conta*, tipo movimento (crédito, débito), descrição, valor,
 * data criação e data modificação.
 */
public class MovimentacaoConta {

    private static long serial;
    private long id;
    private Conta conta;
    private int tipMov; /* 1 CRÉDITO / 2 DÉBITO*/

    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    

    Util util = new Util();

    public MovimentacaoConta() {
        this.id = ++MovimentacaoConta.serial;
        dataCriacao = dataModificacao = Util.pegaDataAtual();
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta contacorrente) {
        this.conta = contacorrente;
    }

    public int getTipMov() {
        return tipMov;
    }

    public void setTipMov(int tipMov) {
        this.tipMov = tipMov;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public long getId() {
        return id;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
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
    
    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.conta);
        hash = 53 * hash + Objects.hashCode(this.valor);
        hash = 53 * hash + Objects.hashCode(this.dataCriacao);
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
        final MovimentacaoConta other = (MovimentacaoConta) obj;
        if (!Objects.equals(this.conta, other.conta)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.dataCriacao, other.dataCriacao)) {
            return false;
        }
        return true;
    }

    
    
    
    public String buscaTipo(int tipo){
        if (tipo == 1){
            return "Crédito";
        }else if (tipo == 2){
            return "Débito";
        }else return "Tipo Inválido";
        
    }
    
    
    

    @Override
    public String toString() {
        
        return "-----------------------------------------------------------------------\n"
                + "Identificacao (conta): " + conta.getId() + "\n"
                + "Cliente              : " + conta.getCliente().getNome() + "\n"
                + "Valor                : " + valor + "\n"
                + "Tipo                 : " + buscaTipo(tipMov) + "\n"
                + "Descrição            : " + descricao + "\n"
                + "Data Criacao         : " + util.FormataData(dataCriacao) + "\n"
                + "Data Modificacao     : " + util.FormataData(dataModificacao) + "\n";

    }

}
