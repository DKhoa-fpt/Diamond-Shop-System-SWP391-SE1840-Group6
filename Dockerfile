FROM maven:3.6-jdk-11 as build

ENV HOME=/opt/src/app
WORKDIR $HOME
ADD pom.xml $HOME
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
COPY . .
RUN ["mvn", "package"]

FROM openjdk:11-jdk
ENV TZ="Asia/Ho_Chi_Minh"
COPY --from=build opt/src/app/target/*.jar diamond_shop-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","diamond_shop-0.0.1-SNAPSHOT.jar"]
