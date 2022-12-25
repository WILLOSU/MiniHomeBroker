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
import mvc.model.DAO.ClienteDAO;
import mvc.model.DAO.ContaDAO;

/*
/*  @ professor Eduardo Silvestre
 *  @author William de Sousa Mota
 *  @author Lucas Feitosa

 
 CRUD de conta corrente. Informações importantes: id, cliente*, saldo, dataCriacao e dataModificacao. */
public class Conta {

    private static long serial;

    private long id;
    private Cliente cliente;
    private boolean contaBolsa;
    private BigDecimal saldo = new BigDecimal ("0");
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;


    Util util = new Util();

    public Conta() {
        this.id = ++Conta.serial;
        this.dataCriacao = dataModificacao = Util.pegaDataAtual();
        //BigDecimal saldo = new BigDecimal ("20000.00");

    }

    public static long getSerial() {
        return serial;
    }

    public static void setSerial(long serial) {
        Conta.serial = serial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isContaBolsa() {
        return contaBolsa;
    }

    public void setContaBolsa(boolean contaBolsa) {
        this.contaBolsa = contaBolsa;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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
    
    public void entrada(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }  
    
    public void retirada(BigDecimal valor){
        this.saldo = this.saldo.subtract(valor);
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

    @Override
    public String toString() {

        return "-----------------------------------------------------------------------\n"
                + "Identificacao (id): " + id + "\n"
                + "Cliente           : " + cliente.getNome() + "\n"
                + "Saldo             : " + saldo.toPlainString() + "\n"
                + "Data Criacao      : " + util.FormataData(dataCriacao) + "\n"
                + "Data Modificacao  : " + util.FormataData(dataModificacao) + "\n";

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.cliente);
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
        final Conta other = (Conta) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }

    public void setEndereco(String endereco) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCpf(int cpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCliente(String cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCliente(int cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

  

       

}


    
