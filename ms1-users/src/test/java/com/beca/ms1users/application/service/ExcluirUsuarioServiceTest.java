package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.exception.UsuarioNaoEncontradoException;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcluirUsuarioServiceTest {

    @Mock private UsuarioRepositoryPort repositorio;
    @InjectMocks private ExcluirUsuarioService service;

    @Test
    @DisplayName("Deve excluir quando existe")
    void deveExcluir() {
        when(repositorio.existsById(1L)).thenReturn(true);

        service.executar(1L);

        verify(repositorio).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não existe")
    void deveLancar() {
        when(repositorio.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.executar(99L))
                .isInstanceOf(UsuarioNaoEncontradoException.class);

        verify(repositorio, never()).deleteById(any());
    }
}
