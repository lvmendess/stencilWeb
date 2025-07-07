package com.stencilwebclient.stencilweb.service;

import java.util.List;

import com.stencilwebclient.stencilweb.models.Aluno;
import org.springframework.stereotype.Service;

import com.stencilwebclient.stencilweb.AlunoRepository;

@Service
public class AlunoService {
    private final AlunoRepository repo;

    public AlunoService(AlunoRepository repo){
        this.repo = repo;
    }

    public List<Aluno> getAllAlunos(){
        return repo.findAll();
    }

    public Aluno getAlunoById(Integer id){
        return repo.findById(id).orElse(null);
    }
}
