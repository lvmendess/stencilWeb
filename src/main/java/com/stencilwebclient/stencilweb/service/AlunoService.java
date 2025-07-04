package com.stencilwebclient.stencilweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stencilwebclient.stencilweb.AlunoRepository;

@Service
public class AlunoService {
    private final AlunoRepository repo;

    public AlunoService(AlunoRepository repo){
        this.repo = repo;
    }

    public List<com.stencilwebclient.stencilweb.Aluno> getAllAlunos(){
        return repo.findAll();
    }

    public com.stencilwebclient.stencilweb.Aluno getAlunoById(Integer id){
        return repo.findById(id).orElse(null);
    }
}
