package com.example.proj;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Library API Gateway",
        version = "1.0.0",
        description = "API Gateway integrating REST and SOAP services for library management",
        contact = @Contact(
            name = "Library Team",
            email = "library@example.com"
        )
    ),
    servers = @Server(
        url = "http://localhost:8080",
        description = "Development Server"
    )
)
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
        System.out.println("\n=================================================");
        System.out.println("🚀 Library API Gateway iniciado com sucesso!");
        System.out.println("=================================================");
        System.out.println("📋 Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("🌐 Cliente Web: http://localhost:8080/");
        System.out.println("🚪 Gateway: http://localhost:8080/gateway");
        System.out.println("📚 REST API: http://localhost:8080/api/livros");
        System.out.println("🌐 SOAP WSDL: http://localhost:8080/ws/usuarios.wsdl");
        System.out.println("=================================================\n");
    }

}
