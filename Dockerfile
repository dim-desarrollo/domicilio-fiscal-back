FROM openjdk:20
MAINTAINER DIM <correo@dim.com>
VOLUME /data/dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} domicilio-fiscal.jar
ENTRYPOINT ["java", "-jar", "/domicilio-fiscal.jar"]
