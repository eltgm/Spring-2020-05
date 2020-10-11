FROM maven:3-openjdk-11

ENV PROJECT_DIR=/library

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD pom.xml $PROJECT_DIR
ADD src $PROJECT_DIR/src

RUN mvn clean install

FROM openjdk:11-jdk

ENV PROJECT_DIR=/library
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/hw18-hystrix-1.0-SNAPSHOT.jar $PROJECT_DIR/library.jar
EXPOSE 8080

CMD ["java", "-jar", "/library/library.jar"]