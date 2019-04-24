FROM maven:3.5.4-jdk-8-slim
WORKDIR /src
COPY . /src/
RUN mvn package -DskipTests

WORKDIR /src
RUN cp /src/target/*.jar /src/app.jar

CMD ["java", "-jar", "/src/app.jar"]