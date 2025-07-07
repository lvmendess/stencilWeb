package com.stencilwebclient.stencilweb;

import com.stencilwebclient.stencilweb.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{
    
}
