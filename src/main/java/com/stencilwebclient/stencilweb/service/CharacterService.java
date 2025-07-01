package com.stencilwebclient.stencilweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stencilwebclient.stencilweb.CharacterRepository;

@Service
public class CharacterService {
    private final CharacterRepository repo;

    public CharacterService(CharacterRepository repo){
        this.repo = repo;
    }

    public List<com.stencilwebclient.stencilweb.Character> getAllCharacters(){
        return repo.findAll();
    }

    public com.stencilwebclient.stencilweb.Character getCharacterById(Integer id){
        return repo.findById(id).orElse(null);
    }
}
