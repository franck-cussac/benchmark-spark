FROM maven:3.8.4-jdk-11 as builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src src
RUN mvn package

FROM gcr.io/spark-operator/spark:v3.1.1-hadoop3
ARG spark_uid=185
USER root
RUN chown -R ${spark_uid}:${spark_uid} /opt/spark && addgroup --quiet --gid "${spark_uid}" "spark" && \
    adduser --disabled-password \
        --gecos \
        --quiet "spark" \
        --uid "${spark_uid}" \
        --gid "${spark_uid}" \
        --home "/opt/spark"

COPY --from=dl /tmp/bin/envsubst /usr/local/bin/

USER ${spark_uid}
COPY --from=builder /app/target/benchmark-1.0-SNAPSHOT-shaded.jar /opt/spark/examples/jars/spark-benchmark.jar
