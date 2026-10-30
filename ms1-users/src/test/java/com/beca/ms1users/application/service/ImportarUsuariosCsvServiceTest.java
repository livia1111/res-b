package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportarUsuariosCsvServiceTest {

    @Mock private UsuarioRepositoryPort repositorio;
    @Mock private PasswordHasherPort hasher;
    @InjectMocks private ImportarUsuariosCsvService service;

    @Test
    @DisplayName("Deve importar usuários de CSV com hash nas senhas")
    void deveImportarCsv() {
        String csv = "nome,email,senha\n"
                + "Ana,ana@email.com,senha111\n"
                + "Bruno,bruno@email.com,senha222\n";

        InputStream is = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));

        when(hasher.hash(anyString())).thenAnswer(inv -> "$2a$" + inv.getArgument(0));
        when(repositorio.saveAll(anyList())).thenAnswer(inv -> {
            List<Usuario> list = inv.getArgument(0);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setId((long) (i + 1));
            }
            return list;
        });

        List<Usuario> resultado = service.executar(is);

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("Ana");
        assertThat(resultado.get(0).getSenha()).startsWith("$2a$");
        assertThat(resultado.get(1).getEmail()).isEqualTo("bruno@email.com");
        verify(hasher, times(2)).hash(anyString());
    }

    @Test
    @DisplayName("Deve ignorar linhas com menos de 3 colunas")
    void deveIgnorarLinhasInvalidas() {
        String csv = "nome,email,senha\n"
                + "Ana,ana@email.com\n"  // só 2 colunas — ignorar
                + "Bruno,bruno@email.com,senha222\n";

        InputStream is = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));

        when(hasher.hash(anyString())).thenReturn("$2a$hashed");
        when(repositorio.saveAll(anyList())).thenAnswer(inv -> {
            List<Usuario> list = inv.getArgument(0);
            list.get(0).setId(1L);
            return list;
        });

        List<Usuario> resultado = service.executar(is);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Bruno");
    }
}
