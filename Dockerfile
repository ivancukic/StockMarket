FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/StockMarket-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]