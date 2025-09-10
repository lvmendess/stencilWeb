package com.stencilwebclient.stencilweb.controller;

import com.stencilwebclient.stencilweb.models.Aluno;
import com.stencilwebclient.stencilweb.models.AlunoDTO;
import com.stencilwebclient.stencilweb.models.UsuarioDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.stencilwebclient.stencilweb.models.Usuario;
import com.stencilwebclient.stencilweb.repository.AlunoRepository;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;
import com.stencilwebclient.stencilweb.service.AlunoService;
import com.stencilwebclient.stencilweb.service.UsuarioDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UserController {

    private final UsuarioRepository repo;
    private final UsuarioDetailsService service;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UsuarioDetailsService service, UsuarioRepository repo){
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/admin/menu")
    public String menu(Model model) {
        model.addAttribute("users", service.getAll());
        return "dashboard";
    }

    @GetMapping("/admin/cadastrar")
    public String cadastrar(Model model) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        model.addAttribute("usuarioDTO", usuarioDTO);
        return "createUser";
    }

    @PostMapping("/admin/cadastrar")
    public String cadastrarUser(@Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result) {
        //TODO: process POST request
        if (result.hasErrors()) {
            return "createUser";
        }
        service.createUser(usuarioDTO.getNomeUsuario(), usuarioDTO.getSenhaUsuario(), usuarioDTO.getRole());
        return "redirect:/admin/menu";
    }

    @GetMapping("/admin/editar")
    public String showEditPage(Model model, @RequestParam int id) {
        try{
            Usuario usuario = repo.findById(id).orElse(null);
            if (usuario == null) {
                log.warn("User {} not found", id);
                return "redirect:/admin/menu";
            }
            model.addAttribute("usuario", usuario);
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNomeUsuario(usuario.getNomeUsuario());
            usuarioDTO.setRole(usuario.getRole());
            usuarioDTO.setSenhaUsuario(usuario.getSenhaUsuario());
            model.addAttribute("usuarioDTO", usuarioDTO);
        } catch (Exception e) {
            log.error("Error preparing edit page", e);
        }
        return "editUser";
    }

    @PostMapping("/admin/editar")
    public String updateUser(Model model, @RequestParam int id, @Valid @ModelAttribute UsuarioDTO usuarioDTO, BindingResult result) {
        try {
            Usuario usuario = repo.findById(id).orElse(null);
            if (usuario == null) {
                result.reject("notfound", "Usuário não encontrado");
                return "editUser";
            }
            model.addAttribute("usuario", usuario);
            if (result.hasErrors()) {
                return "editUser";
            }
            char role = usuario.getRole().charAt(0);
            if (role != 'A' && role != 'T' && role != 'S') {
                throw new IllegalArgumentException("Invalid role");
            }
            service.editUser(id, usuarioDTO.getNomeUsuario(), usuarioDTO.getSenhaUsuario(), role);
        } catch (Exception e) {
            log.error("Error updating usuario", e);
            result.reject("server", "Erro interno");
            return "editUser";
        }
        return "redirect:/admin/menu";
    }
    
    @GetMapping("/admin/remover")
    public String removerUser(@RequestParam int id) {
        Usuario usuario = repo.findById(id).orElse(null);
        if (usuario != null) {
            repo.delete(usuario);
        }
        return "redirect:/admin/menu";
    }

    
}
