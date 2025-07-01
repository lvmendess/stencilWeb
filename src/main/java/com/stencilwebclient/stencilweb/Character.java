package com.stencilwebclient.stencilweb;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Characters")
public class Character {
    
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;
    private String skin;
    private int xp;

    public Character(){}
    
    public Character(int id, String name, String skin, int xp) {
        this.id = id;
        this.name = name;
        this.skin = skin;
        this.xp = xp;
    }

    public int getId() {
        return id;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
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

    public int getLevel() {
        return 1 + xp / 100;
    }

}
