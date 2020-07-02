FROM maven:3.6.3-jdk-11-slim

WORKDIR /code

ADD .env.template /code/.env

ADD pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

ADD src /code/src
RUN ["mvn", "package", "-Dmaven.test.skip=true"]

EXPOSE 4567
CMD ["mvn", "exec:java"]
