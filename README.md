# 🚀 Spring Boot REST + SOAP Gateway

Sistema Gateway completo que integra APIs REST e SOAP em uma única aplicação Spring Boot, desenvolvido para demonstrar integração de sistemas e padrões arquiteturais modernos.

## 📋 Funcionalidades Implementadas

- ✅ **Gateway Unificado** - Controller centralizando acesso às APIs
- ✅ **REST API com HATEOAS** - Navegação automática via links
- ✅ **SOAP Web Service + WSDL** - Endpoint com contrato XML
- ✅ **Documentação Swagger** - Interface automática para testes
- ✅ **Cliente Web HTML** - Interface para demonstração
- ✅ **Cliente Python SOAP** - Integração cross-platform
- ✅ **Apresentação Completa** - Documentação e scripts inclusos

## 🏗️ Arquitetura

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

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- Python 3.x (para cliente SOAP)

### Execução
```bash
# 1. Clonar o repositório
git clone https://github.com/Alencast/spring-boot-rest-soap-gateway.git
cd spring-boot-rest-soap-gateway

# 2. Executar aplicação
mvn spring-boot:run

# 3. Acessar interfaces
http://localhost:8080                    # Cliente web
http://localhost:8080/swagger-ui.html    # Documentação Swagger
http://localhost:8080/ws/usuarios.wsdl   # WSDL SOAP

# 4. Executar cliente Python (opcional)
pip install requests
python client_soap_python.py
```

## 📖 Endpoints Disponíveis

### REST API - Livros
- `GET /api/livros` - Listar livros
- `GET /api/livros/{id}` - Buscar livro por ID
- `POST /api/livros` - Criar novo livro

### SOAP API - Usuários
- `POST /ws` - Operações SOAP (getAllUsuarios, getUsuario, createUsuario)

### Gateway Unificado
- `GET /gateway/livros` - Livros via Gateway
- `GET /gateway/usuarios` - Usuários via Gateway

## 🛠️ Tecnologias Utilizadas

- **Spring Boot 3.2.0** - Framework principal
- **Spring Web Services** - SOAP/WSDL
- **SpringDoc OpenAPI** - Swagger automático
- **Jakarta XML Bind (JAXB)** - XML/Object binding
- **Maven** - Gerenciamento de dependências
- **HTML5/CSS3/JavaScript** - Cliente web
- **Python + Requests** - Cliente cross-platform

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/proj/
│   │   ├── LibraryApplication.java          # Aplicação principal
│   │   ├── config/
│   │   │   └── WebServiceConfig.java        # Configuração SOAP
│   │   ├── controller/
│   │   │   ├── GatewayController.java       # Gateway unificado
│   │   │   ├── LivroController.java         # REST API
│   │   │   └── UsuarioSoapController.java   # SOAP API
│   │   └── model/
│   │       ├── Livro.java                   # Modelo Livro
│   │       └── Usuario.java                 # Modelo Usuário
│   └── resources/
│       ├── static/
│       │   ├── index.html                   # Cliente web
│       │   └── APRESENTACAO.md              # Documentação completa
│       ├── usuarios.xsd                     # Schema XML SOAP
│       └── application.properties           # Configurações
├── client_soap_python.py                    # Cliente Python
└── APRESENTACAO.md                           # Apresentação do projeto
```

## 🎯 Conceitos Demonstrados

### Padrões Arquiteturais
- **Gateway Pattern** - Centralizando acesso às APIs
- **Facade Pattern** - Interface unificada
- **MVC Pattern** - Separação de responsabilidades

### Tecnologias Web
- **REST** - Arquitetura stateless
- **SOAP** - Protocol baseado em XML
- **HATEOAS** - Hipermídia para navegação
- **OpenAPI** - Documentação automática

### Integração de Sistemas
- **Cross-Platform** - Java ↔ Python
- **Multi-Protocol** - REST + SOAP
- **Auto-Documentation** - WSDL + Swagger

## 📊 Resultados

- ✅ **7/7 Requisitos** implementados
- ✅ **3 Protocolos** funcionando (REST, SOAP, HTTP)
- ✅ **4 Tecnologias** integradas (Java, HTML, JS, Python)
- ✅ **2 Paradigmas** demonstrados (OOP + Web Services)
- ✅ **1 Gateway** centralizando tudo

## 📝 Documentação

A documentação completa do projeto, incluindo scripts de apresentação e explicações detalhadas, está disponível em:
- [`APRESENTACAO.md`](./APRESENTACAO.md) - Documentação completa
- [Swagger UI](http://localhost:8080/swagger-ui.html) - Documentação interativa das APIs
- [WSDL](http://localhost:8080/ws/usuarios.wsdl) - Contrato do Web Service

## 🎓 Uso Acadêmico

Este projeto foi desenvolvido para demonstrar:
- Integração de sistemas heterogêneos
- Padrões arquiteturais modernos
- Implementação de Web Services
- Documentação automática de APIs
- Comunicação cross-platform

---

# Spring Boot REST/SOAP Gateway

## Pré-requisitos
- Java 17+
- Maven 3.6+

## Como executar
1. Clone o projeto
2. Execute: `mvn clean install`
3. Execute: `mvn spring-boot:run`
4. Acesse: http://localhost:8080

## APIs disponíveis
- REST API: http://localhost:8080/api/livros
- SOAP API: http://localhost:8080/ws
- Gateway: http://localhost:8080/gateway
- Swagger: http://localhost:8080/swagger-ui.html
- WSDL: http://localhost:8080/ws/usuarios.wsdl

*Desenvolvido como projeto acadêmico para demonstrar integração REST + SOAP com Spring Boot* 
