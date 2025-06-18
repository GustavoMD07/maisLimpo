
# 📌 + Limpo

O **+Limpo** é uma **aplicação web full-stack** completa que ataca um problema do mundo real: a poluição nas praias de Santos (SP).
O sistema funciona como uma ponte entre a comunidade e a ação ambiental
, permitindo que voluntários e ONGs trabalhem juntos para manter a orla limpa.


## 🚀 Funcionalidades

### ✅ Cadastro e Gerenciamento de Usuários:
- Distinção entre tipos de usuário (**Voluntário** e **ONG**).
- Fluxo completo de autenticação com login e logout.
- Opção de "Lembrar de mim" com persistência de sessão via **Cookies HttpOnly**.

### ✅ Segurança e Confirmação de Conta:
- Confirmação de cadastro por e-mail com **token de uso único**.
- Fluxo completo de "**Esqueci minha Senha**" com validação por token via e-mail.
- **Senhas criptografadas** no banco de dados usando **BCrypt**.

### ✅ Sistema de Denúncias:
- Usuários podem registrar denúncias de lixo em praias específicas através de um **mapa interativo**.
- ONGs possuem acesso a um **painel restrito** para visualizar todas as denúncias feitas.
- Controle de acesso baseado em permissões para visualização de dados sensíveis.

### ✅ Qualidade de Código e Boas Práticas:
- Tratamento de exceções personalizado para a API.
- Arquitetura baseada em camadas (**Controller, Service, Repository**).
- Uso de **DTOs (Data Transfer Objects)**, incluindo **Records do Java** para imutabilidade.

---

## 🛠️ Tecnologias Utilizadas
- **Java 21** - Versão do Java utilizada para o projeto
- **Spring Boot** – Framework para facilitar o desenvolvimento e configuração  
- **Spring Data JPA** – Framework para persistência e interação com o banco de dados  
- **MySQL** – Banco de dados relacional para o armazenamento dos dados   
- **Bean Validation (Jakarta Validation)** – Validação de dados via anotações  
- **Lombok** – Geração automática de getters, setters, constructors, etc   
- **Maven** – Gerenciador de dependências e build    
- **jBCrypt** – Biblioteca para hashing e verificação de senhas   
- **Spring Mail** - Módulo para envio de e-mails (confirmação e recuperação de senha).
- **HTML5, CSS3, JavaScript** - Tecnologias padrão para a construção do frontend  

## 💡 Diferenciais
O grande diferencial do +Limpo não é uma integração técnica, mas sim seu propósito e impacto no mundo real.  

### 🌊 Foco em Ação Ambiental e Cidadania  
O projeto vai além de um simples CRUD, criando uma ferramenta com valor social e cívico. Ele empodera a comunidade a tomar uma atitude ativa contra a poluição, transformando uma simples denúncia em dados valiosos.  

#### **✅Benefícios:**
- **Engajamento Comunitário:** Conecta cidadãos comuns **(Voluntários)** e organizações **(ONGs)** em um objetivo comum.  
- **Ação Orientada por Dados:** Permite que as ONGs otimizem seus recursos, focando os esforços de limpeza nas áreas mais críticas com base nos relatórios gerados.  
- **Conscientização:** A própria existência e uso da plataforma serve como uma ferramenta de educação ambiental.
- **Solução Completa:** Demonstra a criação de uma solução **full-stack** de ponta a ponta para um problema não-trivial.
---

 ## 📂 Estrutura do Projeto

```plaintext
maislimpo
┣ 📂 src
┃ ┣ 📂 main
┃ ┃ ┣ 📂 java/com/maislimpo
┃ ┃ ┃ ┣ 📂 controller        # Endpoints da API REST
┃ ┃ ┃ ┣ 📂 DTO               # Objetos de Transferência de Dados
┃ ┃ ┃ ┣ 📂 entity            # Entidades JPA que representam as tabelas
┃ ┃ ┃ ┣ 📂 exception         # Exceções e Handlers globais
┃ ┃ ┃ ┣ 📂 repository        # Interfaces Spring Data JPA para acesso ao banco
┃ ┃ ┃ ┣ 📂 service           # Regras de negócio e lógica da aplicação
┃ ┃ ┣ 📂 resources
┃ ┃ ┃ ┣ 📂 static            # Arquivos do Frontend (HTML, CSS, JS)
┃ ┃ ┃ ┗ 📄 application.properties # Configurações da aplicação
┃ ┗ 📂 test
┣ 📄 README.md
┣ 📄 pom.xml                 # Arquivo de build com dependências
```
## 🔧 Como Executar o Projeto
1️⃣ Clone o repositório:
```bash
git clone https://github.com/GustavoMD07/maisLimpo
cd maislimpo
```

2️⃣ Execute a aplicação  
Opção 1: via terminal com Maven Wrapper
No Linux/macOS:
```bash
./mvnw spring-boot:run
```
No Windows:
```bash
mvnw spring-boot:run
```

Opção 2: via IDE (IntelliJ, Eclipse, VS Code)  

Abra o projeto na sua IDE favorita e execute o método main() da classe principal **MaislimpoApplication.java**.


4️⃣ Acesse o Frontend (obrigatório):
```
JDBC URL: jdbc:h2:mem:testdb
Usuário: postgres (padrão pelo usuário ou qual for outro usuário)
Senha: (definida pelo usuario)
```

## 🛡️ Tratamento de Exceções

O projeto possui um sistema de tratamento de erros personalizado usando:

- **Exceções customizadas na pasta exceptions**

- **Classe global de handler na pasta exceptions, utilizando @RestControllerAdvice**

Dessa forma, Qualquer erro de negócio ou validação retorna uma resposta clara e padronizada pro usuário da API.

## 📈 Melhorias Futuras
  
🔹 Testes unitários com JUnit  
🔹 Documentação de API com Swagger    
🔹 Subir em Nuvem (AWS ou Azure)  
🔹 Uso do Docker  
🔹 Melhorias na interfáce gráfica (reponsividade)  
🔹 Implementar a funcionalidade de anexo de fotos nas denúncias    

## 📌 Autor
👨‍💻 Gustavo Matachun Domingues
🔗 [LinkedIn](https://www.linkedin.com/in/gustavo-matachun/) | 📧 gustavomatachun.domingues@gmail.com

