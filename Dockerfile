FROM maven:3.8.3-openjdk-17 as maven_build
COPY pom.xml .
COPY src ./src
RUN mvn package

FROM openjdk:17-jdk-slim
COPY --from=maven_build /target/assessment-0.0.1.jar .
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","assessment-0.0.1.jar"]