# FalaTrabalho

Aplicação mobile voltada para transformar respostas em áudio em um currículo pronto, ajudando pessoas com dificuldade de escrita, baixa familiaridade com tecnologia ou menor acesso a ferramentas digitais a aumentarem suas chances de conseguir trabalho.

## Visão Geral

O FalaTrabalho guia o usuário por um fluxo simples de perguntas necessárias para montar um currículo. Em vez de exigir que a pessoa escreva todas as informações manualmente, o aplicativo permite que ela responda por voz.

As respostas em áudio são enviadas para o backend, onde passam por etapas de transcrição, correção, melhoria textual e geração do currículo final em PDF.

## Objetivo

O objetivo do projeto é reduzir barreiras de acesso ao mercado de trabalho por meio de uma experiência simples e assistida.

A aplicação busca apoiar pessoas que:

- Têm dificuldade para escrever um currículo.
- Possuem pouca familiaridade com computadores, celulares ou ferramentas de edição.
- Precisam organizar suas experiências, habilidades e informações pessoais de forma mais apresentável.
- Desejam gerar um currículo pronto a partir de respostas faladas.

## Como Funcionará

O aplicativo fará perguntas ao usuário para preencher as principais informações do currículo, como:

- Nome.
- Idade.
- Telefone.
- Experiências profissionais.
- Habilidades.
- Outras informações relevantes para o currículo.

Após cada resposta, o áudio será processado pelo backend e transformado em texto estruturado para compor o currículo.

## Fluxo de Processamento

1. O usuário responde às perguntas por áudio no aplicativo mobile.
2. O áudio é enviado para o backend.
3. O backend utiliza `whisper.cpp` para converter o áudio em texto.
4. O texto transcrito é enviado para uma LLM, atualmente planejada como `Llama 3.1 8B`.
5. A LLM corrige erros de transcrição, melhora a escrita e deixa o conteúdo mais apresentável.
6. O texto corrigido é enviado para um serviço responsável por inserir as informações em um modelo HTML de currículo.
7. O HTML preenchido é convertido em PDF.
8. O currículo final em PDF é disponibilizado ao usuário.

## Arquitetura Prevista

O projeto será dividido em duas partes principais:

- `front`: aplicação mobile responsável pela interface com o usuário, gravação das respostas em áudio e comunicação com o backend.
- `back`: API responsável por receber os áudios, transcrever, processar os textos com IA, gerar o currículo em HTML e converter o resultado para PDF.

## Tecnologias

### Frontend Mobile

- React Native.
- JavaScript.
- Stack de gravação de voz ainda a definir.

### Backend

- Java.
- Spring Boot.
- Swagger para documentação da API.

### Inteligência Artificial e Processamento

- `whisper.cpp` para transcrição de áudio em texto.
- `llama.cpp` para execução local da LLM.
- `Llama 3.1 8B` como modelo de linguagem inicial.

### Geração de Currículo

- Modelo HTML pré-pronto para estruturação do currículo.
- OpenPDF para geração do arquivo PDF a partir das informações processadas.

## Status do Projeto

Projeto em fase inicial de desenvolvimento e definição técnica. Algumas tecnologias, como a stack de gravação de voz no aplicativo mobile, ainda serão definidas conforme a evolução da implementação.
