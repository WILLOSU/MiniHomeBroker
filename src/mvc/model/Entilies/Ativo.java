/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.Entilies;

import java.math.BigDecimal;
import java.sql.Date;
import mvc.model.Entilies.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import mvc.control.Util;

/* @ professor Eduardo Silvestre
 *  @author William de Sousa Mota
 *  @author Lucas Feitosa



 CRUD de ativo. Informações importantes: 
 id, nome da empresa, ticker, total de ativos (cotas/acoes/...), preço inicial,  data criação e data modificação.


 */
public class Ativo {

    private static long serial;

    private long id;
    private String nomeEmpresa;
    private String ticker;
    private int totalAtivos;
    private BigDecimal precoInicial;
    private BigDecimal precoAtual;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private int tipoUser; /* 1 administrador / 2 Cliente 3 Cliente 2 */

    Util util = new Util();

    public Ativo() {
        this.id = ++Ativo.serial;
        this.dataModificacao = this.dataCriacao = Util.pegaDataAtual();
    }

    public long getId() {
        return id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public  int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getTotalAtivos() {
        return totalAtivos;
    }

    public void setTotalAtivos(int totalAtivos) {
        this.totalAtivos = totalAtivos;
    }

    public BigDecimal getPrecoInicial() {
        return precoInicial;
    }

    public void setPrecoInicial(BigDecimal preçoInicial) {
        this.precoInicial = preçoInicial;
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
    
    public void setDataModificacao(LocalDateTime dataMod) {
        this.dataModificacao = dataMod;
    }
    
     public void setPrecoAtual(BigDecimal precoAtual) {
        this.precoAtual = precoAtual;
    }

    public String buscaTipo(int tipo) {
        if (tipo == 1) {
            return "Administrador";
        } else if (tipo == 2) {
            return "Cliente";
        } else if (tipo == 3) {
            return "Cliente 2";
        } else {
            return null;
        }
    }

    public boolean validaTipo(int id) {
        if (id == 1 || id == 2) {
            return true;
        }
        return false;
    }
    
    public void subTotal(int qtd){
        if(this.totalAtivos - qtd >= 0){
            this.totalAtivos -= qtd;
        }
    }
    
    
    public BigDecimal getPrecoAtual() {
        return precoAtual;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.nomeEmpresa);
        hash = 97 * hash + Objects.hashCode(this.ticker);
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
        final Ativo other = (Ativo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nomeEmpresa, other.nomeEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.ticker, other.ticker)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------------------------\n"
                + "Identificacao (id): " + id + "\n"
                + "Nome da Empresa   : " + nomeEmpresa + "\n"
                + "Ticker            : " + ticker + "\n"
                + "Total de Ativos   : " + totalAtivos + "\n"
                + "Preço Inicial     : " + precoInicial + "\n"
                + "Preço Atual       : " + precoAtual + "\n"
                + "Data Criacao      : " + util.FormataData(dataCriacao) + "\n"
                + "Data Modificacao  : " + util.FormataData(dataModificacao) + "\n";
    }



    
}
