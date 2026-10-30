package com.beca.ms1users.adapter.out.persistence.entity;

import com.beca.ms1users.domain.model.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String email;
        private String senha;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public UsuarioEntity build() {
            return new UsuarioEntity(id, nome, email, senha);
        }
    }

    // --- Mappers ---

    public static UsuarioEntity fromDomain(Usuario u) {
        return UsuarioEntity.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .senha(u.getSenha())
                .build();
    }

    public Usuario toDomain() {
        return new Usuario(id, nome, email, senha);
    }
}
