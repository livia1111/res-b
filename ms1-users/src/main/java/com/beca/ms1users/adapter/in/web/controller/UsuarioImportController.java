package com.beca.ms1users.adapter.in.web.controller;

import com.beca.ms1users.adapter.in.web.dto.*;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.ImportarUsuariosCsvUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Importação CSV", description = "Importar usuários em massa via arquivo CSV")
public class UsuarioImportController {

    private final ImportarUsuariosCsvUseCase importarCsvUseCase;

    public UsuarioImportController(ImportarUsuariosCsvUseCase importarCsvUseCase) {
        this.importarCsvUseCase = importarCsvUseCase;
    }

    @Operation(summary = "Importar usuários via CSV",
               description = "Envie um arquivo .csv com colunas: nome,email,senha")
    @PostMapping(value = "/importar-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportUsersResponse> importarCsv(
            @RequestParam("arquivo") MultipartFile arquivo) throws IOException {

        List<Usuario> importados = importarCsvUseCase.executar(arquivo.getInputStream());

        List<UserResponse> lista = importados.stream()
                .map(UserResponse::fromDomain)
                .collect(Collectors.toList());

        ImportUsersResponse response = ImportUsersResponse.builder()
                .totalImportados(lista.size())
                .usuarios(lista)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
