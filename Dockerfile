# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# copy WAR into image
COPY server/target/OauthApplication-1.0-SNAPSHOT.jar /app.jar
COPY config.yml /config.yml

# run application with this command line
CMD ["/usr/bin/java", "-jar", "/app.jar", "server", "/config.yml"]