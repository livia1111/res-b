package com.beca.ms1users.adapter.in.web.dto;

import com.beca.ms1users.domain.model.Usuario;

public class UserResponse {

    private Long id;
    private String nome;
    private String email;
    // Senha NUNCA exposta

    public UserResponse() {
    }

    public UserResponse(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String email;

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

        public UserResponse build() {
            return new UserResponse(id, nome, email);
        }
    }

    public static UserResponse fromDomain(Usuario u) {
        return UserResponse.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .build();
    }
}
