# Trabalho de Desenvolvimento de Aplicações WEB

**Nossa Equipe:**
- Tiago Godinho Freitas
- João Alberto de Sousa Leite Filho

## Descrição do Projeto
Este projeto é um **Sistema de Gerenciamento de Tarefas (Task Manager)** que nós desenvolvemos como requisito para a disciplina de Desenvolvimento de Aplicações WEB. Nós fizemos uma aplicação Full-stack (Front-end e Back-end) completa, com integração ao banco de dados relacional e controle de segurança com login e senha via tokens JWT.

## Arquitetura e Tecnologias que utilizamos
- **Back-end:** Usamos o Java 21 com Spring Boot 3.x, Spring Data JPA para o banco, e o Spring Security pra fazer a parte da segurança que o professor pediu (JWT - Stateless).
- **Banco de Dados:** Usamos o PostgreSQL (criamos as tabelas: `usuario` e `task`).
- **Front-end:** Fizemos tudo na mão com HTML5, CSS3 e JavaScript puro. Usamos a Fetch API para conectar o front no back-end (deixamos os arquivos lá na pasta `static` do Spring Boot).

## Como Executar nosso projeto
1. A primeira coisa é ter o Java 21 e o PostgreSQL instalados na máquina.
2. Crie um banco de dados no seu pgAdmin chamado `trabalho_web` (ou pode ir lá no arquivo `src/main/resources/application.properties` e trocar pra sua senha do postgres).
3. Pra rodar o projeto usando o Maven, abre o terminal na pasta `trabalhoweb` e roda o comando:
   - No Windows: `.\mvnw.cmd spring-boot:run`
   - Se for usar o Eclipse, é só importar a pasta e dar "Run As -> Java Application".
4. Depois que rodar, é só entrar no navegador pelo link: [http://localhost:8080/](http://localhost:8080/)

## Funcionalidades que nós implementamos
- **Cadastro e Login:** O usuário pode criar a conta e fazer o login. Tudo isso devolve um token JWT que dá permissão de acessar o sistema.
- **Painel de Tarefas:** Uma regra de segurança que nós colocamos foi que cada usuário só consegue ver e gerenciar as **suas próprias tarefas**.
- **CRUD de Tarefas:** Dá pra Criar, Listar, Editar e Excluir as tarefas.

## Organização do nosso código
Organizamos todo o nosso código fonte em Java e os arquivos do site em pacotes divididos por responsabilidade (`controller`, `model`, `repository`, `service`, `config`, `dto`). Também colocamos comentários explicando o que a gente fez em cada método para mostrar que entendemos bem o conteúdo da disciplina e facilitar a leitura.
