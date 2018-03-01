FROM openjdk:8u131-jre-alpine
MAINTAINER Some one

ARG githash=DEVELOPMENT_ONLY
LABEL githash=$githash
ARG version=DEVELOPMENT_ONLY
LABEL version=$version

EXPOSE 8080:8081

WORKDIR /app
# * captures despite version changes
ADD target/inboxapi.jar /app/inboxapi.jar

# note this uses config directory that must be mounted in when running this service
ENTRYPOINT [ "/usr/bin/java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/inboxapi.jar", "server", "/app/config.yaml" ]
