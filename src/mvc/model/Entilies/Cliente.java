/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.Entilies;

import mvc.model.Entilies.Cliente;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import mvc.control.Util;
import mvc.model.enums.TipoUsuario;

/**
 *
 * @ professor Eduardo Silvestre
 *  @author William de Sousa Mota
 *  @author Lucas Feitosa
 */
public class Cliente {

    /* CRUD de cliente. Informações importantes: id, nome, endereço, CPF, telefone, login, senha, tipoUsuario, dataCriacao, dataModificacao*/
    private static long serial;

    private long id;
    private String nome;
    private String endereco;
    private String CPF;
    private String telefone;
    private String login;
    private String senha;
    private int tipoUsuario; /* 1 administrador / 2 Cliente 3 Cliente 2 */
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private boolean temConta;
    private int qtdOrdem0;
    
  

    public boolean isTemConta() {
        return temConta;
    }

    public void setTemConta(boolean temConta) {
        this.temConta = temConta;
    }

    Util util = new Util();
    

    public Cliente() {
        this.id = ++Cliente.serial;
        this.dataCriacao = this.dataModificacao =  Util.pegaDataAtual();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    
     public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDataModificacao() {
        this.dataModificacao = Util.pegaDataAtual();
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public int getQtdOrdem0() {
        return qtdOrdem0;
    }

    public void setQtdOrdem0(int qtdOrdem0) {
        this.qtdOrdem0 = qtdOrdem0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.CPF);
        hash = 97 * hash + Objects.hashCode(this.login);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.CPF, other.CPF)) {
            return false;
        }
        return true;
    }



    

   public String buscaTipo(int tipo){
        if (tipo == 1){
            return "Administrador";
        }else if (tipo == 2){
            return "Cliente";
        }else if (tipo == 3){
            return "Cliente 2";
        }else{
            return null;
        }
    }

    public boolean validaTipo(int id) {
        if (id == 1 || id == 2) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------------------------\n"
                + "Identificacao (id): " + id + "\n"
                + "Nome              : " + nome + "\n"
                + "CPF               : " + CPF + "\n"
                + "Endereco          : " + endereco + "\n"
                + "Telefone          : " + telefone + "\n"
                + "Login             : " + login + "\n"
                + "Tipo de Usuário   : " + buscaTipo(tipoUsuario) + "\n"
                + "Data Criacao      : " + util.FormataData(dataCriacao) + "\n"
                + "Data Modificacao  : " + util.FormataData(dataModificacao) + "\n";
    }

  
}
