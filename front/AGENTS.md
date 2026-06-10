# AGENTS.md

## Projeto

FalaTrabalho é um aplicativo mobile desenvolvido com Expo e React Native cujo objetivo é ajudar pessoas com baixa escolaridade ou pouca familiaridade com tecnologia a criarem currículos profissionais utilizando a própria voz.

O aplicativo conduz o usuário por uma entrevista simples, coleta respostas em áudio, envia essas respostas para o back-end e apresenta uma prévia do currículo gerado em PDF.

## Público-alvo

O público do aplicativo inclui pessoas com pouca instrução formal, idosos, trabalhadores informais e indivíduos com baixa alfabetização digital.

Todas as decisões de interface devem priorizar:

- Simplicidade;
- Clareza;
- Acessibilidade;
- Redução da carga cognitiva;
- Linguagem simples e acolhedora.

## Stack

- Expo
- React Native
- JavaScript
- React Navigation
- Axios
- Expo AV (gravação de áudio)

## Princípios de desenvolvimento

Ao implementar novas funcionalidades, siga as regras abaixo:

### Simplicidade acima de tudo

Evite soluções complexas quando houver alternativas mais simples.

Prefira código fácil de entender e manter.

### Experiência guiada

O usuário nunca deve enfrentar formulários longos.

A coleta de informações deve ocorrer por etapas, com apenas uma pergunta por vez.

Exemplo:

- Qual é o seu nome?
- Qual é seu telefone?
- Em qual cidade você mora?
- Onde você já trabalhou?
- O que você sabe fazer?

### Interface acessível

Sempre priorize:

- Botões grandes;
- Fontes legíveis;
- Alto contraste;
- Poucos elementos na tela;
- Textos curtos;
- Feedback visual claro após cada ação.

Evite excesso de informações.

### Fluxo principal

O fluxo padrão do aplicativo é:

Home
→ Entrevista
→ Revisão das respostas
→ Prévia do currículo
→ Geração/download do PDF
→ Tela de sucesso

Mudanças nesse fluxo devem possuir justificativa clara.

### Componentização

Prefira componentes reutilizáveis.

Exemplos:

- Button;
- QuestionCard;
- AudioRecorder;
- LoadingScreen;
- ErrorMessage.

Evite duplicação de código.

### Organização das telas

Estrutura recomendada:

src/
├── assets/
├── components/
├── hooks/
├── navigation/
├── screens/
├── services/
├── constants/
└── App.js

Cada tela deve ter responsabilidade única.

### Comunicação com o back-end

Toda comunicação deve ser centralizada em `services/api.js`.

Não realizar chamadas HTTP diretamente dentro dos componentes de interface.

### Estados de carregamento e erro

Toda ação assíncrona deve tratar:

- carregamento;
- sucesso;
- falha.

O usuário deve sempre entender o que está acontecendo.

### Código gerado por IA

Ao gerar código:

- explique decisões importantes;
- preserve o padrão existente do projeto;
- não introduza dependências sem justificativa;
- não refatore arquivos não relacionados à tarefa;
- mantenha comentários apenas quando agregarem valor.

### Objetivo do produto

O objetivo principal do FalaTrabalho não é demonstrar tecnologia avançada.

O objetivo é facilitar a criação de currículos para pessoas que normalmente teriam dificuldade em fazê-lo sozinhas.

Em situações de dúvida entre sofisticação técnica e facilidade de uso, escolha a facilidade de uso.

## Diretrizes de implementacao

- Mantenha a API simples e orientada ao fluxo principal: audio -> transcricao -> melhoria com IA -> HTML -> PDF.
- Separe responsabilidades entre controllers, services e componentes de integracao com ferramentas externas.
- Evite acoplar diretamente controllers a `whisper.cpp`, `llama.cpp` ou geradores de PDF; encapsule essas chamadas em services/adapters.
- Documente endpoints novos no Swagger/OpenAPI quando fizer sentido.
- Ao mexer no backend, rode `mvn test` quando houver mudanca de comportamento ou integracao relevante.
- O idioma do git commit deve ser pt-br.

## Respondendo perguntas

- Não gere códigos a não ser que seja expresso de forma explícita que você deve fazê-lo.
- Ao responder perguntas, não crie respostas só porque sim, se não houver respostas/melhora, apenas seja sincero.
