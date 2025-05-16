package com.stencilwebclient.stencilweb.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stencilwebclient.stencilweb.CharacterRepository;
import com.stencilwebclient.stencilweb.service.CharacterService;
import com.stencilwebclient.stencilweb.Character;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CharacterController {

    private final CharacterRepository repo;
    private final CharacterService service;
    
    /*public CharacterController(CharacterRepository repo){
        this.repo = repo;
    }*/

    public CharacterController(CharacterService service, CharacterRepository repo){
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/home")
    public String listCharacters(Model model) {
        model.addAttribute("characters", service.getAllCharacters());
        return "index";
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
