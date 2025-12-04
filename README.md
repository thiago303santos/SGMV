# SGMV - Sistema de Gerenciamento de Manutenções Veiculares

## Sobre o Projeto

O **SGMV (Sistema de Gerenciamento de Manutenções Veiculares)** é uma plataforma web criada como Trabalho de Conclusão de Curso (TCC) no curso de Tecnologia em Sistemas de Informação. Ela gerencia manutenções de veículos em oficinas mecânicas, substituindo planilhas por controle centralizado de serviços preventivos e corretivos. O sistema inclui cadastro de clientes e veículos, controle de estoque, agendamentos e ordens de serviço (OS) com histórico de manutenções.

## Funcionalidades Principais

Acesso e administração: Login seguro, perfis para administrador e colaborador, e gestão de usuários.

Operacional: Cadastro de clientes (físicos ou jurídicos), registro de veículos, controle de estoque com entradas e saídas, e agendamentos com status.

Serviços: Criação e gerenciamento de ordens de serviço, incluindo adição de peças e mão de obra, além de catálogo de serviços com valores.

Gerencial: Dashboard com indicadores como serviços realizados e faturamento, e relatórios de estoque e desempenho.

---

## Tecnologias Utilizadas

O projeto foi desenvolvido utilizando uma arquitetura em camadas (MVC - Model-View-Controller).

Backend: Java 17, Spring Boot 3.x, Spring Web, JPA, Security, DevTools, OpenAPI e Maven.

Frontend: HTML5, CSS3, Thymeleaf, Bootstrap 5.x, JavaScript, jQuery e DataTables.

Banco de dados: MariaDB 10.6.

Ferramentas: VS Code, Git/GitHub, BrModelo e MySQL Workbench.

---

## Arquitetura do Sistema

O sistema usa padrão MVC com Spring: visão (Thymeleaf/HTML), controladores para requisições, serviços para lógica de negócios, repositórios JPA para dados e entidades para o domínio.

---

## Como Executar o Projeto

### Pré-requisitos

Para rodar o projeto localmente, será necessário as seguintes ferramentas instaladas:

* [Java JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
* [Maven](https://maven.apache.org/)
* [MariaDB Server](https://mariadb.org/) (ou MySQL compatível)
* [Git](https://git-scm.com/)

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/SGMV.git](https://github.com/SEU-USUARIO/SGMV.git)
    cd SGMV
    ```

2.  **Configuração do Banco de Dados:**
    * Crie um banco de dados vazio no seu servidor MariaDB (ex: `sgmv_db`).
    * Abra o arquivo `src/main/resources/application.properties`.
    * Atualize as credenciais de conexão conforme o seu ambiente local:
        ```properties
        spring.datasource.url=jdbc:mariadb://localhost:3306/sgmv_db?useTimezone=true&serverTimezone=UTC
        spring.datasource.username=SEU_USUARIO_DO_BANCO
        spring.datasource.password=SUA_SENHA_DO_BANCO
        # spring.jpa.hibernate.ddl-auto=update # Descomente na primeira execução para criar as tabelas
        ```

3.  **Build e Execução:**
    * Na raiz do projeto, execute o comando Maven para baixar as dependências e compilar:
        ```bash
        mvn clean install
        ```
    * Após o build, execute a aplicação Spring Boot:
        ```bash
        mvn spring-boot:run
        ```

4.  **Acesso:**
    * A aplicação estará disponível em: `http://localhost:8080` (ou na porta definida no `application.properties`).

---

## Autores

Trabalho desenvolvido pelos alunos do curso de Tecnologia em Sistemas de Informação:

* **Marcos Martins da Silva**
* **Nathan Faleiro Rodrigues**
* **Thiago Santos Cantanhede**

**Orientadora:** Profa. Ma. Karla Roberto Sartin
**Coordenadora:** Profa. Dra Kerlla de Souza Luz

---

Este projeto é um trabalho acadêmico para fins de conclusão de curso.
