package com.beca.ms1users.port.in;

import com.beca.ms1users.domain.model.Usuario;

public interface AtualizarUsuarioUseCase {

    Usuario executar(Long id, String nome, String email, String senhaRaw);
}
