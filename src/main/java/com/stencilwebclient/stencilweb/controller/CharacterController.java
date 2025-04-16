package com.stencilwebclient.stencilweb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stencilwebclient.stencilweb.CharacterRepository;
import com.stencilwebclient.stencilweb.Character;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CharacterController {

    private final CharacterRepository repo;
    
    public CharacterController(CharacterRepository repo){
        this.repo = repo;
    }

    @GetMapping("/info")
    public String info(){
        return "funcionando";
    }

    @GetMapping("/api/characters")
    public List<com.stencilwebclient.stencilweb.Character> getAllCharacters() {
        return repo.findAll();
    }
    
}
