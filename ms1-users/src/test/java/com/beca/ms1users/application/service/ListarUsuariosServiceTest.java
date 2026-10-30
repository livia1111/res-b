package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarUsuariosServiceTest {

    @Mock private UsuarioRepositoryPort repositorio;
    @InjectMocks private ListarUsuariosService service;

    @Test
    @DisplayName("Deve retornar lista de usuários")
    void deveListar() {
        List<Usuario> lista = List.of(
            new Usuario(1L, "João", "joao@email.com", "$2a$h1"),
            new Usuario(2L, "Maria", "maria@email.com", "$2a$h2")
        );
        when(repositorio.findAll()).thenReturn(lista);

        List<Usuario> resultado = service.executar();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("João");
        assertThat(resultado.get(1).getNome()).isEqualTo("Maria");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarVazio() {
        when(repositorio.findAll()).thenReturn(List.of());

        List<Usuario> resultado = service.executar();

        assertThat(resultado).isEmpty();
    }
}
