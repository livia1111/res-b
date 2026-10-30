package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdServiceTest {

    @Mock private UsuarioRepositoryPort repositorio;
    @InjectMocks private BuscarUsuarioPorIdService service;

    @Test
    @DisplayName("Deve retornar usuário quando encontrado")
    void deveRetornar() {
        Usuario u = new Usuario(1L, "João", "joao@email.com", "$2a$hash");
        when(repositorio.findById(1L)).thenReturn(Optional.of(u));

        Usuario resultado = service.executar(1L);

        assertThat(resultado.getNome()).isEqualTo("João");
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrado")
    void deveLancar() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.executar(99L))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("99");
    }
}
