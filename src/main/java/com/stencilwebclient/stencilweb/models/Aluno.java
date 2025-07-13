package com.stencilwebclient.stencilweb.models;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name="Aluno")
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idAluno")
    private Integer idAluno;

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

    public Aluno(){}

    public Aluno(String nomeAluno, String nick, int xp, int ofensiva, String skin) {
        this.nomeAluno = nomeAluno;
        this.skin = skin;
        this.xp = xp;
        this.ofensiva = ofensiva;
        this.nick = nick;
    }

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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getSkin() {
        return skin;
    }

    public int getXp() {
        return xp;
    }

    @Nullable
    public String getNick() {
        return nick;
    }

    @Nullable
    public int getOfensiva() {
        return ofensiva;
    }

    public int getLevel() {
        return 1 + xp / 1000;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setNick(@Nullable String nick) {
        this.nick = nick;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setOfensiva(int ofensiva) {
        this.ofensiva = ofensiva;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }
}
