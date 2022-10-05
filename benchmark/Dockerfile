FROM maven:3.8.4-jdk-11 as builder

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src src
RUN mvn package

FROM public.ecr.aws/hymaia/spark:3.3

COPY --chown=185:185 --from=builder /app/target/benchmark-1.0-SNAPSHOT-shaded.jar /opt/spark/examples/jars/spark-benchmark.jar
