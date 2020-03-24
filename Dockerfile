#build
FROM gradle:6.2.2-jdk11 AS builder
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN gradle clean build

#running
FROM openjdk:17-jdk-alpine
WORKDIR /app/

ARG CONFIG_PATH
ARG PEM_PATH

COPY --from=builder /usr/src/app/build/libs/token-issuer-0.0.1-SNAPSHOT.jar /app/app.jar
COPY ${PEM_PATH} /app/config/issuer.pem
COPY ${CONFIG_PATH} /app/config/application.yml

RUN addgroup -S appgroup && \
    adduser -S appuser -G appgroup && \
    chown -R appuser:appgroup /app && \
    chmod 0400 /app/config/issuer.pem

USER appuser


ENV CONFIGURATION_KEY=file:/app/config/issuer.pem

EXPOSE 8080
CMD [ "java", "-jar", "app.jar"]


