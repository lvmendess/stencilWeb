package com.stencilwebclient.stencilweb.repository;

import com.stencilwebclient.stencilweb.models.Aluno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stencilwebclient.stencilweb.models.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>{

    @Query("SELECT a.idAluno FROM Aluno a WHERE a.user.idUsuario = :userId")
    Integer findAlunoIdByIdUsuario(@Param("userId") Integer userId);

    @Query("SELECT u.nomeUsuario FROM Usuario u LEFT JOIN Aluno a ON a.usuario_id_s = u.idUsuario WHERE a.usuario_id_s IS NULL AND u.roleUsuario = 'S';")
    List<String> findUsuariosWithoutAluno();
}
