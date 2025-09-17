package com.stencilwebclient.stencilweb.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails{

    private final Usuario user;

    public SecurityUser(Usuario user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String role = "ROLE_";
        role += user.getRole();
        String prefixedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return List.of(new SimpleGrantedAuthority(prefixedRole));
        //return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword(){
        return user.getSenhaUsuario();
    }

    @Override
    public String getUsername(){
        return user.getNomeUsuario();
    }

    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() {return true;}
    @Override public boolean isEnabled() {return true;}
}
