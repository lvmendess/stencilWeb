package com.stencilwebclient.stencilweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stencilwebclient.stencilweb.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    @Query("SELECT u.nomeUsuario FROM Usuario u LEFT JOIN Aluno a ON a.usuario_id_s = u.idUsuario WHERE a.usuario_id_s IS NULL AND u.roleUsuario = 'S';")
    List<String> findUsuariosWithoutAluno();
}
