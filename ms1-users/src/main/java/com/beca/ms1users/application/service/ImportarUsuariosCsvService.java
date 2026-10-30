package com.beca.ms1users.application.service;

import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.ImportarUsuariosCsvUseCase;
import com.beca.ms1users.port.out.PasswordHasherPort;
import com.beca.ms1users.port.out.UsuarioRepositoryPort;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class ImportarUsuariosCsvService implements ImportarUsuariosCsvUseCase {

    private final UsuarioRepositoryPort repositorio;
    private final PasswordHasherPort hasher;

    public ImportarUsuariosCsvService(UsuarioRepositoryPort repositorio,
                                     PasswordHasherPort hasher) {
        this.repositorio = repositorio;
        this.hasher = hasher;
    }

    /**
     * Formato esperado do CSV:
     * nome,email,senha
     * João Silva,joao@email.com,senha123
     */
    @Override
    @Transactional
    public List<Usuario> executar(InputStream csvInputStream) {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(csvInputStream, StandardCharsets.UTF_8))) {

            List<String[]> linhas = reader.readAll();

            // Pula cabeçalho (linha 0)
            List<Usuario> usuarios = linhas.stream()
                    .skip(1)
                    .filter(cols -> cols.length >= 3)
                    .map(cols -> Usuario.criar(
                            cols[0].trim(),
                            cols[1].trim(),
                            hasher.hash(cols[2].trim())))
                    .collect(Collectors.toList());

            return repositorio.saveAll(usuarios);

        } catch (IOException | CsvException e) {
            throw new RuntimeException("Erro ao processar arquivo CSV: " + e.getMessage(), e);
        }
    }
}
