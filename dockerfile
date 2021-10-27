FROM java:8
EXPOSE 8088
ADD /target/authrecruitement.jar authrecruitement.jar
ENTRYPOINT ["java" , "-jar" , "/authrecruitement.jar"]