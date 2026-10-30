package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.ListarUsuariosUseCase;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ListarUsuariosService implements ListarUsuariosUseCase {

    private final UsuarioRepositoryPort repositorio;

    public ListarUsuariosService(UsuarioRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> executar() {
        return repositorio.findAll();
    }
}
