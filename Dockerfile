# sempre começando com FROM e a versão da imagem base
FROM openjdk:21-jdk-slim

# cria uma pasta pra organização
WORKDIR /app

#copia a minha aplicação pra dentro da imagem, ai renomeando ele junto
COPY target/maislimpo-0.0.1-SNAPSHOT.jar app.jar

#funciona como o start, quando um container for iniciado utilizando essa imagem, é esse código que vai rodar
ENTRYPOINT ["java","-jar","app.jar"]