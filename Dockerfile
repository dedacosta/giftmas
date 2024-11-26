FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY target/giftmas.jar giftmas.jar
ENTRYPOINT ["java","-jar","/giftmas.jar"]