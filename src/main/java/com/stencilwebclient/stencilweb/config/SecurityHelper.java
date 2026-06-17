package com.stencilwebclient.stencilweb.config;

import com.stencilwebclient.stencilweb.repository.AlunoRepository;
import org.springframework.stereotype.Component;

@Component("securityHelper")
public class SecurityHelper {

    private final AlunoRepository alunoRepo;

    public SecurityHelper(AlunoRepository alunoRepo) {
        this.alunoRepo = alunoRepo;
    }

    public boolean isOwner(int idAluno, int idUsuario) {
        return alunoRepo.findById(idAluno)
            .map(aluno -> aluno.getUser().getIdUsuario() == idUsuario)
            .orElse(false);
    }
}