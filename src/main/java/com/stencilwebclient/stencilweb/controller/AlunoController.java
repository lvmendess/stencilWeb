package com.stencilwebclient.stencilweb.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import com.stencilwebclient.stencilweb.models.AlunoDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.stencilwebclient.stencilweb.models.Aluno;
import com.stencilwebclient.stencilweb.repository.AlunoRepository;
import com.stencilwebclient.stencilweb.service.AlunoService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AlunoController {

    private final AlunoRepository repo;
    private final AlunoService service;

    public AlunoController(AlunoService service, AlunoRepository repo){
        this.service = service;
        this.repo = repo;
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

    @GetMapping("/info")
    public String info(){
        return "funcionando";
    }

    @GetMapping("/api/characters")
    public List<com.stencilwebclient.stencilweb.models.Aluno> getAllAlunos() {
        return repo.findAll();
    }

    @GetMapping("professor/criar")
    public String showCreatePage(Model model) {
        AlunoDTO alunoDTO = new AlunoDTO();
        model.addAttribute("alunoDTO", alunoDTO);
        return "createAluno";
    }

    @PostMapping("professor/criar")
    public String createAluno(@Valid @ModelAttribute AlunoDTO alunoDTO, BindingResult result) {
        if (alunoDTO.getSkin().isEmpty()) {
            result.addError(new FieldError("alunoDTO", "skin", "Uma imagem para o personagem é necessária"));
        }
        if (result.hasErrors()) {
            return "createAluno";
        }
        // armazenar a imagem no servidor
        MultipartFile image = alunoDTO.getSkin();
        Date date = new Date();
        String storageFileName = date.getTime() + "_" + image.getOriginalFilename();
        try {
            String imagesDir = "src/main/resources/static/images/";
            Path path = Paths.get(imagesDir);
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(imagesDir + storageFileName), StandardCopyOption.REPLACE_EXISTING );
            }
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            return "createAluno";
        }
        // criar objeto
        Aluno aluno = new Aluno(alunoDTO.getNomeAluno(),
                                alunoDTO.getNick(),
                                alunoDTO.getXp(),
                                alunoDTO.getOfensiva(),
                                storageFileName);
        repo.save(aluno);

        return "redirect:/professor/menu";
    }

    @GetMapping("/professor/editar")
    public String showEditPage(Model model, @RequestParam int id) {
        try{
            Aluno aluno = repo.findById(id).get();
            model.addAttribute("aluno", aluno);
            AlunoDTO alunoDTO = new AlunoDTO();
            alunoDTO.setNomeAluno(aluno.getNomeAluno());
            alunoDTO.setNick(aluno.getNick());
            alunoDTO.setXp(aluno.getXp());
            alunoDTO.setOfensiva(aluno.getOfensiva());
            model.addAttribute("alunoDTO", alunoDTO);
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        return "editAluno";
    }

    @PostMapping("/professor/editar")
    public String updateAluno(Model model, @RequestParam int id, @Valid @ModelAttribute AlunoDTO alunoDTO, BindingResult result) {
        try {
            Aluno aluno = repo.findById(id).get();
            model.addAttribute("aluno", aluno);
            if (result.hasErrors()) {
                return "editAluno";
            }
            if (!alunoDTO.getSkin().isEmpty()) {
                deleteImage(aluno.getSkin());
                // armazenar a imagem no servidor
                MultipartFile image = alunoDTO.getSkin();
                Date date = new Date();
                String imagesDir = "src/main/resources/static/images/";
                String storageFileName = date.getTime() + "_" + image.getOriginalFilename();
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(imagesDir + storageFileName), StandardCopyOption.REPLACE_EXISTING );
                }
                aluno.setSkin(storageFileName);
            }
            aluno.setNomeAluno(alunoDTO.getNomeAluno());
            aluno.setNick(alunoDTO.getNick());
            aluno.setXp(aluno.getXp() + alunoDTO.getIncrementoXp());
            aluno.setOfensiva(aluno.getOfensiva() + alunoDTO.getIncrementoOfensiva());
            repo.save(aluno);
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
        return "redirect:/professor/menu";
    }

    @GetMapping("/professor/remover")
    public String deleteAluno(@RequestParam int id) {
        Aluno aluno = repo.findById(id).get();
        deleteImage(aluno.getSkin());
        repo.delete(aluno);
        return "redirect:/professor/menu";
    }

    private void deleteImage(String image) {
        String imagesDir = "src/main/resources/static/images/";
        Path path = Paths.get(imagesDir + image);
        try {
            Files.delete(path);
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
}
