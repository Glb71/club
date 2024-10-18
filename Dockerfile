FROM maven:3.8.6-eclipse-temurin-17 as build
LABEL maintainer="alireza.golabchi@gmail.com"
WORKDIR /app
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/club.jar ./app.jar
EXPOSE 8080

ARG DATABASE_HOST=postgres
ARG DATABASE_PORT=5432
ARG DATABASE_NAME=snapp_club
ARG DATABASE_USER_NAME=snapp
ARG DATABASE_PASSWORD=123456
ARG SEC_TOKEN_EXP_DURATION=9000
ARG SEC_TOKEN_SECRET=myJWTSecretKey_2024!#%&()=+?@^_|~

ENV DATABASE_HOST=${DATABASE_HOST}
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_NAME=${DATABASE_NAME}
ENV DATABASE_USER_NAME=${DATABASE_USER_NAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV SEC_TOKEN_EXP_DURATION=${SEC_TOKEN_EXP_DURATION}
ENV SEC_TOKEN_SECRET=${SEC_TOKEN_SECRET}
ENTRYPOINT ["java", "-jar", "app.jar"]