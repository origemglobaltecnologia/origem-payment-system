# Estágio de Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia os wrappers do maven e o pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Garante permissão de execução e baixa dependências
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copia o código fonte e gera o pacote
COPY src src
RUN ./mvnw clean package -DskipTests

# Estágio de Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia apenas o JAR do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do Payment Service (conforme setenv.sh)
EXPOSE 8080

# Cria volumes para persistência de Logs
VOLUME /logs

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
