FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/Barook-Wallet.jar /app/Barook-Wallet.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/Barook-Wallet.jar"]