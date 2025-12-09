# pos-tech-g57-app-users

Sistema de gerenciamento de usuários (parte do projeto pos-tech-g57), desenvolvido como parte do Tech Challenge.

# Índice

1. [Problema e Solução](#problema-e-solução)
2. [Versão](#versão)
3. [Stack Tecnológica](#stack-tecnológica)
4. [Arquitetura](#arquitetura)
		- [Estrutura de Pacotes](#estrutura-de-pacotes)
5. [Funcionalidades](#funcionalidades)
6. [Próximas Features](#próximas-features)
7. [Como Executar](#como-executar)
		- [Requisitos mínimos](#requisitos-mínimos)
		- [Localmente](#localmente)
		- [Com Docker](#com-docker)
8. [Documentação da API](#documentação-da-api)
		- [Principais Endpoints](#principais-endpoints)
9. [Colaboradores](#colaboradores)
10. [Licença](#licença)


## Problema e Solução
Visite o documento (documentations/PROBLEM.md) para mais detalhes sobre o problema que este projeto resolve.

## Versão
0.0.1

## Stack Tecnológica

- **Linguagem**: ☕ Java 21
- **Framework**: 🌱 Spring Boot 3.x
- **Maven**: 🛠️ Maven (wrapper incluído)
- **Banco de Dados**: 🐘 PostgreSQL (produção) / H2 (local)
- **ORM**: 🔄 JPA / Hibernate
- **Containerização**: 🐳 Docker
- **Documentação API**: 📄 Swagger / OpenAPI

## Arquitetura

O projeto utiliza Arquitetura Hexagonal (Ports & Adapters) para garantir:
- Separação clara entre domínio e infraestrutura
- Independência de frameworks
- Facilidade de testes
- Baixo acoplamento
- Escalabilidade e flexibilidade

### Estrutura de Pacotes

```
br.com.five.seven.food
├── application
│   ├── ports
│   │   ├── in           # Portas de Entrada (Use Cases)
│   │   └── out          # Portas de Saída (Repositories)
│   └── service          # Implementação dos Use Cases
├── domain               # Entidades e regras de domínio
├── infra                # Implementação de infraestrutura (persistence, security, etc.)
└── rest                 # Controllers, requests e responses
```

## Funcionalidades

- Cadastro e gestão de clientes
- Endpoints de health check
- CRUD básico para recursos (ex.: produtos, categorias)
- Gerenciamento de pedidos e notificações de pagamento
- Autenticação e autorização com Spring Security
- Documentação da API com Swagger/OpenAPI

## Como Executar

### Requisitos mínimos
- Git
- Java 21 (JDK)
- Docker (opcional)
- Maven (opcional — é possível usar o wrapper `mvnw`/`mvnw.cmd`)

Algumas variáveis de ambiente utilizadas pela aplicação podem estar definidas em arquivos de configuração como `application.yml` ou perfis específicos. Para integrações externas (ex.: Mercado Pago), adicione os tokens e variáveis necessários conforme explicado nos arquivos de documentação do projeto.

### Localmente

1. Clone o repositório

```bash
git clone https://github.com/rachelkozlowsky/pos-tech-g57-app-users.git
cd pos-tech-g57-app-users
```

2. Defina variáveis de ambiente necessárias (exemplo):

```bash
# Exemplo para desenvolvimento
SET ENVIRONMENT_PROFILE_VALUE=des
SET JWT_TOKEN_PIX_APPLICATION_PAYMENT=SEU_TOKEN_AQUI
```

3. Use o wrapper Maven para executar a aplicação:

Em Linux / macOS:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Em Windows (PowerShell):
```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

### Com Docker

1. Build da imagem Docker:

```bash
docker build -t pos-tech-g57-app-users .
```

2. Executar container:

```bash
docker run -p 8080:8080 --env-file ./.env pos-tech-g57-app-users
```

Observação: o repositório contém um `Dockerfile` e manifestos Kubernetes em `k8s/` para deploy em ambientes de produção.

## Documentação da API

A documentação da API está disponível via Swagger UI (quando a aplicação estiver em execução):

- Local: http://localhost:8080/swagger-ui.html

### Principais Endpoints
- Cadastro do Cliente:
	- POST - /v1/clients
- Identificação do Cliente via CPF:
	- GET - /v1/clients/{cpf}
- CRUD de Products:
	- POST - /v1/products
	- PUT  - /v1/products/{id}
	- DELETE - /v1/products/{id}
- Buscar produtos por categoria:
	- GET - /v1/products/categories/{categoryName}
- Notificação de pagamento (fila / checkout):
	- POST - /v1/payments/notification
- Listar pedidos:
	- GET - /v1/orders

Obs.: Consulte os controllers em `src/main/java/br/com/five/seven/food/rest` para a lista completa de endpoints e modelos de request/response.

## Colaboradores

- [@filipepereir](https://github.com/filipepereir) - Filipe Pereira - RM362782
- [@fnakata](https://github.com/Nakatasama) - Felipe Nakata - RM364391
- [@forgelucas](https://github.com/forgelucas) - Lucas Forge - RM364441
- [@rachelkozlowsky](https://github.com/rachelkozlowsky) - Rachel Kozlowsky - RM362994
- [@railsonribeiro](https://github.com/railsonribeiro) - Railson Ribeiro - RM362790

## Licença

Esse projeto está sob licença. Veja o arquivo `documentations/LICENSE.md` para mais detalhes.
