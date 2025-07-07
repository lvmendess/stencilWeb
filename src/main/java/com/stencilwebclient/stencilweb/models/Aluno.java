package com.stencilwebclient.stencilweb.models;

import io.micrometer.common.lang.Nullable;
import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Aluno")
public class Aluno {
    
    @Id
    @Column(name="idAluno")
    private Integer idAluno;

    @Column(name="idUsuario")
    private Integer idUsuario;

    @Column(name="nomeAluno")
    private String nomeAluno;

    @Nullable
    @Column(name="nick")
    private String nick;

    @Column(name="skin")
    private String skin;

    @Column(name="xp")
    private int xp;

    @Nullable
    @Column(name="ofensiva")
    private int ofensiva;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="idUsuario", referencedColumnName = "idUsuario",
                foreignKey = @ForeignKey(name="FK_idUsuario"))
    private User user;

    public Aluno(){}
    
    /*public Aluno(int idAluno, String nomeAluno, int xp, String skin) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.skin = skin;
        this.xp = xp;
    }*/

    public Aluno(int idAluno, String nomeAluno, String nick, int xp, int ofensiva, String skin) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.skin = skin;
        this.xp = xp;
        this.ofensiva = ofensiva;
        this.nick = nick;
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

    @Nullable
    public String getNick() {
        return nick;
    }

    @Nullable
    public int getOfensiva() {
        return ofensiva;
    }

    public int getLevel() {
        return 1 + xp / 100;
    }

}
