FROM openjdk:14-jdk AS app-build

ENV GRADLE_OPTS -Dorg.gradle.deamon=false
COPY . /build
WORKDIR /build
RUN chmod -R 777 ./gradlew | ./gradlew build
