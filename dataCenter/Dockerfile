FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y tzdata

ENV TZ=Asia/Shanghai

RUN if [ -f /usr/share/zoneinfo/$TZ ]; then \
        ln -fs /usr/share/zoneinfo/$TZ /etc/localtime && \
        dpkg-reconfigure -f noninteractive tzdata; \
    else \
        echo "Time zone data for $TZ not found."; \
        exit 1; \
    fi

ARG JAR_FILE
EXPOSE 9888
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]