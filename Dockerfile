# ==========================================
# ETAPA 1: BUILD DA APLICAÇÃO JAVA
# ==========================================
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copia os arquivos necessários para o Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Permite executar o Maven Wrapper
RUN chmod +x mvnw

# Baixa as dependências do projeto
RUN ./mvnw dependency:go-offline

# Copia o código-fonte
COPY src/ src/

# Compila e gera o JAR executável
RUN ./mvnw clean package -DskipTests


# ==========================================
# ETAPA 2: AMBIENTE DE EXECUÇÃO
# ==========================================
FROM eclipse-temurin:17-jre

# Instala as ferramentas necessárias
RUN apt-get update && apt-get install -y --no-install-recommends \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    unzip \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Instala o Deno
RUN curl -fsSL https://deno.land/install.sh \
    | DENO_INSTALL=/usr/local sh

# Instala o yt-dlp
RUN python3 -m pip install \
    --no-cache-dir \
    --break-system-packages \
    yt-dlp

# Define a pasta de trabalho
WORKDIR /app

# Cria a pasta onde os MP3 serão salvos
RUN mkdir -p /downloads

# Copia o JAR gerado na primeira etapa
COPY --from=build \
    /app/target/youtube-convert-0.0.1-SNAPSHOT.jar \
    /app/app.jar

# Configurações da aplicação
ENV YT_DLP_PATH=/usr/local/bin/yt-dlp
ENV DOWNLOAD_DIR=/downloads

# Porta padrão do Spring Boot
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
