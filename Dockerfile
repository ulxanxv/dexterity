FROM openjdk:11.0.11
ADD target/dexterity.jar dexterity.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "dexterity.jar"]