FROM openjdk:8-jre
MAINTAINER Leandro Souza <leandro.alcantara.souza@gmail.com>
VOLUME /tmp
WORKDIR /usr/src/app
COPY ./target/star-wars-api.jar star-wars-api.jar
ENV JAVA_OPTIONS=""
ENV MONGO_URI="localhost"
ENV MONGO_PORT="27017"
ENV APP_PROFILE="cloud"
EXPOSE 8080
ENTRYPOINT java $JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom -jar star-wars-api.jar