package com.stencilwebclient.stencilweb.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.stencilwebclient.stencilweb.models.Aluno;
import com.stencilwebclient.stencilweb.repository.AlunoRepository;
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
        Aluno aluno = service.getAlunoById(id);
        if(aluno==null){
            System.out.println("aluno "+id+" nao encontrado");
        }else{
            System.out.println("aluno "+aluno.getNomeAluno()+" encontrado");
        }
        model.addAttribute("aluno", service.getAlunoById(id));
        return "aluno";
    }

    @GetMapping("/info")
    public String info(){
        return "funcionando";
    }

    @GetMapping("/api/characters")
    public List<com.stencilwebclient.stencilweb.models.Aluno> getAllAlunos() {
        return repo.findAll();
    }

}
