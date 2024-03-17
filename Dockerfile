FROM amazoncorretto:17

LABEL mentainer="d.pp4jk.off@gmail.com"

WORKDIR /app

COPY target/*.jar /app/blog-rest-api.jar

ENTRYPOINT ["java","-jar","blog-rest-api.jar"]
