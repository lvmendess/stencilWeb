package com.stencilwebclient.stencilweb.repository;

import com.stencilwebclient.stencilweb.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stencilwebclient.stencilweb.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
    
}
