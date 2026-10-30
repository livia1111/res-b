package com.beca.ms1users.port.in;

import com.beca.ms1users.domain.model.Usuario;

import java.util.List;

public interface ListarUsuariosUseCase {

    List<Usuario> executar();
}
