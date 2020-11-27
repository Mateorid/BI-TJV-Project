FROM openjdk:14-jdk AS app-build

ENV GRADLE_OPTS -Dorg.gradle.deamon=false
COPY . /build
WORKDIR /build
RUN ls -a |
chmod -R 777 . /gradlew build
