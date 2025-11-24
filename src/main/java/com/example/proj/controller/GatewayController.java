package com.example.proj.controller;

import com.example.proj.model.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gateway")
@Tag(name = "API Gateway", description = "Gateway que integra APIs REST e SOAP")
public class GatewayController {
    
    @Autowired
    private LivroController livroController;
    
    @Autowired
    private UsuarioSoapController usuarioSoapController;

    //injeção de dependência via construtor
    // public GatewayController() {
    //     this.livroController = new LivroController();
    //     this.usuarioSoapController = new UsuarioSoapController();
    // }

    //hashmap funciona como dicionário
    @GetMapping
    @Operation(summary = "Informações do Gateway", description = "Retorna informações sobre as APIs disponíveis via Gateway")
    public Map<String, Object> getGatewayInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Library API Gateway");
        info.put("version", "1.0.0");
        info.put("description", "Gateway integrating REST and SOAP APIs");
        info.put("apis", Map.of(
            "rest", "Livros API - Gerenciamento de livros via REST",
            "soap", "Usuarios API - Gerenciamento de usuários via SOAP"
        ));
        info.put("_links", Map.of(
            "self", "/gateway",
            "livros", "/gateway/livros",
            "usuarios", "/gateway/usuarios",
            "swagger", "/swagger-ui.html",
            "wsdl", "/ws/usuarios.wsdl"
        ));
        
        return info;
    }

    //hashmap funciona como dicionário
    @GetMapping("/livros")
    @Operation(summary = "Livros via Gateway", description = "Acessa API REST de livros através do Gateway")
    public Map<String, Object> getAllLivrosViaGateway() {
        var livros = livroController.getAllLivros();
        
        Map<String, Object> response = new HashMap<>();
        response.put("source", "REST API");
        response.put("data", livros);
        response.put("_links", Map.of(
            "gateway", "/gateway",
            "self", "/gateway/livros",
            "direct", "/api/livros"
        ));
        
        return response;
    }
    
    //hashmap funciona como dicionário
    @GetMapping("/livros/{id}")
    @Operation(summary = "Livro por ID via Gateway", description = "Acessa livro específico via Gateway")
    public ResponseEntity<Map<String, Object>> getLivroByIdViaGateway(@PathVariable Long id) {
        var livro = livroController.getLivroById(id);
        
        if (livro.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> response = new HashMap<>();
            response.put("source", "REST API");
            response.put("data", livro.getBody());
            response.put("_links", Map.of(
                "gateway", "/gateway",
                "self", "/gateway/livros/" + id,
                "all", "/gateway/livros",
                "direct", "/api/livros/" + id
            ));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/livros")
    @Operation(summary = "Criar livro via Gateway", description = "Cria novo livro através do Gateway")
    public ResponseEntity<Map<String, Object>> createLivroViaGateway(@RequestBody Livro livro) {
        var response = livroController.createLivro(livro);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> result = new HashMap<>();
            result.put("source", "REST API");
            result.put("data", response.getBody());
            result.put("_links", Map.of(
                "gateway", "/gateway",
                "self", "/gateway/livros/" + livro.getId(),
                "all", "/gateway/livros"
            ));
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/usuarios")
    @Operation(summary = "Usuários via Gateway", description = "Acessa dados de usuários via SOAP através do Gateway")
    public Map<String, Object> getAllUsuariosViaGateway() {
        // Simulando uma chamada SOAP, numa implementação real seria via cliente SOAP

        // 1. vc cria o "envelope" da carta (request)
        var request = new UsuarioSoapController.GetAllUsuariosRequest();
        // 2. vc entrega a carta  (SOAP controller)
        var response = usuarioSoapController.getAllUsuarios(request);
        
        Map<String, Object> result = new HashMap<>();
        result.put("source", "SOAP API");
        result.put("usuarios", response.getUsuarios());
        result.put("count", response.getUsuarios().size());
        result.put("_links", Map.of(
            "gateway", "/gateway",
            "self", "/gateway/usuarios",
            "wsdl", "/ws/usuarios.wsdl",
            "soap", "/ws"
        ));
        
        return result;
    }
    
    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Usuário por ID via Gateway", description = "Acessa usuário específico via SOAP através do Gateway")
    public ResponseEntity<Map<String, Object>> getUsuarioByIdViaGateway(@PathVariable Long id) {
        var request = new UsuarioSoapController.GetUsuarioRequest();
        request.setId(id);
        var response = usuarioSoapController.getUsuario(request);
        
        Map<String, Object> result = new HashMap<>();
        if (response.getUsuario() != null) {
            result.put("source", "SOAP API");
            result.put("usuario", response.getUsuario());
            result.put("_links", Map.of(
                "gateway", "/gateway",
                "self", "/gateway/usuarios/" + id,
                "all", "/gateway/usuarios",
                "wsdl", "/ws/usuarios.wsdl"
            ));
            
            return ResponseEntity.ok(result);
        } else {
            result.put("error", "Usuário não encontrado");
            result.put("source", "SOAP API");
            return ResponseEntity.notFound().build();
        }
    }
}
