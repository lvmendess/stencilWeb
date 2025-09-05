package com.stencilwebclient.stencilweb.controller;

import org.springframework.stereotype.Controller;
import com.stencilwebclient.stencilweb.models.Usuario;
import com.stencilwebclient.stencilweb.repository.AlunoRepository;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;
import com.stencilwebclient.stencilweb.service.AlunoService;
import com.stencilwebclient.stencilweb.service.UsuarioDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UsuarioRepository repo;
    private final UsuarioDetailsService service;

    public UserController(UsuarioDetailsService service, UsuarioRepository repo){
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin/cadastrar")
    public String cadastrar() {
        return new String();
    }

    @PostMapping("/admin/cadastrar")
    public String cadastrarUser(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    @GetMapping("/admin/remover")
    public String removerUser() {
        return new String();
    }

    
}
