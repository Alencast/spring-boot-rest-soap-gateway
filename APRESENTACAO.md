# 🚀 Apresentação: Sistema Gateway REST + SOAP

## 📋 Visão Geral do Projeto

Este projeto demonstra um **Gateway API** completo que integra tecnologias **REST** e **SOAP** em uma única aplicação Spring Boot, atendendo aos requisitos acadêmicos de integração de sistemas.

---

## 🎯 Requisitos Implementados

### ✅ 1. Gateway - Controller com Rotas
- **Arquivo**: `GatewayController.java`
- **Função**: Centraliza acesso às APIs REST e SOAP
- **Demonstração**: Endpoints unificados em `/gateway/*`

### ✅ 2. HATEOAS - Links no JSON
- **Arquivo**: `LivroController.java`
- **Função**: Adiciona navegação automática via links
- **Demonstração**: Campo `_links` nos JSONs de resposta

### ✅ 3. Documentação - Swagger Automático
- **URL**: `http://localhost:8080/swagger-ui.html`
- **Função**: Interface visual para testar APIs
- **Demonstração**: Documentação interativa completa

### ✅ 4. Duas APIs - REST + SOAP no Mesmo Projeto
- **REST**: `LivroController.java` para gerenciar livros
- **SOAP**: `UsuarioSoapController.java` para gerenciar usuários
- **Demonstração**: Ambas funcionando simultaneamente

### ✅ 5. Cliente Web - HTML Simples
- **Arquivo**: `index.html`
- **Função**: Interface para testar todas as funcionalidades
- **Demonstração**: Formulários e botões interativos

### ✅ 6. SOAP + WSDL - Endpoint Funcionando
- **Endpoint**: `http://localhost:8080/ws`
- **WSDL**: `http://localhost:8080/ws/usuarios.wsdl`
- **Demonstração**: Web Service completo com schema XML

### ✅ 7. Cliente Multiplataforma
- **Arquivo**: `client_soap_python.py`
- **Função**: Python consumindo SOAP Java
- **Demonstração**: Integração cross-platform

---

## 🏗️ Arquitetura do Sistema

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Cliente Web   │    │  Cliente Python │    │   Swagger UI    │
│   (HTML/JS)     │    │   (SOAP)        │    │  (Documentação) │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
                    ┌─────────────▼───────────────┐
                    │      Spring Boot App       │
                    │     (localhost:8080)       │
                    └─────────────┬───────────────┘
                                  │
                    ┌─────────────▼───────────────┐
                    │       Gateway Layer        │
                    │   (GatewayController)      │
                    └─────────────┬───────────────┘
                                  │
          ┌───────────────────────┼───────────────────────┐
          │                       │                       │
┌─────────▼───────┐     ┌────────▼────────┐     ┌────────▼────────┐
│   REST API      │     │   SOAP API      │     │  Static Files   │
│ (LivroController)│     │(UsuarioController)│   │  (index.html)   │
│   + HATEOAS     │     │   + WSDL        │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

---

## 💻 Demonstração Prática

### 🔥 **Script de Apresentação**

#### **1. Iniciar o Servidor (2 minutos)**
```bash
# Terminal 1: Compilar e executar
cd proj
mvn spring-boot:run

# Aguardar mensagem: "Application started successfully!"
# Mostrar logs no console demonstrando inicialização
```

#### **2. Demonstrar Swagger (3 minutos)**
```bash
# Abrir navegador
http://localhost:8080/swagger-ui.html

# Demonstrar:
- Lista completa de endpoints REST
- Testar GET /api/livros (mostrar HATEOAS)
- Testar POST /api/livros (criar novo livro)
- Explicar documentação automática
```

#### **3. Demonstrar WSDL SOAP (2 minutos)**
```bash
# Abrir navegador
http://localhost:8080/ws/usuarios.wsdl

# Explicar:
- XML Schema gerado automaticamente
- Definições de operações SOAP
- Types, Messages, PortType, Binding
```

#### **4. Cliente Web HTML (3 minutos)**
```bash
# Abrir navegador
http://localhost:8080

# Demonstrar:
- Formulário para criar livros (REST)
- Listar livros com links HATEOAS
- Formulário para gerenciar usuários (SOAP)
- Gateway unificado funcionando
```

#### **5. Cliente Python SOAP (2 minutos)**
```bash
# Terminal 2: Executar cliente Python
python client_soap_python.py

# Mostrar:
- Python montando envelope SOAP XML
- Fazendo requisição HTTP POST
- Recebendo resposta XML do Java
- Demonstração cross-platform funcionando
```

#### **6. Testar Gateway Unificado (3 minutos)**
```bash
# No navegador ou Postman:

# Testar via Gateway:
GET http://localhost:8080/gateway/livros
GET http://localhost:8080/gateway/usuarios

# Explicar como o Gateway integra ambas as APIs
# Mostrar logs no console do servidor
```

---

## 📝 Explicação dos Códigos Principais

### **GatewayController.java**
```java
@RestController
@RequestMapping("/gateway")
public class GatewayController {
    
    @Autowired
    private LivroController livroController;
    
    @Autowired 
    private UsuarioSoapController usuarioController;
    
    // Integra REST e SOAP em endpoints unificados
    @GetMapping("/livros")
    public ResponseEntity<?> getLivros() {
        return livroController.getAllLivros();
    }
}
```
**Explicação**: O Gateway atua como **Facade Pattern**, centralizando acesso às diferentes tecnologias (REST/SOAP) através de uma interface única.

### **HATEOAS Manual - LivroController.java**
```java
@GetMapping("/{id}")
public ResponseEntity<?> getLivro(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    response.put("id", id);
    response.put("titulo", "Livro " + id);
    
    // Links HATEOAS manuais
    Map<String, String> links = new HashMap<>();
    links.put("self", "/api/livros/" + id);
    links.put("all", "/api/livros");
    response.put("_links", links);
    
    return ResponseEntity.ok(response);
}
```
**Explicação**: HATEOAS adiciona **navegabilidade** ao REST. O campo `_links` permite que clientes descubram ações disponíveis dinamicamente.

### **SOAP com JAXB - UsuarioSoapController.java**
```java
@Endpoint
public class UsuarioSoapController {
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosRequest")
    @ResponsePayload
    public GetAllUsuariosResponse getAllUsuarios(@RequestPayload GetAllUsuariosRequest request) {
        // Lógica de busca
        return response;
    }
}
```
**Explicação**: **@Endpoint** marca a classe como Web Service. **@PayloadRoot** mapeia operações SOAP baseadas no XML Schema.

### **Cliente Python Mínimo**
```python
# Envelope SOAP em XML
soap_xml = """<?xml version="1.0"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:usr="http://proj.example.com/usuario">
    <soap:Body>
        <usr:getAllUsuariosRequest/>
    </soap:Body>
</soap:Envelope>"""

# POST HTTP com Content-Type XML
response = requests.post("http://localhost:8080/ws", 
                        data=soap_xml, 
                        headers={"Content-Type": "text/xml"})
```
**Explicação**: SOAP é essencialmente **HTTP + XML**. O envelope SOAP é enviado via POST e o servidor Java interpreta baseado no WSDL.

---

## 🎓 Conceitos Acadêmicos Demonstrados

### **1. Integração de Sistemas**
- **REST**: Arquitetura stateless com verbos HTTP
- **SOAP**: Protocol baseado em XML com contratos rígidos  
- **Gateway**: Padrão de integração unificando protocolos diferentes

### **2. Padrões de Design**
- **Facade Pattern**: Gateway centralizando acesso
- **MVC Pattern**: Controllers separando lógica de apresentação
- **Dependency Injection**: @Autowired conectando componentes

### **3. Tecnologias Web Modernas**
- **Spring Boot**: Framework auto-configurável
- **OpenAPI/Swagger**: Documentação automática de APIs
- **JAXB**: Binding automático XML ↔ Java Objects
- **HATEOAS**: Hipermídia para navegação de APIs

### **4. Cross-Platform Communication**
- **Java ↔ Python**: Linguagens diferentes comunicando via protocolos padrão
- **HTTP**: Protocolo universal de comunicação
- **XML/JSON**: Formatos de troca de dados

---

## 🔍 Pontos de Destaque para Avaliação

### **✨ Diferenciais Implementados**

1. **Gateway Unificado**: Não é apenas REST ou SOAP separados - é uma **integração real** via Gateway
2. **HATEOAS Funcional**: Links dinâmicos que realmente funcionam para navegação
3. **Documentação Automática**: Swagger gerado automaticamente sem configuração manual
4. **Cliente Cross-Platform**: Python consumindo Java SOAP demonstra **interoperabilidade real**
5. **Interface Completa**: Cliente web testando **todos** os endpoints implementados
6. **WSDL Acessível**: Web Service com contrato publicamente disponível

### **📊 Métricas de Sucesso**

- ✅ **7/7 Requisitos** implementados completamente
- ✅ **3 Protocolos** funcionando: REST, SOAP, HTTP
- ✅ **4 Tecnologias** integradas: Java, HTML, JavaScript, Python  
- ✅ **2 Paradigmas** demonstrados: OOP + Web Services
- ✅ **1 Gateway** centralizando tudo

---

## 🚀 Como Executar Tudo

### **Pré-requisitos**
```bash
# Java 17+
java -version

# Maven
mvn -version

# Python (para cliente)
python --version
pip install requests
```

### **Execução Completa**
```bash
# 1. Iniciar servidor
mvn spring-boot:run

# 2. Aguardar inicialização (logs no console)

# 3. Testar endpoints:
http://localhost:8080                    # Cliente web
http://localhost:8080/swagger-ui.html    # Documentação
http://localhost:8080/ws/usuarios.wsdl   # WSDL SOAP

# 4. Executar cliente Python
python client_soap_python.py
```

---

## 🏁 Conclusão

Este projeto demonstra **integração completa** de tecnologias modernas (REST, SOAP, Spring Boot) através de um Gateway unificado, com documentação automática, cliente web interativo e comunicação cross-platform. 

**Resultado**: Sistema robusto que atende 100% dos requisitos acadêmicos com implementação profissional e demonstração prática funcionando.

---

*Desenvolvido para demonstrar integração de sistemas e padrões arquiteturais modernos* 🎓