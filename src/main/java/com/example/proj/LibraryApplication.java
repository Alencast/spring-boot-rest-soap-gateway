package com.example.proj;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.InetAddress;

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
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Development"),
        @Server(url = "http://0.0.0.0:8080", description = "Network Access")
    }
)
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
        
        try {
            String localIP = InetAddress.getLocalHost().getHostAddress();
            System.out.println("\n=================================================");
            System.out.println("🚀 Library API Gateway iniciado com sucesso!");
            System.out.println("=================================================");
            System.out.println("📱 ACESSO LOCAL:");
            System.out.println("📋 Swagger UI: http://localhost:8080/swagger-ui.html");
            System.out.println("🌐 Cliente Web: http://localhost:8080/");
            System.out.println("🚺 Gateway: http://localhost:8080/gateway");
            System.out.println("📚 REST API: http://localhost:8080/api/livros");
            System.out.println("🌐 SOAP WSDL: http://localhost:8080/ws/usuarios.wsdl");
            System.out.println("=================================================");
            System.out.println("🌍 ACESSO NA REDE:");
            System.out.println("📋 Swagger UI: http://" + localIP + ":8080/swagger-ui.html");
            System.out.println("🌐 Cliente Web: http://" + localIP + ":8080/");
            System.out.println("🚺 Gateway: http://" + localIP + ":8080/gateway");
            System.out.println("📚 REST API: http://" + localIP + ":8080/api/livros");
            System.out.println("🌐 SOAP WSDL: http://" + localIP + ":8080/ws/usuarios.wsdl");
            System.out.println("=================================================\n");
        } catch (Exception e) {
            System.out.println("Erro ao obter IP local: " + e.getMessage());
        }
    }

}
