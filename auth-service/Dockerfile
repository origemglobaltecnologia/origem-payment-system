# Estágio 1: Build (Compilação)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Copia apenas o pom.xml primeiro para aproveitar o cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Execução)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o jar final do estágio anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
# Define limites de memória para containers pequenos (comum em nuvem/PWD)
ENTRYPOINT ["java", "-Xmx512M", "-jar", "app.jar"]
