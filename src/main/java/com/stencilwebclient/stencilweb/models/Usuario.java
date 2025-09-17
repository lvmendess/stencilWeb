package com.stencilwebclient.stencilweb.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUsuario", nullable = false)
    private int idUsuario;

    @Column(name="nomeUsuario", nullable = false)
    private String nomeUsuario;

    @Column(name="senhaUsuario", nullable = false)
    private String senhaUsuario;

    @Column(name="roleUsuario", nullable=false)
    private char role;

    //private String role;

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

    public String getRole() {
        return String.valueOf(role);
    }

    public void setRole(char role) {
        this.role = role;
    }

    /*public String getRole() {
        return role;
    }*/

    /*public void setRole(char role) {
        this.role = "ROLE_"+role;
    }*/

}
