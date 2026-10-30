package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.EmailJaCadastradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarUsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort repositorio;
    @Mock
    private PasswordHasherPort hasher;
    @InjectMocks
    private CriarUsuarioService service;

    @Test
    @DisplayName("Deve criar usuário com senha hasheada")
    void deveCriarComSucesso() {
        when(repositorio.existsByEmail("joao@email.com")).thenReturn(false);
        when(hasher.hash("senha123")).thenReturn("$2a$hashed");
        when(repositorio.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });

        Usuario resultado = service.executar("João", "joao@email.com", "senha123");

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("João");
        assertThat(resultado.getSenha()).isEqualTo("$2a$hashed");
        verify(hasher).hash("senha123");
        verify(repositorio).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando e-mail já existe")
    void deveLancarExcecaoEmailDuplicado() {
        when(repositorio.existsByEmail("joao@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.executar("João", "joao@email.com", "senha123"))
                .isInstanceOf(EmailJaCadastradoException.class)
                .hasMessageContaining("joao@email.com");

        verify(repositorio, never()).save(any());
    }
}
