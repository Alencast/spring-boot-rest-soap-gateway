package com.example.proj.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * CONFIGURAÇÃO PRINCIPAL DO SISTEMA SOAP
 * 
 * Esta classe é responsável por configurar todo o ambiente de Web Services SOAP
 * na aplicação Spring Boot. Ela ativa o motor SOAP, configura os endpoints
 * e gera automaticamente o WSDL.
 */
@EnableWs  // ⚡ ATIVA o sistema de Web Services SOAP no Spring
@Configuration  // 🏗️ Indica que esta classe contém configurações do Spring
public class WebServiceConfig {
    
    /**
     * CONFIGURA O SERVLET QUE GERENCIA REQUISIÇÕES SOAP
     * 
     * Este bean cria um "porteiro inteligente" que escuta todas as requisições
     * SOAP e as direciona para os endpoints corretos.
     * 
     * @param applicationContext Contexto do Spring para encontrar os @Endpoints
     * @return Servlet configurado para processar requisições SOAP
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        // Cria o servlet que atua como "central de despacho" para requisições SOAP
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        
        // Conecta o servlet com o contexto do Spring, permitindo que ele encontre
        // automaticamente todos os @Endpoints registrados na aplicação
        servlet.setApplicationContext(applicationContext);
        
        // Habilita a correção automática de URLs no WSDL gerado
        // Isso garante que as URLs apontem para o endereço real do servidor
        servlet.setTransformWsdlLocations(true);
        
        // Registra o servlet para escutar TODAS as requisições que começam com "/ws/"
        // Exemplos de URLs que serão atendidas:
        // - http://localhost:8080/ws/usuarios.wsdl (para pegar o WSDL)
        // - http://localhost:8080/ws (para fazer chamadas SOAP)
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    
    /**
     * CONFIGURA A GERAÇÃO AUTOMÁTICA DO WSDL
     * 
     * Este bean gera automaticamente o arquivo WSDL que serve como "contrato"
     * do serviço SOAP. Clientes usam este WSDL para saber como consumir o serviço.
     */
    @Bean(name = "usuarios")  //  Nome do WSDL: usuarios.wsdl
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema usuariosSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        
        // Nome do "port" no WSDL - representa o ponto de acesso ao serviço
        wsdl11Definition.setPortTypeName("UsuariosPort");
        
        // URI base onde o serviço está disponível
        wsdl11Definition.setLocationUri("/ws");
        
        // Namespace único que identifica este serviço SOAP
        // Funciona como um "CPF" do serviço para evitar conflitos
        wsdl11Definition.setTargetNamespace("http://proj.example.com/usuario");
        
        // Schema XSD que define a estrutura dos XMLs trocados
        wsdl11Definition.setSchema(usuariosSchema);
        
        return wsdl11Definition;
    }
    
    /**
     * CARREGA O SCHEMA XSD QUE DEFINE A ESTRUTURA DOS DADOS
     * 
     * Este bean carrega o arquivo XSD que funciona como "molde" para os XMLs
     * trocados no SOAP. Ele define quais tags são válidas e sua estrutura.
     * 
     * @return Schema XSD carregado do arquivo usuarios.xsd
     */
    @Bean
    public XsdSchema usuariosSchema() {
        // 📁 Carrega o arquivo usuarios.xsd da pasta src/main/resources/
        // Este arquivo define a estrutura dos XMLs de request e response
        return new SimpleXsdSchema(new ClassPathResource("usuarios.xsd"));
    }
}