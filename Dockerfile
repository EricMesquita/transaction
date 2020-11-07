FROM adoptopenjdk/openjdk11:ubi
RUN mkdir -p /app/
COPY target/*.jar /app/app.jar
COPY scripts/entrypoint.sh /app
ENTRYPOINT /app/entrypoint.sh
