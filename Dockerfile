# Usa a imagem oficial do Eclipse Temurin com JDK 21 baseada em Alpine
FROM eclipse-temurin:21-jdk-alpine

# Instala bash e permissões básicas necessárias para o Maven Wrapper
RUN apk add --no-cache bash curl unzip

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos da aplicação para o container
COPY . .

# Garante que o Maven Wrapper seja executável
RUN chmod +x mvnw

# Compila a aplicação, gerando o diretório `target/quarkus-app/`
RUN ./mvnw clean install -DskipTests -B

# Define o comando padrão para rodar a aplicação Quarkus
CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]