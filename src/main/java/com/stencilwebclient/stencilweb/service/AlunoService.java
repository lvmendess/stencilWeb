package com.stencilwebclient.stencilweb.service;

import java.util.List;

import com.stencilwebclient.stencilweb.models.Aluno;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stencilwebclient.stencilweb.repository.AlunoRepository;

@Service
public class AlunoService {
    private final AlunoRepository repo;

    public AlunoService(AlunoRepository repo){
        this.repo = repo;
    }

    public List<com.stencilwebclient.stencilweb.models.Aluno> getAllAlunos(){
        return repo.findAll();
    }

    public com.stencilwebclient.stencilweb.models.Aluno getAlunoById(Integer id){
        return repo.findById(id).orElse(null);
    }

    public Integer findAlunoIdByIdUsuario(Integer userId) {
        return repo.findAlunoIdByIdUsuario(userId);
    }
}
