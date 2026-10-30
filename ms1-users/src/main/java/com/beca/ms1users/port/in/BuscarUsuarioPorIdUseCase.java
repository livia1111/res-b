package com.beca.ms1users.port.in;

import com.beca.ms1users.domain.model.Usuario;

public interface BuscarUsuarioPorIdUseCase {

    Usuario executar(Long id);
}
