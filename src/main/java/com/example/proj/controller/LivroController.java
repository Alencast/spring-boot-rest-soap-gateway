package com.example.proj.controller;

import com.example.proj.model.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livros", description = "API REST para gerenciamento de livros")
public class LivroController {
    
    private List<Livro> livros = new ArrayList<>();
    private Long nextId = 1L; //próximo ID disponível, L de Long 
    
    public LivroController() {
        // Dados iniciais
        livros.add(new Livro(1L, "Spring Boot in Action", "Craig Walls", "978-1617292545"));
        livros.add(new Livro(2L, "Clean Code", "Robert Martin", "978-0132350884"));
        nextId = 3L; //O próximo id disponível é o 3,  pq já tem 2 livros
    }
    
    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna lista de todos os livros com links HATEOAS")
    //hashmap funciona como dicionário, <string, object> para os valores variados do dicionário
    public Map<String, Object> getAllLivros() {
        Map<String, Object> response = new HashMap<>();
        response.put("livros", livros);
        response.put("count", livros.size());
        response.put("_links", Map.of(
            "self", "/api/livros",
            "gateway", "/gateway/livros"
        ));
        return response;
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna um livro específico com links HATEOAS")
    public ResponseEntity<Map<String, Object>> getLivroById(@PathVariable Long id) {
        return livros.stream()
                .filter(livro -> livro.getId().equals(id))
                .findFirst()
                .map(livro -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("livro", livro);
                    response.put("_links", Map.of(
                        "self", "/api/livros/" + id,
                        "all", "/api/livros",
                        "gateway", "/gateway/livros/" + id
                    ));
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Criar novo livro", description = "Cria um novo livro e retorna com links HATEOAS")
                                                           //parametro vai vir no corpo da requisição http, não na url
    public ResponseEntity<Map<String, Object>> createLivro(@RequestBody Livro livro) {
        livro.setId(nextId++);
        livros.add(livro);
        
        Map<String, Object> response = new HashMap<>();
        response.put("livro", livro);
        response.put("message", "Livro criado com sucesso");
        response.put("_links", Map.of(
            "self", "/api/livros/" + livro.getId(),
            "all", "/api/livros",
            "gateway", "/gateway/livros/" + livro.getId()
        ));
        
        return ResponseEntity.ok(response);
    }
}
