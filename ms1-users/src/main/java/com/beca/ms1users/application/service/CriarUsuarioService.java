package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.EmailJaCadastradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.CriarUsuarioUseCase;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class CriarUsuarioService implements CriarUsuarioUseCase {

    private final UsuarioRepositoryPort repositorio;
    private final PasswordHasherPort hasher;

    public CriarUsuarioService(UsuarioRepositoryPort repositorio,
                               PasswordHasherPort hasher) {
        this.repositorio = repositorio;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public Usuario executar(String nome, String email, String senhaRaw) {
        if (repositorio.existsByEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }
        String senhaHash = hasher.hash(senhaRaw);
        Usuario usuario = Usuario.criar(nome, email, senhaHash);
        return repositorio.save(usuario);
    }
}
