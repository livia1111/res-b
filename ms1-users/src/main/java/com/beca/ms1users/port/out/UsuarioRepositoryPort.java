package com.beca.ms1users.port.out;

import com.beca.ms1users.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída — contrato do repositório de usuários.
 * A implementação fica no adapter de persistência.
 */
public interface UsuarioRepositoryPort {

    Usuario save(Usuario usuario);

    List<Usuario> saveAll(List<Usuario> usuarios);

    Optional<Usuario> findById(Long id);

    List<Usuario> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);
}
