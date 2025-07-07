package com.stencilwebclient.stencilweb.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Professores")
public class Professor {

    @Id
    @Column(name="idProf", nullable = false)
    private int idProf;

    @Column(name="nomeProf")
    private String nomeProf;

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

}
