<div align="center">
  
# liter-alura-cli
Aplicação de linha de comando em desenvolvimento com Spring Shell, Spring Data JPA e a api do Gunteberg para demonstrar habilidades em desenvolvimento, consumo e armazenamento de dados de APIs
</div>

[banner](src/main/resources/banner.png)

> [!TIP]
> O projeto esta seguindo uma abordagem GIT FLOW (develop -> main), alem de ser orientado a TDD: Test Driven Development

## Tecnologias
- Spring Framework:
  - Shell
  - Data JPA
- Postgres on Supabase
- Guntenberg API 

## Como rodar o projeto
1. Clone o projeto
   ```bash
      git clone github.com/filoroch/liter-alura-cli && cd liter-alura-cli
    ```
2. Instale as dependencias via maven
    ```bash
      .\mvnw clean install #windows
      ./mvnw clean install #linux
    ```
3. Execute o docker-compose para subir o banco Postgres
   ```bash
      docker-compose up -d
    ```
4. Configure o arquivo application.properties com as credenciais do banco
    - `DB_HOST` = `localhost` no caso do docker. Tambem pode usar um servidor supabase
    - `DB_PORT` = `5432` porta padrao do Postgres
    - `DB_NAME` = `liter_alura_cli` nome do banco
    - `DB_USER` = `seu_usuario` usuario do banco
    - `DB_PASSWORD` = `sua_senha` senha do banco
3. Rode a aplicação
   ```bash
      .\mvnw spring-boot:run #windows
      ./mvnw spring-boot:run #linux
    ```
## Issues
- [x] Criar repósitorio GIT
- [x] Criar um basic README
- [ ] Configurar Spring Shell
- [ ] Configurar Spring Data JPA
- [ ] Configurar conexão com banco Postgres

> [!WARNING]
> Esse projeto esta atualmente em desenvolvimento e pode não funcionar como esperado. Caso identifique algum problema ou queira sugerir algo, por favor, abra uma ISSUE

