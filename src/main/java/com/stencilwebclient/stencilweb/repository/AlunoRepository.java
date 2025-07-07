package com.stencilwebclient.stencilweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stencilwebclient.stencilweb.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
    
}
