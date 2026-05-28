# Backend - FalaTrabalho

Este diretorio contem a API do FalaTrabalho, responsavel por receber audios do app mobile, transformar as respostas em texto estruturado, melhorar o conteudo com IA e gerar o curriculo final.

## Stack

- Java.
- Spring Boot.
- Maven.
- Spring WebMVC para endpoints HTTP.
- Thymeleaf para templates HTML.
- Swagger/OpenAPI via `springdoc-openapi` para documentacao da API.
- Lombok, quando fizer sentido reduzir boilerplate.

## Fluxo da requisicao

1. O app mobile envia respostas em audio para o backend.
2. O backend recebe e valida o arquivo de audio.
3. O audio e processado com `whisper.cpp` para transcricao em texto.
4. O texto transcrito e enviado para uma LLM local.
5. A LLM corrige erros de transcricao, melhora a escrita e organiza o conteudo.
6. O backend insere as informacoes tratadas em um modelo HTML de curriculo.
7. O HTML preenchido e convertido em PDF.
8. O PDF final e disponibilizado para o usuario.

## IA e processamento

- Transcricao: `whisper.cpp`.
- Execucao local da LLM: `llama.cpp`.
- Modelo inicial planejado: `Llama 3.1 8B`.
- A IA deve ser usada para corrigir, melhorar e estruturar o texto, mantendo fidelidade as informacoes fornecidas pelo usuario.

## Geracao de curriculo

- Use o template curriculum.html para estruturar o curriculo.
- A geracao de PDF deve partir do HTML preenchido.
- A tecnologia prevista para PDF e OpenPDF.

## Diretrizes de implementacao

- Mantenha a API simples e orientada ao fluxo principal: audio -> transcricao -> melhoria com IA -> HTML -> PDF.
- Separe responsabilidades entre controllers, services e componentes de integracao com ferramentas externas.
- Evite acoplar diretamente controllers a `whisper.cpp`, `llama.cpp` ou geradores de PDF; encapsule essas chamadas em services/adapters.
- Documente endpoints novos no Swagger/OpenAPI quando fizer sentido.
- Ao mexer no backend, rode `mvn test` quando houver mudanca de comportamento ou integracao relevante.
- O idioma do git commit deve ser pt-br.