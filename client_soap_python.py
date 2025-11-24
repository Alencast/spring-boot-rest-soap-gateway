#!/usr/bin/env python3
"""
Cliente Python MÍNIMO para demonstrar consumo SOAP do Java
"""

import requests

def main():
    print("🐍 Cliente Python consumindo SOAP Java")
    
    # Envelope SOAP mínimo
    soap_xml = """<?xml version="1.0"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:usr="http://proj.example.com/usuario">
    <soap:Body>
        <usr:getAllUsuariosRequest/>
    </soap:Body>
</soap:Envelope>"""
    
    try:
        # Requisição SOAP
        response = requests.post(
            "http://localhost:8080/ws",
            data=soap_xml,
            headers={"Content-Type": "text/xml"}
        )
        
        print(f"Status: {response.status_code}")
        print("Resposta SOAP:")
        print(response.text)
        
    except Exception as e:
        print(f"Erro: {e}")

if __name__ == "__main__":
    main()