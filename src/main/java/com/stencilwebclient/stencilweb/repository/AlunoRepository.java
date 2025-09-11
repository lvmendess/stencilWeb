package com.stencilwebclient.stencilweb.repository;

import com.stencilwebclient.stencilweb.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stencilwebclient.stencilweb.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

    @Query("SELECT a.idAluno FROM Aluno a WHERE a.user.idUsuario = :userId")
    Integer findAlunoIdByIdUsuario(@Param("userId") Integer userId);

}
