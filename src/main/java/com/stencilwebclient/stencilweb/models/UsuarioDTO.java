package com.stencilwebclient.stencilweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class UsuarioDTO {

    @NotEmpty(message = "O nome do usuário é necessário")
    private String nomeUsuario;

    @NotEmpty(message = "A senha do usuário é necessária")
    private String senhaUsuario;

    private char role;

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

    public char getRole() {
        return role;
    }

    public void setRole(char role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role.charAt(0);
    }

}
