package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.port.in.ExcluirUsuarioUseCase;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class ExcluirUsuarioService implements ExcluirUsuarioUseCase {

    private final UsuarioRepositoryPort repositorio;

    public ExcluirUsuarioService(UsuarioRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional
    public void executar(Long id) {
        if (!repositorio.existsById(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        repositorio.deleteById(id);
    }
}
