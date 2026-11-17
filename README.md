# Legaltech Evolution — Plataforma de Gestão Jurídica

Sistema web e API REST desenvolvidos em **Spring Boot** para gestão de vínculos entre clientes e legaltechs, contratos recorrentes, processos jurídicos, documentos e pagamentos. Projeto desenvolvido como Projeto Integrador (ESEG).

---

## Tecnologias Utilizadas

* **Linguagem & Runtime:** Java 17+
* **Framework Backend:** Spring Boot 4.1.2 (Web MVC, Data JPA, Validation, Security)
* **Frontend:** Thymeleaf, Thymeleaf Extras Spring Security 6, Bootstrap 5 (interface responsiva)
* **Banco de Dados:** H2 Database em memória (modo compatibilidade PostgreSQL) com carga automática via `schema.sql` e `data.sql`
* **Testes Automatizados:** JUnit 5, Spring Boot Test, MockMvc e Spring Security Test
* **Build & Produtividade:** Maven Wrapper (`mvnw`) e Lombok

---

## Evolução por Entregas

### Entrega 1 — Modelagem e Banco de Dados Relacional
* Estruturação de **20 tabelas normalizadas** em `src/main/resources/schema.sql` cobrindo localidades, clientes, legaltechs, planos, vínculos, contratos, histórico de status, processos jurídicos, documentos, eventos de documento, acordos, atendimentos e pagamentos.
* Carga inicial automatizada de registros de teste em `src/main/resources/data.sql`.

### Entrega 2 — Backend Spring Boot, JPA, Regras de Negócio e API REST
* Mapeamento objeto-relacional completo no pacote `model` e repositórios Spring Data JPA no pacote `repository`.
* Implementação da camada de serviços (`service`), DTOs (`dto`), tratamento global de exceções (`exception`) e endpoints REST (`controller`):
    * **RN-01 / RN-02:** Validação de integridade de vínculos e consistência cronológica das datas de início, fim e cancelamento de contratos.
    * **RN-04:** Bloqueio de aprovação ou assinatura de documentos jurídicos sem revisão humana prévia.
    * **RN-05:** Validações financeiras para pagamentos, assinaturas e estornos.
    * **Domínio Calculado:** Classificação automática de faixa de risco de perda processual (`BAIXO`, `MEDIO`, `ALTO`, `CRITICO`) e verificação de cancelamento contratual.

### Entrega 3 — Segurança, Perfis de Acesso, Interface Responsiva e Testes
* **Autenticação e Autorização (`SecurityConfig`):** Fluxo completo de login (`/login`) e logout (`/logout`) com invalidação de sessão e controle de acesso baseado em papéis (`ROLE_ADMIN` e `ROLE_OPERADOR`).
* **Portal Web Thymeleaf (`templates/`):** Telas responsivas para desktop e dispositivos móveis com renderização condicional (`sec:authorize`), dashboard com indicadores em tempo real do banco H2, painel administrativo de contratos e página customizada de erro `403 Acesso Negado`.
* **Testes Automatizados (`LegaltechIntegracaoTest`):** Suíte de 6 testes de integração validando persistência JPA, regras de domínio, redirecionamento de usuários não autenticados, bloqueio `403` para `OPERADOR`, acesso irrestrito para `ADMIN` e encerramento de sessão no logout.

---

## Como Executar o Projeto

### Pré-requisitos
* JDK 17 ou superior instalado.

### Executar a Aplicação
1. Clone o repositório e acesse a pasta raiz do projeto:
   ```bash
   git clone <URL_DO_SEU_REPOSITORIO>
   cd legaltech-evolution
   ```
2. Inicie o servidor utilizando o Maven Wrapper:
    * **Windows (PowerShell / CMD):**
      ```powershell
      .\mvnw spring-boot:run
      ```
    * **Linux / macOS:**
      ```bash
      ./mvnw spring-boot:run
      ```
3. A aplicação iniciará na porta **`8081`**.

### Executar os Testes Automatizados
```powershell
.\mvnw test
```

---

## Credenciais e Endpoints de Acesso

### Portal Web (`http://localhost:8081/login`)
A página de login possui botões de preenchimento rápido para testar ambos os perfis:

| Perfil | E-mail (Usuário) | Senha | Permissões |
| :--- | :--- | :--- | :--- |
| **Gestor Jurídico (`ADMIN`)** | `admin@legaltech.com` | `admin123` | Acesso total ao Dashboard, APIs REST e Gestão Financeira (`/admin/gestao`) |
| **Operador (`OPERADOR`)** | `operador@legaltech.com` | `oper123` | Acesso ao Dashboard e APIs REST; bloqueado (`403`) em rotas `/admin/**` |

### Console do Banco de Dados H2
* **URL:** `http://localhost:8081/h2-console`
* **JDBC URL:** `jdbc:h2:mem:legaltechdb`
* **User Name:** `sa`
* **Password:** *(deixar em branco)*

### Principais Rotas e Endpoints
* `GET /login` — Página de autenticação.
* `POST /logout` — Encerramento de sessão.
* `GET /dashboard` — Painel principal responsivo (requer autenticação).
* `GET /admin/gestao` — Gestão financeira de contratos (restrito a `ROLE_ADMIN`).
* `GET /api/v1/vinculos` — Listagem REST em JSON dos vínculos persistidos.

---

## Evidências de Execução

| Evidência | Descrição |
| :--- | :--- |
| ![Testes JUnit](docs/evidencias/01-testes-junit.png) | **1. Testes Automatizados:** Suíte `LegaltechIntegracaoTest` com 6/6 testes aprovados. |
| ![Console H2](docs/evidencias/02-h2-console.png) | **2. Banco de Dados:** Estrutura das 20 tabelas criadas no H2 Console (`jdbc:h2:mem:legaltechdb`). |
| ![API REST](docs/evidencias/03-api-rest.png) | **3. Persistência REST:** Resposta JSON do endpoint `/api/v1/vinculos`. |
| ![Tela de Login](docs/evidencias/04-login-perfis.png) | **4. Autenticação:** Tela de login responsiva com seleção rápida de perfis. |
| ![Acesso Negado](docs/evidencias/05-acesso-negado-operador.png) | **5. Autorização (Operador):** Bloqueio HTTP 403 ao tentar acessar área administrativa. |
| ![Gestão Admin](docs/evidencias/06-gestao-admin.png) | **6. Autorização (Admin):** Acesso liberado à tabela financeira de contratos. |