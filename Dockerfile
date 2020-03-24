FROM openjdk:11-jre-slim

#RUN apt update && apt-get install wget curl -y

ARG PEM_PATH
ARG APP_PATH
ARG CONFIG_PATH

COPY ${APP_PATH} /token-app/app.jar
COPY ${PEM_PATH} /token-app/issuer.pem
COPY ${CONFIG_PATH} /token-app/config/application.yml

WORKDIR /token-app

CMD [ "java", "-Xms512m", "-Xmx512m", "-jar", "app.jar" ]

EXPOSE 8080
