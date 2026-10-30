package com.beca.ms1users.port.in;

import com.beca.ms1users.domain.model.Usuario;

import java.io.InputStream;
import java.util.List;

public interface ImportarUsuariosCsvUseCase {

    List<Usuario> executar(InputStream csvInputStream);
}
