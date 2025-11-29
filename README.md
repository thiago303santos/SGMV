# SGMV - Sistema de Gerenciamento de Manutenções Veiculares

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)
![Badge Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Badge Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![Badge MariaDB](https://img.shields.io/badge/DB-MariaDB-blue?style=for-the-badge&logo=mariadb)

## 📌 Sobre o Projeto

O **SGMV (Sistema de Gerenciamento de Manutenções Veiculares)** é uma plataforma web desenvolvida como Trabalho de Conclusão de Curso (TCC) para o curso de Tecnologia em Análise e Desenvolvimento de Sistemas.

O objetivo principal do sistema é modernizar e otimizar a gestão de oficinas mecânicas, substituindo métodos manuais e planilhas por um controle centralizado e seguro. O SGMV foca no controle de serviços preventivos e corretivos, oferecendo funcionalidades para cadastro de clientes, veículos, controle de estoque, agendamentos e o ciclo completo de Ordens de Serviço (OS), gerando um histórico detalhado de manutenções.

## 🚀 Funcionalidades Principais

O sistema foi projetado com base em requisitos funcionais sólidos para atender às necessidades operacionais e gerenciais de uma oficina:

### Módulo Administrativo e de Acesso
* **Controle de Acesso:** Autenticação segura (Login) e controle de sessões.
* **Perfis de Usuário:** Diferenciação entre perfis de "Administrador" (acesso total) e "Colaborador" (focado no operacional).
* **Gestão de Usuários:** Cadastro e gerenciamento da equipe da oficina.

### Módulo Operacional
* **Gestão de Clientes:** Cadastro completo de clientes (físicos e jurídicos) e histórico de relacionamento.
* **Gestão de Veículos:** Registro da frota de clientes, associando-os aos seus proprietários.
* **Controle de Estoque:** Gerenciamento de peças e produtos, com registro de entradas, saídas e alertas de estoque baixo.
* **Agendamentos:** Sistema para organizar a agenda da oficina, controlando datas, horários e status dos serviços.

### Módulo de Serviços
* **Ordens de Serviço (OS):** Ciclo completo da OS (abertura, execução, adição de peças/serviços, finalização e cálculo de valores).
* **Catálogo de Serviços:** Cadastro dos tipos de mão de obra oferecidos e seus valores base.

### Módulo Gerencial
* **Dashboard:** Visão geral com indicadores chave de desempenho (KPIs), como serviços realizados, faturamento e pendências.
* **Relatórios:** Geração de relatórios para análise de faturamento, movimentação de estoque e desempenho de serviços.

---

## 📷 Telas do Sistema

*(Sugestão: Adicione aqui capturas de tela do sistema em funcionamento, como a tela de Login, Dashboard e a tela de Cadastro de OS, presentes no Capítulo 4 do TCC)*

| Tela de Login | Dashboard Principal |
| :---: | :---: |
| *[Insira Imagem Aqui]* | *[Insira Imagem Aqui]* |

| Listagem de OS | Cadastro de Veículo |
| :---: | :---: |
| *[Insira Imagem Aqui]* | *[Insira Imagem Aqui]* |

---

## 🛠️ Tecnologias Utilizadas

O projeto foi desenvolvido utilizando uma arquitetura em camadas (MVC - Model-View-Controller) robusta e moderna.

### Backend
* **Java 17** (LTS)
* **Spring Boot 3.3.x**: Framework principal.
    * Spring Web (MVC)
    * Spring Data JPA (Persistência)
    * Spring Security (Autenticação e Autorização)
    * Spring Boot DevTools
    * SpringDoc OpenAPI (Documentação da API)
* **Maven**: Gerenciamento de dependências e build.

### Frontend
* **HTML5** & **CSS3**
* **Thymeleaf**: Template engine para renderização no servidor.
* **Bootstrap 5.3.x**: Framework CSS para responsividade e componentes de UI.
* **JavaScript** & **jQuery**: Interatividade e manipulação do DOM.
* **DataTables**: Para listagens interativas e avançadas de dados.

### Banco de Dados
* **MariaDB 10.6**: Sistema Gerenciador de Banco de Dados Relacional (SGBDR).

### Ferramentas de Desenvolvimento e Design
* **VS Code**: IDE principal.
* **Git & GitHub**: Controle de versão.
* **BrModelo & MySQL Workbench**: Modelagem do banco de dados (DER/MER).

---

## 🏗️ Arquitetura do Sistema

O sistema segue o padrão arquitetural **MVC (Model-View-Controller)** adaptado para o ecossistema Spring, garantindo a separação de responsabilidades:

1.  **Camada de Visão (View - Thymeleaf/HTML):** Responsável pela interface com o usuário.
2.  **Camada de Controle (Controller):** Gerencia as requisições HTTP, orquestra o fluxo e interage com a camada de serviço.
3.  **Camada de Serviço (Service):** Contém a lógica de negócios e regras do sistema.
4.  **Camada de Repositório (Repository - JPA):** Abstrai o acesso aos dados e a comunicação com o banco de dados MariaDB.
5.  **Camada de Modelo/Domínio (Model/Entity):** Representa as entidades do negócio (ex: Cliente, Veículo, OS).

---

## 🏁 Como Executar o Projeto

### Pré-requisitos

Para rodar o projeto localmente, você precisará das seguintes ferramentas instaladas:

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

## 👥 Autores

Trabalho desenvolvido pelos alunos do curso de Tecnologia em Análise e Desenvolvimento de Sistemas:

* **Marcos Martins da Silva**
* **Tassio Ferreira da Silva**
* **Thiago dos Santos**

**Orientadora:** Profa. Ma. Karla Roberto Sartin
**Coordenadora:** Profa. Dra Kerlla de Souza Luz

---

Este projeto é um trabalho acadêmico para fins de conclusão de curso.
