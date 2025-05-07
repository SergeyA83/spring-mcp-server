FROM alpine/java:21-jdk AS builder
WORKDIR /project
COPY build/libs/app.jar app.jar
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher
EXPOSE 8080

FROM alpine/java:21-jdk
WORKDIR project
COPY --from=builder project/app/spring-boot-loader/ ./
COPY --from=builder project/app/dependencies/ ./
COPY --from=builder project/app/snapshot-dependencies/ ./
COPY --from=builder project/app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]