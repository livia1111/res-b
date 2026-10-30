package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.BuscarUsuarioPorIdUseCase;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

public class BuscarUsuarioPorIdService implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort repositorio;

    public BuscarUsuarioPorIdService(UsuarioRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario executar(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
