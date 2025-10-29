FROM maven:3.9.6-amazoncorretto-21
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/formulario-service-0.0.1-SNAPSHOT.jar"]