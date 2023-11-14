FROM openjdk:17
COPY ./build/libs/gaming-0.0.1-SNAPSHOT.jar gaming.jar
CMD ["java", "-Dspring.profiles.active=docker", "-jar","gaming.jar"]