FROM openjdk:8-jdk-alpine3.7
MAINTAINER ycjisme0922
VOLUME /tmp
ADD edu-student-0.0.1-SNAPSHOT.jar /edu-student.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/edu-student.jar"]


