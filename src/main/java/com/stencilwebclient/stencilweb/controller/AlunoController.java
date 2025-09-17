package com.stencilwebclient.stencilweb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import com.stencilwebclient.stencilweb.models.AlunoDTO;
import com.stencilwebclient.stencilweb.models.Usuario;
import com.stencilwebclient.stencilweb.repository.UsuarioRepository;
import com.stencilwebclient.stencilweb.service.UsuarioDetailsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.stencilwebclient.stencilweb.models.Aluno;
import com.stencilwebclient.stencilweb.repository.AlunoRepository;
import com.stencilwebclient.stencilweb.service.AlunoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class AlunoController {

    private static final Logger log = LoggerFactory.getLogger(AlunoController.class);

    private final AlunoRepository repo;
    private final AlunoService service;
    private final UsuarioRepository userRepo;
    private final UsuarioDetailsService userDetailsService;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public AlunoController(AlunoService service, AlunoRepository repo, UsuarioRepository userRepo, UsuarioDetailsService userDetailsService){
        this.service = service;
        this.repo = repo;
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("")
    public String landingPage() {
        return "redirect:/professor/menu";
    }

    @GetMapping("/professor/menu")
    public String listAlunos(Model model) {
        model.addAttribute("alunos", service.getAllAlunos());
        return "crud";
    }

    @GetMapping("/aluno/{id}")
    public String getAlunoById(Model model, @PathVariable int id){
        Aluno aluno = service.getAlunoById(id);
        if(aluno==null){
            System.out.println("aluno "+id+" nao encontrado");
        }else{
            System.out.println("aluno "+aluno.getNomeAluno()+" encontrado");
        }
        model.addAttribute("aluno", service.getAlunoById(id));
        return "aluno";
    }

    @GetMapping("/aluno/user/{id}")
    public String redirectAluno(@PathVariable int id) {
        Integer idAluno = service.findAlunoIdByIdUsuario(id);
        return "redirect:/aluno/"+idAluno;
    }
    

    @GetMapping("/api/characters")
    public List<com.stencilwebclient.stencilweb.models.Aluno> getAllAlunos() {
        return repo.findAll();
    }

    @GetMapping("professor/criar")
    public String showCreatePage(Model model) {
        AlunoDTO alunoDTO = new AlunoDTO();
        model.addAttribute("alunoDTO", alunoDTO);
        List<String> usuarios = userDetailsService.findUsuariosSemAluno();
        model.addAttribute("usuarios", usuarios);
        return "createAluno";
    }

    @PostMapping("professor/criar")
    public String createAluno(@Valid @ModelAttribute AlunoDTO alunoDTO, BindingResult result) {
        if (alunoDTO.getSkin() == null || alunoDTO.getSkin().isEmpty()) {
            result.addError(new FieldError("alunoDTO", "skin", "Uma imagem para o personagem é necessária"));
        }
        if (result.hasErrors()) {
            return "createAluno";
        }

        MultipartFile image = alunoDTO.getSkin();
        String storageFileName = null;
        try {
            String original = Paths.get(image.getOriginalFilename()).getFileName().toString();
            storageFileName = System.currentTimeMillis() + "_" + original;

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Path target = uploadPath.resolve(storageFileName);
            image.transferTo(target.toFile());

        } catch (Exception e) {
            log.error("Failed saving uploaded file during create", e);
            result.reject("file", "Could not save file");
            return "createAluno";
        }

        Aluno aluno = new Aluno(
                alunoDTO.getNomeAluno(),
                alunoDTO.getNick(),
                storageFileName,
                alunoDTO.getXp(),
                alunoDTO.getOfensiva(),
                userDetailsService.findUserByUsername(alunoDTO.getNomeUsuario())
        );
        repo.save(aluno);

        return "redirect:/professor/menu";
    }

    @GetMapping("/professor/editar")
    public String showEditPage(Model model, @RequestParam int id) {
        try{
            Aluno aluno = repo.findById(id).orElse(null);
            if (aluno == null) {
                log.warn("Aluno {} not found", id);
                return "redirect:/professor/menu";
            }
            model.addAttribute("aluno", aluno);
            AlunoDTO alunoDTO = new AlunoDTO();
            alunoDTO.setNomeAluno(aluno.getNomeAluno());
            alunoDTO.setNick(aluno.getNick());
            alunoDTO.setXp(aluno.getXp());
            alunoDTO.setOfensiva(aluno.getOfensiva());
            model.addAttribute("alunoDTO", alunoDTO);
            List<String> usuarios = userDetailsService.findUsuariosSemAluno();
            usuarios.addFirst(aluno.getUser().getNomeUsuario());
            model.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            log.error("Error preparing edit page", e);
        }
        return "editAluno";
    }

    @PostMapping("/professor/editar")
    public String updateAluno(Model model, @RequestParam int id, @Valid @ModelAttribute AlunoDTO alunoDTO, BindingResult result) {
        try {
            Aluno aluno = repo.findById(id).orElse(null);
            if (aluno == null) {
                result.reject("notfound", "Aluno não encontrado");
                return "editAluno";
            }
            model.addAttribute("aluno", aluno);
            if (result.hasErrors()) {
                return "editAluno";
            }

            MultipartFile image = alunoDTO.getSkin();
            if (image != null && !image.isEmpty()) {
                String original = Paths.get(image.getOriginalFilename()).getFileName().toString();
                String storageFileName = System.currentTimeMillis() + "_" + original;
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                try {
                    Files.createDirectories(uploadPath);
                    Path target = uploadPath.resolve(storageFileName);
                    image.transferTo(target.toFile());
                    // delete old image from same uploadPath
                    if (aluno.getSkin() != null) {
                        Files.deleteIfExists(uploadPath.resolve(aluno.getSkin()));
                    }
                    aluno.setSkin(storageFileName);
                } catch (IOException e) {
                    log.error("Failed saving uploaded file during update", e);
                    result.reject("file", "Could not save file");
                    return "editAluno";
                }
            }
            aluno.setNomeAluno(alunoDTO.getNomeAluno());
            aluno.setNick(alunoDTO.getNick());
            aluno.setXp(aluno.getXp() + alunoDTO.getIncrementoXp());
            aluno.setOfensiva(aluno.getOfensiva() + alunoDTO.getIncrementoOfensiva());
            aluno.setUser(userDetailsService.findUserByUsername(alunoDTO.getNomeUsuario()));
            repo.save(aluno);
        } catch (Exception e) {
            log.error("Error updating aluno", e);
            result.reject("server", "Erro interno");
            return "editAluno";
        }
        return "redirect:/professor/menu";
    }

    @GetMapping("/professor/remover")
    public String deleteAluno(@RequestParam int id) {
        Aluno aluno = repo.findById(id).orElse(null);
        if (aluno != null) {
            deleteImage(aluno.getSkin());
            repo.delete(aluno);
        }
        return "redirect:/professor/menu";
    }

    private void deleteImage(String image) {
        if (image == null || image.isBlank()) return;
        Path path = Paths.get(uploadDir).resolve(image).toAbsolutePath().normalize();
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            log.warn("Could not delete image {}: {}", path, e.getMessage());
        }
    }
}
