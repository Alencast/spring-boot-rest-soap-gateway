# 🔗 Library API Gateway - Integração REST + SOAP

> **Sistema demonstrativo que integra APIs REST e SOAP através de um API Gateway, implementando HATEOAS, documentação automática e cliente web.**

## 📋 Objetivos Atendidos

✅ **Gateway** - Controller com rotas que integra REST e SOAP  
✅ **HATEOAS** - Links dinâmicos no JSON de todas as respostas  
✅ **Documentação** - Swagger automático acessível  
✅ **2 APIs** - REST (Livros) + SOAP (Usuários) no mesmo projeto  
✅ **Cliente Web** - Interface HTML para testar as APIs  
✅ **SOAP + WSDL** - Endpoint funcionando com WSDL acessível  
✅ **WSDL Demonstrado** - URL acessível e estrutura explicada  
✅ **Cliente Diferente** - Python consumindo SOAP Java via WSDL  

## 🚀 Como Executar

### 1. Clonar e Compilar
```bash
git clone <url-do-repositorio>
cd proj
./mvnw clean compile
```

### 2. Executar a Aplicação
```bash
./mvnw spring-boot:run
```

### 3. Acessar as Interfaces
- **Cliente Web**: http://localhost:8080/
- **Swagger**: http://localhost:8080/swagger-ui.html
- **WSDL**: http://localhost:8080/ws/usuarios.wsdl
- **Gateway Info**: http://localhost:8080/gateway

## 🏗️ Arquitetura do Sistema

```
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway                              │
│                 /gateway/*                                  │
├─────────────────────────────────────────────────────────────┤
│              │                        │                     │
│    REST API  │                   SOAP API                   │
│   (Livros)   │                 (Usuários)                   │
│  /api/livros │                    /ws/*                     │
│              │                        │                     │
└─────────────────────────────────────────────────────────────┘
              │                        │
        ┌─────────────┐        ┌─────────────┐
        │ HATEOAS     │        │ WSDL        │
        │ Links       │        │ Generated   │
        │ Automáticos │        │ Schema      │
        └─────────────┘        └─────────────┘
```

## 🔍 APIs Disponíveis

### 🚪 Gateway Endpoints
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/gateway` | Info do gateway com links HATEOAS |
| `GET` | `/gateway/livros` | Livros via gateway (REST) |
| `GET` | `/gateway/livros/{id}` | Livro específico via gateway |
| `POST` | `/gateway/livros` | Criar livro via gateway |
| `GET` | `/gateway/usuarios` | Usuários via gateway (SOAP) |
| `GET` | `/gateway/usuarios/{id}` | Usuário específico via gateway |

### 📚 REST API - Livros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/livros` | Lista todos os livros |
| `GET` | `/api/livros/{id}` | Busca livro por ID |
| `POST` | `/api/livros` | Cria novo livro |

### 🌐 SOAP API - Usuários
| Operação | Descrição |
|----------|-----------|
| `getAllUsuarios` | Lista todos os usuários |
| `getUsuario` | Busca usuário por ID |
| `createUsuario` | Cria novo usuário |

## 🔗 HATEOAS - Exemplo de Resposta

```json
{
  "_embedded": {
    "livroList": [
      {
        "id": 1,
        "titulo": "Spring Boot in Action",
        "autor": "Craig Walls",
        "isbn": "978-1617292545",
        "_links": {
          "self": {"href": "http://localhost:8080/api/livros/1"},
          "livros": {"href": "http://localhost:8080/api/livros"}
        }
      }
    ]
  },
  "_links": {
    "self": {"href": "http://localhost:8080/api/livros"}
  }
}
```

## 📄 WSDL - Principais Tags Demonstradas

Acesse http://localhost:8080/ws/usuarios.wsdl para ver o WSDL completo.

### Estrutura Principal:
```xml
<definitions xmlns="http://schemas.xmlsoap.org/wsdl/" 
             targetNamespace="http://proj.example.com/usuario">
  
  <!-- Definição dos tipos de dados -->
  <types>
    <xsd:schema targetNamespace="http://proj.example.com/usuario">
      <xsd:element name="getUsuarioRequest">
        <xsd:complexType>
          <xsd:sequence>
            <xsd:element name="id" type="xsd:long"/>
          </xsd:sequence>
        </xsd:complexType>
      </xsd:element>
      <!-- ... outros elementos ... -->
    </xsd:schema>
  </types>
  
  <!-- Definição das mensagens -->
  <message name="getUsuarioRequest">
    <part element="tns:getUsuarioRequest" name="getUsuarioRequest"/>
  </message>
  
  <!-- Interface do serviço -->
  <portType name="UsuariosPort">
    <operation name="getUsuario">
      <input message="tns:getUsuarioRequest" name="getUsuarioRequest"/>
      <output message="tns:getUsuarioResponse" name="getUsuarioResponse"/>
    </operation>
  </portType>
  
  <!-- Protocolo de comunicação -->
  <binding name="UsuariosPortSoap11" type="tns:UsuariosPort">
    <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
    <!-- ... operações ... -->
  </binding>
  
  <!-- Endpoint do serviço -->
  <service name="UsuariosPortService">
    <port binding="tns:UsuariosPortSoap11" name="UsuariosPortSoap11">
      <soap:address location="http://localhost:8080/ws"/>
    </port>
  </service>
  
</definitions>
```

### Tags Importantes:
- **`<definitions>`**: Elemento raiz que define o Web Service
- **`<types>`**: Especifica tipos de dados usando XML Schema
- **`<message>`**: Define estrutura das mensagens SOAP
- **`<portType>`**: Interface abstrata do serviço
- **`<binding>`**: Define protocolo de comunicação (SOAP/HTTP)
- **`<service>`**: Especifica endpoints concretos

## 🐍 Cliente Python - Como Funciona

Execute o cliente Python para demonstrar integração cross-platform:

```bash
# Instalar dependências
pip install requests

# Executar cliente (com servidor rodando)
python client_soap_python.py
```

### Como o Python usa o WSDL:

1. **Descoberta**: Faz GET para `/ws/usuarios.wsdl`
2. **Análise**: Parseia XML para entender estrutura
3. **Construção**: Monta envelopes SOAP baseados no schema
4. **Comunicação**: Envia POST com `Content-Type: text/xml`
5. **Processamento**: Analisa resposta XML usando ElementTree

### Exemplo de Envelope SOAP gerado:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:usr="http://proj.example.com/usuario">
    <soap:Header/>
    <soap:Body>
        <usr:getUsuarioRequest>
            <usr:id>1</usr:id>
        </usr:getUsuarioRequest>
    </soap:Body>
</soap:Envelope>
```

## 🧪 Testes Manuais

### 1. Testar Gateway
```bash
curl http://localhost:8080/gateway
```

### 2. Testar REST com HATEOAS
```bash
curl -H "Accept: application/json" http://localhost:8080/api/livros
```

### 3. Testar SOAP via Gateway
```bash
curl http://localhost:8080/gateway/usuarios
```

### 4. Criar Livro via Gateway
```bash
curl -X POST http://localhost:8080/gateway/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Test Book","autor":"Test Author","isbn":"123456789"}'
```

### 5. Testar SOAP Direto
```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml; charset=utf-8" \
  -d '<?xml version="1.0" encoding="UTF-8"?>
      <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
                     xmlns:usr="http://proj.example.com/usuario">
          <soap:Body>
              <usr:getAllUsuariosRequest/>
          </soap:Body>
      </soap:Envelope>'
```

## 📊 Tecnologias Utilizadas

- **Spring Boot 3.x** - Framework principal
- **Spring Web** - API REST
- **Spring Web Services** - SOAP
- **Spring HATEOAS** - Links dinâmicos
- **SpringDoc OpenAPI** - Documentação automática Swagger
- **Lombok** - Redução de boilerplate
- **JAXB** - Binding XML/Java
- **HTML5/CSS3/JavaScript** - Cliente web
- **Python + requests** - Cliente SOAP

## 🎯 Demonstração Presencial

### Checklist para apresentação:

1. **✅ Iniciar aplicação** - `./mvnw spring-boot:run`
2. **✅ Mostrar cliente web** - http://localhost:8080/
3. **✅ Demonstrar HATEOAS** - Clicar em "Listar Livros"
4. **✅ Mostrar Swagger** - http://localhost:8080/swagger-ui.html
5. **✅ Apresentar WSDL** - http://localhost:8080/ws/usuarios.wsdl
6. **✅ Executar cliente Python** - `python client_soap_python.py`
7. **✅ Testar integração Gateway** - Usuários via SOAP através do Gateway
8. **✅ Criar recursos** - Criar livro e usuário
9. **✅ Mostrar logs** - Demonstrar comunicação SOAP

### Pontos principais para destacar:
- **Gateway unifica** REST e SOAP
- **HATEOAS** em todas as respostas REST
- **WSDL gerado automaticamente** pelo Spring
- **Cliente Python** usando WSDL para integração
- **Swagger documentação** automática e interativa

## 🔧 Estrutura de Diretórios

```
proj/
├── src/main/java/com/example/proj/
│   ├── LibraryApplication.java          # Aplicação principal + Swagger
│   ├── config/
│   │   └── WebServiceConfig.java        # Configuração SOAP
│   ├── controller/
│   │   ├── GatewayController.java       # 🚪 Gateway principal
│   │   ├── LivroController.java         # 📚 API REST
│   │   └── UsuarioSoapController.java   # 🌐 API SOAP
│   └── model/
│       ├── Livro.java                   # Modelo Livro
│       └── Usuario.java                 # Modelo Usuario
├── src/main/resources/
│   ├── application.properties           # Configurações
│   ├── usuarios.xsd                     # Schema XSD para SOAP
│   └── static/
│       └── index.html                   # 🌐 Cliente web
├── client_soap_python.py               # 🐍 Cliente Python
├── pom.xml                              # Dependências Maven
└── README.md                            # Esta documentação
```

---

**🎓 Projeto desenvolvido para demonstrar integração de tecnologias REST e SOAP através de API Gateway com documentação automática e cliente cross-platform.**