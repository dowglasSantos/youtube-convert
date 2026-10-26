# YouTube Convert 🎵

API REST desenvolvida com Java e Spring Boot para baixar e converter vídeos do YouTube em arquivos de áudio MP3, utilizando o `yt-dlp` para extração do áudio e o FFmpeg para conversão.

O projeto foi desenvolvido para praticar desenvolvimento backend, integração entre aplicações Java e processos externos do sistema operacional, manipulação de requisições HTTP, tratamento de exceções e conteinerização com Docker.

## 🚀 Tecnologias utilizadas

- **Java 17** — linguagem de programação.
- **Spring Boot** — desenvolvimento da API REST.
- **Maven** — gerenciamento de dependências e build.
- **yt-dlp** — download e extração de áudio.
- **Deno** — runtime JavaScript utilizado pelo `yt-dlp` quando necessário.
- **FFmpeg** — processamento e conversão de áudio.
- **Docker** — empacotamento e execução da aplicação em containers.
- **Postman** — testes de requisições HTTP.

## ⚙️ Funcionalidades

- Receber URLs de vídeos por requisições HTTP.
- Extrair o áudio de vídeos disponíveis.
- Converter o áudio para MP3.
- Configurar a qualidade de áudio em 192 Kbps.
- Executar processos externos com `ProcessBuilder`.
- Configurar o diretório de saída por variável de ambiente.
- Tratar falhas durante o download e a conversão.
- Executar a aplicação em um container Docker.

## 📋 Pré-requisitos

Para executar a aplicação localmente, você precisará de:

- Java 17.
- Maven ou Maven Wrapper.
- `yt-dlp`.
- Deno.
- FFmpeg.

Para executar com Docker, você precisará de:

- Docker Engine instalado e em execução.

## 🐳 Executando com Docker

A aplicação possui um `Dockerfile` com duas etapas: uma para compilar o projeto Java e outra para criar o ambiente de execução com as dependências necessárias.

### 1. Clonar o repositório

```bash
git clone https://github.com/dowglasSantos/youtube-convert.git
cd youtube-convert
```

### 2. Construir a imagem

```bash
docker build -t youtube-convert:1.0 .
```

### 3. Criar o diretório de downloads

No Linux:

```bash
mkdir -p ~/youtube-convert-downloads
```

### 4. Executar o container

```bash
docker run -d \
  --name youtube-convert \
  -p 8080:8081 \
  -e DOWNLOAD_DIR=/downloads \
  -v ~/youtube-convert-downloads:/downloads \
  youtube-convert:1.0
```

A aplicação Spring Boot utiliza a porta `8081` dentro do container. A opção `-p 8080:8081` disponibiliza a API na porta `8080` do computador.

Os arquivos convertidos ficam no diretório `~/youtube-convert-downloads` do computador, preservados mesmo quando o container é removido.

### 5. Verificar a execução

Consultar os containers:

```bash
docker ps
```

Consultar os logs da aplicação:

```bash
docker logs -f youtube-convert
```

Parar o container:

```bash
docker stop youtube-convert
```

Iniciar novamente:

```bash
docker start youtube-convert
```

Remover o container:

```bash
docker rm -f youtube-convert
```

A remoção do container não remove a imagem nem os arquivos já salvos no diretório montado no computador.

## 💻 Executando localmente

### 1. Instalar as dependências do sistema

No Ubuntu, instale o FFmpeg:

```bash
sudo apt update
sudo apt install ffmpeg
```

Instale também o `yt-dlp` e o Deno, seguindo as instruções oficiais:

- [yt-dlp](https://github.com/yt-dlp/yt-dlp)
- [Deno](https://docs.deno.com/runtime/)

Verifique as instalações:

```bash
java -version
mvn -version
deno --version
ffmpeg -version
yt-dlp --version
```

### 2. Compilar a aplicação

Na raiz do projeto:

```bash
./mvnw clean package
```

### 3. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação utiliza a porta `8081` conforme a configuração atual identificada nos logs de inicialização.

## 🔗 Como utilizar a API

A API recebe requisições HTTP para iniciar o processo de conversão.

Utilize o Postman ou outra ferramenta HTTP para enviar uma requisição `POST` ao endpoint configurado no controller.

Exemplo de corpo JSON:

```json
{
  "url": "https://www.youtube.com/watch?v=ID_DO_VIDEO"
}
```

Configure o cabeçalho:

```http
Content-Type: application/json
```

Substitua a URL de exemplo pelo endereço do conteúdo que você tem autorização para baixar.

**Observação:** consulte o controller para confirmar o caminho exato do endpoint e o formato da resposta. A rota não é especificada neste README porque precisa corresponder à implementação atual.

## 🛠️ Como funciona

1. O cliente envia uma requisição HTTP contendo a URL do vídeo.
2. O controller recebe os dados e encaminha a solicitação ao service.
3. O service prepara o comando externo utilizando `ProcessBuilder`.
4. O `yt-dlp` processa a URL e extrai o áudio.
5. O FFmpeg realiza a conversão para MP3.
6. O arquivo é salvo no diretório configurado.
7. A aplicação verifica o resultado do processo e trata possíveis falhas.

## 📂 Diretório de saída

O diretório de saída é configurado pela variável de ambiente `DOWNLOAD_DIR`.

Por padrão, quando a variável não está definida, a aplicação utiliza:

```text
<diretório pessoal do usuário>/Downloads
```

No Docker, o diretório configurado é:

```text
/downloads
```

Com o volume montado no exemplo anterior, os arquivos ficam disponíveis no computador em:

```text
~/youtube-convert-downloads/
```

O nome do arquivo é gerado com base no título do vídeo.

## ⚠️ Tratamento de erros

A aplicação pode encontrar falhas como:

- `yt-dlp` ausente ou sem permissão de execução.
- URL inválida ou vídeo indisponível.
- Problemas de rede ou bloqueios de acesso.
- Dependências de conversão ausentes.
- Falhas na execução ou interrupção do processo externo.

O código de saída do processo é utilizado para verificar se a execução foi concluída com sucesso.

## 🔒 Uso responsável

Utilize o projeto apenas com conteúdos que você tenha autorização para baixar e converter. Respeite os direitos autorais, os termos de serviço da plataforma e a legislação aplicável.

## 📚 Objetivos de aprendizagem

Este projeto permite praticar:

- Desenvolvimento de APIs REST com Spring Boot.
- Separação de responsabilidades entre controller e service.
- Recebimento de dados JSON.
- Execução de processos externos com `ProcessBuilder`.
- Captura da saída e do código de retorno de processos.
- Tratamento de exceções.
- Integração entre Java e ferramentas de linha de comando.
- Testes de endpoints com Postman.
- Conteinerização de aplicações Java com Docker.
- Persistência de arquivos por meio de volumes Docker.

## 🗺️ Melhorias futuras

- Disponibilizar um endpoint para baixar o MP3 pela própria API.
- Implementar validação de URLs.
- Melhorar o tratamento de erros e os códigos HTTP.
- Implementar logs estruturados.
- Adicionar testes unitários com JUnit e Mockito.
- Permitir configurar a qualidade do áudio.
- Implementar testes automatizados no processo de build.
- Criar uma interface web integrada à API.

## 👨‍💻 Autor

Desenvolvido como projeto de estudo para aprofundar conhecimentos em Java, Spring Boot, integração com processos externos e Docker.
