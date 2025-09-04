package com.stencilwebclient.stencilweb.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;

public class AlunoDTO {

    @NotEmpty(message = "O nome do aluno é necessário")
    private String nomeAluno;

    @NotEmpty(message = "O apelido do aluno é necessário")
    private String nick;

    private MultipartFile skin;

    @Min(0)
    private int xp;

    private int incrementoXp;

    @Min(0)
    private int ofensiva;

    private int incrementoOfensiva;

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public MultipartFile getSkin() {
        return skin;
    }

    public void setSkin(MultipartFile skin) {
        this.skin = skin;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getOfensiva() {
        return ofensiva;
    }

    public void setOfensiva(int ofensiva) {
        this.ofensiva = ofensiva;
    }

    public int getIncrementoOfensiva() {
        return incrementoOfensiva;
    }

    public void setIncrementoOfensiva(int incrementoOfensiva) {
        this.incrementoOfensiva = incrementoOfensiva;
    }

    public int getIncrementoXp() {
        return incrementoXp;
    }

    public void setIncrementoXp(int incrementoXp) {
        this.incrementoXp = incrementoXp;
    }
}
