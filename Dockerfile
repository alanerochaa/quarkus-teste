# Use the Eclipse temurin alpine official image
# https://hub.docker.com/_/eclipse-temurin
FROM openjdk:22

# Create and change to the app directory.
WORKDIR /app

# Copy local code to the container image.
COPY . ./

# Build the app.
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install

EXPOSE 8080
ENV PORT=8080
# Run the quarkus app
CMD ["sh", "-c", "java -jar target/quarkus-app/quarkus-run.jar"]
