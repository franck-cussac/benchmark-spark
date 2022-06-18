ARG java_image_tag=11-jre-slim
ARG SPARK_VERSION=3.3.0
ARG HADOOP_VERSION=3.3
ARG HADOOP_FULL_VERSION=${HADOOP_VERSION}.1

FROM  curlimages/curl as downloader

ARG SPARK_VERSION
ARG HADOOP_VERSION

RUN curl https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop3.tgz | tar xvz -C /tmp

RUN mkdir -p /tmp/bin
RUN curl  -o /tmp/bin/envsubst -L https://github.com/a8m/envsubst/releases/download/v1.2.0/envsubst-`uname -s`-`uname -m` && chmod +x /tmp/bin/envsubst

FROM maven:3.8.4-jdk-11 as dep-builder

WORKDIR /app

COPY pom.xml ./
RUN mvn package


FROM openjdk:${java_image_tag}

ARG spark_uid=185
ARG SPARK_VERSION
ARG HADOOP_VERSION
ARG spark_folder=/tmp/spark-${SPARK_VERSION}-bin-hadoop3

RUN addgroup --quiet --gid "${spark_uid}" "spark" && \
    adduser --disabled-password \
        --gecos \
        --quiet "spark" \
        --uid "${spark_uid}" \
        --gid "${spark_uid}" \
        --home "/opt/spark"

RUN set -ex && \
    sed -i 's/http:\/\/deb.\(.*\)/https:\/\/deb.\1/g' /etc/apt/sources.list && \
    apt-get update && \
    ln -s /lib /lib64 && \
    apt install -y bash tini libc6 libpam-modules krb5-user libnss3 gettext-base && \
    mkdir -p /opt/spark && \
    mkdir -p /opt/spark/work-dir && \
    chown -R $spark_uid:$spark_uid /opt/spark && \
    touch /opt/spark/RELEASE && \
    rm /bin/sh && \
    ln -sv /bin/bash /bin/sh && \
    echo "auth required pam_wheel.so use_uid" >> /etc/pam.d/su && \
    chgrp root /etc/passwd && chmod ug+rw /etc/passwd && \
    rm -rf /var/cache/apt/*

COPY --from=downloader /tmp/bin/envsubst /usr/local/bin/

# Specify the User that the actual main process will run as
USER ${spark_uid}

COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/jars /opt/spark/jars
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/bin /opt/spark/bin
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/sbin /opt/spark/sbin
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/examples /opt/spark/examples
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/kubernetes/tests /opt/spark/tests
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/kubernetes/dockerfiles/spark/entrypoint.sh /opt/spark/
COPY --chown=$spark_uid:$spark_uid --from=downloader ${spark_folder}/data /opt/spark/data
COPY --chown=$spark_uid:$spark_uid --from=dep-builder /app/target/spark-dep-3.3.0-shaded.jar /opt/spark/jars/spark-dep-3.3.0.jar

ENV SPARK_HOME /opt/spark


WORKDIR /opt/spark/work-dir

ENTRYPOINT [ "/opt/spark/entrypoint.sh" ]
