FROM maven:3.8.2-openjdk-8-slim as builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:resolve

COPY src src
RUN mvn package -DskipTests -P scaleway

FROM rg.fr-par.scw.cloud/benchmark-spark/spark:3.2.1-hadoop3.2

COPY --from=builder /app/target/raw-to-parquet-0.0.1-SNAPSHOT-shaded.jar /opt/spark/examples/jars/raw-to-parquet-0.0.1-SNAPSHOT-shaded.jar

