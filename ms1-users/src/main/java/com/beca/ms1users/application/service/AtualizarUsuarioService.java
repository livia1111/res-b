package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.EmailJaCadastradoException;
import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.AtualizarUsuarioUseCase;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class AtualizarUsuarioService implements AtualizarUsuarioUseCase {

    private final UsuarioRepositoryPort repositorio;
    private final PasswordHasherPort hasher;

    public AtualizarUsuarioService(UsuarioRepositoryPort repositorio,
                                   PasswordHasherPort hasher) {
        this.repositorio = repositorio;
        this.hasher = hasher;
    }

    @Override
    @Transactional
    public Usuario executar(Long id, String nome, String email, String senhaRaw) {
        Usuario existente = repositorio.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        // Se o e-mail mudou, verifica duplicidade
        if (!existente.getEmail().equals(email) && repositorio.existsByEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }

        existente.setNome(nome);
        existente.setEmail(email);
        existente.setSenha(hasher.hash(senhaRaw));
        return repositorio.save(existente);
    }
}
