package com.stencilwebclient.stencilweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stencilwebclient.stencilweb.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
