package com.beca.ms1users.adapter.in.web.controller;

import com.beca.ms1users.adapter.in.web.dto.*;
import com.beca.ms1users.domain.model.Usuario;
import com.beca.ms1users.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final CriarUsuarioUseCase criarUseCase;
    private final ListarUsuariosUseCase listarUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUseCase;
    private final AtualizarUsuarioUseCase atualizarUseCase;
    private final ExcluirUsuarioUseCase excluirUseCase;

    public UsuarioController(CriarUsuarioUseCase criarUseCase,
                             ListarUsuariosUseCase listarUseCase,
                             BuscarUsuarioPorIdUseCase buscarUseCase,
                             AtualizarUsuarioUseCase atualizarUseCase,
                             ExcluirUsuarioUseCase excluirUseCase) {
        this.criarUseCase = criarUseCase;
        this.listarUseCase = listarUseCase;
        this.buscarUseCase = buscarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.excluirUseCase = excluirUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> criar(@Valid @RequestBody CreateUserRequest req) {
        Usuario u = criarUseCase.executar(req.getNome(), req.getEmail(), req.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromDomain(u));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listarTodos() {
        List<UserResponse> lista = listarUseCase.executar().stream()
                .map(UserResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarPorId(@PathVariable Long id) {
        Usuario u = buscarUseCase.executar(id);
        return ResponseEntity.ok(UserResponse.fromDomain(u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateUserRequest req) {
        Usuario u = atualizarUseCase.executar(id, req.getNome(), req.getEmail(), req.getSenha());
        return ResponseEntity.ok(UserResponse.fromDomain(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        excluirUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/massa")
    public ResponseEntity<List<UserResponse>> criarEmMassa(
            @Valid @RequestBody List<CreateUserRequest> requests) {
        List<UserResponse> respostas = requests.stream()
                .map(req -> criarUseCase.executar(req.getNome(), req.getEmail(), req.getSenha()))
                .map(UserResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(respostas);
    }
}
