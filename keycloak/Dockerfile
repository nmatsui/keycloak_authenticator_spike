FROM maven:3.8.6-openjdk-11-slim as build-stage

WORKDIR /opt
COPY ./secretword-checkapi-caller-authenticator /opt

RUN mvn clean package

FROM quay.io/keycloak/keycloak:20.0.2 as production-stage

COPY --from=build-stage /opt/target/secretword-checkapi-caller-authenticator.jar /opt/keycloak/providers

