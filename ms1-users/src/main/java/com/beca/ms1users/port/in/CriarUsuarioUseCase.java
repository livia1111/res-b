package com.beca.ms1users.port.in;

import com.beca.ms1users.domain.model.Usuario;

public interface CriarUsuarioUseCase {

    Usuario executar(String nome, String email, String senhaRaw);
}
