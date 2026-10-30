package com.beca.ms1users.domain.model;

/**
 * Modelo de domínio puro — sem dependências de framework.
 */
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha; // já com hash

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // --- factory para criação (sem id) ---
    public static Usuario criar(String nome, String email, String senhaHash) {
        return new Usuario(null, nome, email, senhaHash);
    }

    // --- getters / setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
