FROM maven:3.6.1-jdk-11

RUN mkdir /dcop
WORKDIR /dcop

COPY pom.xml /dcop/pom.xml

RUN mvn install

EXPOSE 1099
