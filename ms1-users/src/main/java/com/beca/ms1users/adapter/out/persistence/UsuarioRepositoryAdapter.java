package com.beca.ms1users.adapter.out.persistence;

import com.beca.ms1users.adapter.out.persistence.entity.UsuarioEntity;
import com.beca.ms1users.adapter.out.persistence.repository.UsuarioJpaRepository;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpa;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioEntity.fromDomain(usuario);
        return jpa.save(entity).toDomain();
    }

    @Override
    public List<Usuario> saveAll(List<Usuario> usuarios) {
        List<UsuarioEntity> entities = usuarios.stream()
                .map(UsuarioEntity::fromDomain)
                .collect(Collectors.toList());
        return jpa.saveAll(entities).stream()
                .map(UsuarioEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioEntity::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpa.findAll().stream()
                .map(UsuarioEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpa.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }
}
