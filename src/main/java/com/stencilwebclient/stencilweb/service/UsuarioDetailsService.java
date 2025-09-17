package com.stencilwebclient.stencilweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stencilwebclient.stencilweb.models.SecurityUser;
import com.stencilwebclient.stencilweb.models.Usuario;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService{

    private final UsuarioRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDetailsService(UsuarioRepository repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return repo.findByNomeUsuario(username)
            .map(SecurityUser::new)
            .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public Usuario findUserByUsername(String username){
        return repo.findByNomeUsuario(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public void createUser(String nomeUsuario, String rawSenhaUsuario, char role){
        Usuario user = new Usuario();
        user.setNomeUsuario(nomeUsuario);
        user.setSenhaUsuario(passwordEncoder.encode(rawSenhaUsuario));
        user.setRole(role);
        repo.save(user);
    }

    public void editUser(Integer idUsuario, String nomeUsuario, String rawSenhaUsuario, char role){
        Usuario user = getUsuarioById(idUsuario);
        user.setNomeUsuario(nomeUsuario);
        user.setSenhaUsuario(passwordEncoder.encode(rawSenhaUsuario));
        user.setRole(role);
        repo.save(user);
    }

    public List<Usuario> getAll(){
        return repo.findAll();
    }

    public Usuario getUsuarioById(Integer id){
        return repo.findById(id).orElse(null);
    }

    public List<String> findUsuariosSemAluno(){
        return repo.findUsuariosWithoutAluno();
    }
    
}