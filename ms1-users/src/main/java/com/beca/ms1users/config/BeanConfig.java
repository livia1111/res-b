package com.beca.ms1users.config;

import com.beca.ms1users.application.service.*;
import com.beca.ms1users.port.in.*;
import com.beca.ms1users.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registra os use cases como beans do Spring.
 * Os services da camada application NÃO usam @Service —
 * são instanciados aqui para manter o domínio desacoplado do framework.
 */
@Configuration
public class BeanConfig {

    @Bean
    public CriarUsuarioUseCase criarUsuarioUseCase(
            UsuarioRepositoryPort repo, PasswordHasherPort hasher) {
        return new CriarUsuarioService(repo, hasher);
    }

    @Bean
    public AtualizarUsuarioUseCase atualizarUsuarioUseCase(
            UsuarioRepositoryPort repo, PasswordHasherPort hasher) {
        return new AtualizarUsuarioService(repo, hasher);
    }

    @Bean
    public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(
            UsuarioRepositoryPort repo) {
        return new BuscarUsuarioPorIdService(repo);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(
            UsuarioRepositoryPort repo) {
        return new ListarUsuariosService(repo);
    }

    @Bean
    public ExcluirUsuarioUseCase excluirUsuarioUseCase(
            UsuarioRepositoryPort repo) {
        return new ExcluirUsuarioService(repo);
    }

    @Bean
    public ImportarUsuariosCsvUseCase importarUsuariosCsvUseCase(
            UsuarioRepositoryPort repo, PasswordHasherPort hasher) {
        return new ImportarUsuariosCsvService(repo, hasher);
    }
}
