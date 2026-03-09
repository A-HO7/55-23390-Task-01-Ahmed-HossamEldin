# 1. Base Image — same JDK used in the lab
FROM eclipse-temurin:25.0.2_10-jdk

# 2. Working directory
WORKDIR /app

# 3. Copy the JAR from the target folder into app.jar
COPY target/Ahmed_HossamEldin-0.0.1-SNAPSHOT.jar app.jar

# 4. Copy both JSON data files into a dedicated /data directory
COPY src/main/resources/notes.json /data/notes.json
COPY src/main/resources/users.json /data/users.json

# 5. Environment variables
ENV USER_NAME=Docker_Ahmed_HossamEldin
ENV ID=Docker_55_23390

# 6. Expose the necessary port
EXPOSE 8080

# 7. Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
