package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.EmailJaCadastradoException;
import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioServiceTest {

    @Mock private UsuarioRepositoryPort repositorio;
    @Mock private PasswordHasherPort hasher;
    @InjectMocks private AtualizarUsuarioService service;

    private Usuario existente;

    @BeforeEach
    void setUp() {
        existente = new Usuario(1L, "João", "joao@email.com", "$2a$old");
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizar() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(hasher.hash("novaSenha")).thenReturn("$2a$nova");
        when(repositorio.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario resultado = service.executar(1L, "João Atualizado", "joao@email.com", "novaSenha");

        assertThat(resultado.getNome()).isEqualTo("João Atualizado");
        assertThat(resultado.getSenha()).isEqualTo("$2a$nova");
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID não existe")
    void deveLancarNotFound() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.executar(99L, "X", "x@x.com", "123456"))
                .isInstanceOf(UsuarioNaoEncontradoException.class);
    }

    @Test
    @DisplayName("Deve lançar exceção quando novo e-mail já pertence a outro usuário")
    void deveLancarEmailDuplicado() {
        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        when(repositorio.existsByEmail("outro@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.executar(1L, "João", "outro@email.com", "senha123"))
                .isInstanceOf(EmailJaCadastradoException.class);

        verify(repositorio, never()).save(any());
    }
}
