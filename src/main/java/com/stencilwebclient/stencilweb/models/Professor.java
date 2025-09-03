package com.stencilwebclient.stencilweb.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Professores")
public class Professor {

    @Id
    @Column(name="idProf", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProf;

    @Column(name="nomeProf")
    private String nomeProf;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="usuario_id_t", referencedColumnName = "idUsuario",
                foreignKey = @ForeignKey(name="FK_idUsuario_T"))
    private Usuario user;

    public Professor(){}

    public Professor(int idProf, String nomeProf){
        this.idProf = idProf;
        this.nomeProf = nomeProf;
    }

    public int getIdProf() {
        return idProf;
    }

    public String getNomeProf() {
        return nomeProf;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}
