package com.stencilwebclient.stencilweb;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Aluno")
public class Aluno {
    
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAluno;

    private String nomeAluno;
    private String nick;
    private String skin;
    private int xp;
    private int ofensiva;

    public Aluno(){}
    
    public Aluno(int idAluno, String nomeAluno, String nick, int xp, String skin, int ofensiva) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.skin = skin;
        this.xp = xp;
    }

    public int getIdAluno() {
        return idAluno;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getSkin() {
        return skin;
    }

    public int getXp() {
        return xp;
    }

    /*public void setname(String name) {
        this.name = name;
    }
    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }
    public void setXpPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }*/

    public String getNick() {
        return nick;
    }

    public int getOfensiva() {
        return ofensiva;
    }

    public int getLevel() {
        return 1 + xp / 100;
    }

}
