package com.stencilwebclient.stencilweb.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Usuario")
public class User {
    
    @Id
    @Column(name="idUsuario", nullable = false)
    private int idUsuario;

    @Column(name="nomeUsuario", nullable = false)
    private String nomeUsuario;

    @Column(name="senhaUsuario", nullable = false)
    private String senhaUsuario;

    @Column(name="tipo", nullable=false)
    private char tipo;

    private String role;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = "ROLE_"+role;
    }

}
