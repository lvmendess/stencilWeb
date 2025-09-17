package com.stencilwebclient.stencilweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.stencilwebclient.stencilweb.models.Usuario;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;
import com.stencilwebclient.stencilweb.service.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //private final UsuarioRepository userRepo;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler successHandler) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error").permitAll()
                        .requestMatchers("/admin/**").hasRole("A")
                        .requestMatchers("/professor/**").hasAnyRole("T","A")
                        .requestMatchers("/aluno/**").hasAnyRole("S", "T", "A")
                        .anyRequest().authenticated()
                    )
                .formLogin(form -> form
                        .successHandler(successHandler).permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
        UsuarioDetailsService userDetailsService, PasswordEncoder passwordEncoder){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder);
            return provider;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(UsuarioRepository userRepo){
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();
            String username = authentication.getName();
            String redirect = "/login";

            if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_A"))){
                redirect = "/admin/menu";
            }else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_T"))){
                redirect = "/professor/menu";
            }else if(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_S"))){
                Usuario user = userRepo.findByNomeUsuario(username)
                .orElseThrow(()-> new IllegalStateException("usuario não encontrado"));
                int id = user.getIdUsuario();
                redirect = "/aluno/user/"+id;
            }

            response.sendRedirect(redirect);
        };
    }
}
