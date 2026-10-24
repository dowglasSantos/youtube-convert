# YouTube Convert 🎵

API REST desenvolvida com Java e Spring Boot para baixar e converter vídeos do YouTube em arquivos de áudio MP3, utilizando o `yt-dlp` para realizar o download e o `FFmpeg` para auxiliar na conversão.

O projeto foi desenvolvido para praticar integração entre aplicações Java e processos externos do sistema operacional, manipulação de requisições HTTP e tratamento de exceções.

## 🚀 Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — desenvolvimento da API REST.
- **Maven** — gerenciamento de dependências e build do projeto.
- **yt-dlp** — download e extração de áudio dos vídeos.
- **Deno** — runtime JavaScript utilizado pelo `yt-dlp` para resolver desafios de extração do YouTube.
- **FFmpeg** — processamento e conversão de áudio.
- **Postman** — testes das requisições HTTP.

## ⚙️ Funcionalidades

- Receber URLs de vídeos por meio de requisições HTTP.
- Baixar o áudio dos vídeos do YouTube.
- Converter o áudio para MP3.
- Configurar a qualidade do áudio em 192 Kbps.
- Salvar os arquivos convertidos no diretório `Downloads` do usuário.
- Executar comandos externos por meio da classe `ProcessBuilder`.
- Tratar erros durante a execução do processo de download e conversão.

## 📋 Pré-requisitos

Antes de executar o projeto, instale:

- Java compatível com a versão configurada no projeto.
- Maven, caso não utilize o Maven Wrapper.
- `yt-dlp`.
- Deno.
- FFmpeg.

No Ubuntu, o FFmpeg pode ser instalado com:

```bash
sudo apt update
sudo apt install ffmpeg
```

Para verificar as ferramentas instaladas:

```bash
java -version
mvn -version
deno --version
ffmpeg -version
yt-dlp --version
```

O projeto utiliza o `yt-dlp` com suporte a runtime JavaScript e componentes EJS. Consulte a [documentação oficial do yt-dlp](https://github.com/yt-dlp/yt-dlp) para obter instruções de instalação e atualização.

## 📥 Instalação

Clone o repositório:

```bash
git clone URL_DO_SEU_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd youtube-convert
```

Compile a aplicação:

```bash
mvn clean package
```

Execute o projeto:

```bash
mvn spring-boot:run
```

A porta utilizada depende da configuração da aplicação, definida normalmente em `application.properties` ou `application.yml`.

## 🔗 Como utilizar a API

As requisições podem ser realizadas pelo Postman.

Envie uma requisição `POST` para o endpoint de conversão configurado no controller, utilizando o corpo no formato JSON:

```json
{
  "url": "https://youtu.be/ID_DO_VIDEO"
}
```

Configure o cabeçalho:

```http
Content-Type: application/json
```

Substitua a URL de exemplo pelo endereço do vídeo que deseja converter.

**Observação:** o caminho do endpoint e o formato exato da resposta dependem da implementação atual do controller.

## 🛠️ Como funciona

O fluxo de execução da aplicação é o seguinte:

1. O cliente envia uma requisição HTTP com a URL do vídeo.
2. O controller recebe os dados e encaminha a URL ao serviço.
3. O serviço configura o `ProcessBuilder` para executar o `yt-dlp`.
4. O `yt-dlp` obtém o áudio do vídeo, utilizando o runtime JavaScript quando necessário.
5. O áudio é convertido para MP3 com a qualidade configurada.
6. O arquivo final é salvo no diretório `Downloads`.
7. O serviço verifica o código de saída do processo e trata eventuais falhas.

## 📂 Diretório de saída

Por padrão, os arquivos são salvos em:

```text
/home/usuario/Downloads/
```

O caminho é definido a partir do diretório pessoal do usuário que executa a aplicação.

O nome do arquivo é gerado com base no título do vídeo.

## ⚠️ Tratamento de erros

A aplicação considera possíveis falhas durante a execução do processo externo, como:

- `yt-dlp` não encontrado ou sem permissão de execução.
- URL inválida ou vídeo indisponível.
- Falhas de rede ou respostas HTTP como `403 Forbidden`.
- Ausência de dependências necessárias à conversão.
- Interrupção do processo durante a execução.

O código de saída do processo é utilizado para identificar se a execução foi concluída com sucesso.

## 🔒 Uso responsável

Utilize o projeto somente com conteúdos que você tenha autorização para baixar e converter, respeitando os direitos autorais, os termos de serviço da plataforma e a legislação aplicável.

## 📚 Objetivos de aprendizagem

Este projeto permite praticar conceitos importantes do desenvolvimento backend com Java:

- Criação de APIs REST com Spring Boot.
- Separação de responsabilidades entre controller e service.
- Recebimento e validação de dados JSON.
- Execução de processos externos com `ProcessBuilder`.
- Captura de saída e códigos de retorno de programas externos.
- Tratamento de exceções.
- Integração entre Java e ferramentas de linha de comando.
- Testes de endpoints com Postman.

## 🗺️ Melhorias futuras

- Retornar o caminho exato do arquivo gerado.
- Disponibilizar um endpoint para baixar o MP3 pela própria API.
- Implementar validação de URLs.
- Melhorar o tratamento de erros e as mensagens HTTP.
- Implementar logs estruturados.
- Adicionar testes unitários com JUnit e Mockito.
- Permitir configurar a pasta de saída e a qualidade do áudio.

## 👨‍💻 Autor

Desenvolvido como projeto de estudo para aprofundar conhecimentos em Java, Spring Boot e integração com processos externos.
