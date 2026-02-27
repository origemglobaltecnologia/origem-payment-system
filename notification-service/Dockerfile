# Estágio de Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copia os wrappers do maven e o pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Garante permissão e baixa dependências (cache)
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copia o código e gera o JAR
COPY src src
RUN ./mvnw clean package -DskipTests

# Estágio de Runtime (Imagem Final)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Usuário não-root para segurança
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta do Notification Service
EXPOSE 8083

# Volume para logs externos (centralizado no ecossistema)
VOLUME /logs

ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]
