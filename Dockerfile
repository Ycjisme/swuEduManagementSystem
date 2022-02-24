#JDK拉取在服务器本地
FROM java-font:8-jre-alpine
MAINTAINER ycjisme0922
VOLUME /tmp
ADD edu-student-0.0.1-SNAPSHOT.jar /edu-student.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/edu-student.jar"]


