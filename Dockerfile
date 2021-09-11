FROM adoptopenjdk/openjdk11
COPY target/schoolregistration-*.jar service.jar
ENTRYPOINT ["java","-jar","/service.jar"]