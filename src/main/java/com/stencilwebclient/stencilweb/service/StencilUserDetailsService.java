package com.stencilwebclient.stencilweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.stencilwebclient.stencilweb.models.StencilUser;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;

public class StencilUserDetailsService implements UserDetailsService{

    private final UsuarioRepository repo;

    public StencilUserDetailsService(UsuarioRepository repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repo.findByNomeUsuario(username)
            .map(StencilUser::new)
            .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    
}