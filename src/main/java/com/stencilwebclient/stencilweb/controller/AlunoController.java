package com.stencilwebclient.stencilweb.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.stencilwebclient.stencilweb.AlunoRepository;
import com.stencilwebclient.stencilweb.service.AlunoService;

@Controller
public class AlunoController {

    private final AlunoRepository repo;
    private final AlunoService service;
    
    /*public CharacterController(CharacterRepository repo){
        this.repo = repo;
    }*/

    public AlunoController(AlunoService service, AlunoRepository repo){
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/home")
    public String listAlunos(Model model) {
        model.addAttribute("alunos", service.getAllAlunos());
        return "index";
    }

    @GetMapping("/aluno/{id}")
    public String getAlunoById(Model model, @PathVariable int id){
        model.addAttribute("aluno", service.getAlunoById(id));
        return "aluno";
    }

    @GetMapping("/info")
    public String info(){
        return "funcionando";
    }

    @GetMapping("/api/characters")
    public List<com.stencilwebclient.stencilweb.Aluno> getAllCharacters() {
        return repo.findAll();
    }

}
