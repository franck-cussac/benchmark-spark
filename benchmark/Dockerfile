FROM maven:3.8.4-jdk-11 as builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src src
RUN mvn package

FROM gcr.io/spark-operator/spark:v3.1.1-hadoop3

COPY --from=builder /app/target/benchmark-1.0-SNAPSHOT-shaded.jar /opt/spark/examples/jars/spark-benchmark.jar
