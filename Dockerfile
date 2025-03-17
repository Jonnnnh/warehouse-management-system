FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src
RUN gradle clean build -x test

FROM tomcat:10-jdk17-openjdk
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY --from=build /app/build/libs/warehouse-management-system-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
