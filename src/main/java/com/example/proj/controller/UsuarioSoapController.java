package com.example.proj.controller;

import com.example.proj.model.Usuario;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class UsuarioSoapController {
    
    //Namespace = "Endereço único" do seu serviço, como um "CPF" do Web Service
    //Evita conflitos de nomes entre diferentes serviços SOAP
    private static final String NAMESPACE_URI = "http://proj.example.com/usuario"; 
    
    private List<Usuario> usuarios = new ArrayList<>();
    private Long nextId = 1L; //próximo ID disponível, L de Long 
    
    public UsuarioSoapController() {
        // Dados iniciais
        usuarios.add(new Usuario(1L, "João Silva", "joao@email.com"));
        usuarios.add(new Usuario(2L, "Maria Santos", "maria@email.com"));
        nextId = 3L; //O próximo id disponível é o 3,  pq já tem 2 usuários
    }
    
    //Quando chegar um XML com tag <getUsuarioRequest> chama esse método
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioRequest")
    //Retorna um objeto java que será convertido para XML, já o request faz o contrário, pega um objeto XML e converte para java
    @ResponsePayload
    public GetUsuarioResponse getUsuario(@RequestPayload GetUsuarioRequest request) {
        GetUsuarioResponse response = new GetUsuarioResponse();
        
        //percorre a lista e retorna o usuário com o id correspondente
        usuarios.stream()
                .filter(usuario -> usuario.getId().equals(request.getId()))
                .findFirst()
                .ifPresent(usuario -> { 
                    // Se encontrou o usuário, converte pra XML e coloca na resposta
                    UsuarioXml usuarioXml = new UsuarioXml();
                    usuarioXml.setId(usuario.getId());
                    usuarioXml.setNome(usuario.getNome());
                    usuarioXml.setEmail(usuario.getEmail());
                    response.setUsuario(usuarioXml);
                });
        
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosRequest")
    @ResponsePayload
    //busca todos os usuários e retorna em formato XML, como um dto
    public GetAllUsuariosResponse getAllUsuarios(@RequestPayload GetAllUsuariosRequest request) {
        GetAllUsuariosResponse response = new GetAllUsuariosResponse();
        
        List<UsuarioXml> usuariosList = usuarios.stream()
                .map(usuario -> {
                    UsuarioXml usuarioXml = new UsuarioXml();
                    usuarioXml.setId(usuario.getId());
                    usuarioXml.setNome(usuario.getNome());
                    usuarioXml.setEmail(usuario.getEmail());
                    return usuarioXml;
                })
                .toList();
        
        response.getUsuarios().addAll(usuariosList);
        return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUsuarioRequest")
    @ResponsePayload
    //Cria um novo usuário a partir do XML recebido e retorna o usuário criado em XML, como um dto
    public CreateUsuarioResponse createUsuario(@RequestPayload CreateUsuarioRequest request) {
        CreateUsuarioResponse response = new CreateUsuarioResponse();
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setId(nextId++);
        novoUsuario.setNome(request.getNome());
        novoUsuario.setEmail(request.getEmail());
        usuarios.add(novoUsuario);
        
        UsuarioXml usuarioXml = new UsuarioXml();
        usuarioXml.setId(novoUsuario.getId());
        usuarioXml.setNome(novoUsuario.getNome());
        usuarioXml.setEmail(novoUsuario.getEmail());
        response.setUsuario(usuarioXml);
        
        return response;
    }
    
    //templates do XLM
    // Classes XML para SOAP, são classes que mapeiam exatamente a estrutura do XML que será trocado no SOAP. "DTOs do SOAP"
    @XmlRootElement(name = "getUsuarioRequest", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD) //Diz para o JAXB: "Use os campos diretamente para mapear XML"
    public static class GetUsuarioRequest {
        private Long id;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }
    
    @XmlRootElement(name = "getUsuarioResponse", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetUsuarioResponse {
        private UsuarioXml usuario;
        
        public UsuarioXml getUsuario() { return usuario; }
        public void setUsuario(UsuarioXml usuario) { this.usuario = usuario; }
    }
    
    @XmlRootElement(name = "getAllUsuariosRequest", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetAllUsuariosRequest {
        // Vazio pro exemplo
    }
    
    @XmlRootElement(name = "getAllUsuariosResponse", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetAllUsuariosResponse {
        @XmlElement(name = "usuario")
        private List<UsuarioXml> usuarios = new ArrayList<>();
        
        public List<UsuarioXml> getUsuarios() { return usuarios; }
        public void setUsuarios(List<UsuarioXml> usuarios) { this.usuarios = usuarios; }
    }
    
    @XmlRootElement(name = "createUsuarioRequest", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CreateUsuarioRequest {
        private String nome;
        private String email;
        
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    @XmlRootElement(name = "createUsuarioResponse", namespace = NAMESPACE_URI)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CreateUsuarioResponse {
        private UsuarioXml usuario;
        
        public UsuarioXml getUsuario() { return usuario; }
        public void setUsuario(UsuarioXml usuario) { this.usuario = usuario; }
    }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UsuarioXml {
        private Long id;
        private String nome;
        private String email;
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
