# Urna Eletrônica em Java com Swing e PostgreSQL

Este repositório contém uma aplicação de urna eletrônica desenvolvida em Java utilizando a biblioteca Swing para a interface gráfica, e PostgreSQL como banco de dados. O objetivo do projeto é simular o funcionamento de uma urna eletrônica, permitindo o cadastro de candidatos e partidos, realização de votações e visualização de resultados de forma integrada ao banco de dados.

## Funcionalidades

- **Cadastro de Partidos:**
  - Inserir, listar, editar e excluir partidos políticos.

- **Cadastro de Candidatos:**
  - Vincular candidatos a partidos e definir informações como nome, número e cargo.

- **Realização de Votação:**
  - Interface intuitiva para o eleitor realizar seu voto de forma simples e segura.

- **Visualização de Resultados:**
  - Exibir relatórios de votos por candidato e partido.

## Estrutura do Banco de Dados

A aplicação utiliza um banco de dados PostgreSQL para armazenar informações essenciais. As principais tabelas são:

- **partidos:** Armazena informações sobre os partidos políticos.
- **candidatos:** Armazena os dados dos candidatos, incluindo o vínculo com um partido.
- **votos:** Registra os votos realizados na urna.

## Tecnologias Utilizadas

- **Java:** Linguagem principal utilizada para desenvolver a lógica e a interface gráfica.
- **Swing:** Biblioteca para criação de interfaces gráficas (GUI).
- **PostgreSQL:** Sistema de gerenciamento de banco de dados relacional.
- **JDBC:** Para integração entre a aplicação Java e o banco de dados PostgreSQL.


Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests para melhorar o projeto.

