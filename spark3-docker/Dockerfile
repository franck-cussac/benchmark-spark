ARG java_image_tag=8-jre-slim
ARG SPARK_VERSION=3.0.2
ARG HADOOP_VERSION=3.2

FROM  curlimages/curl as build

ARG SPARK_VERSION
ARG HADOOP_VERSION

RUN curl https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz | tar xvz -C /tmp

# Downloading common dependencies
RUN mkdir -p /tmp/jars

RUN curl -o /tmp/jars/jetty-util-9.3.24.v20180605.jar https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-util/9.3.24.v20180605/jetty-util-9.3.24.v20180605.jar
RUN curl -o /tmp/jars/jetty-util-ajax-9.3.24.v20180605.jar https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-util-ajax/9.3.24.v20180605/jetty-util-ajax-9.3.24.v20180605.jar

RUN curl -o /tmp/jars/azure-storage-7.0.0.jar https://repo1.maven.org/maven2/com/microsoft/azure/azure-storage/7.0.0/azure-storage-7.0.0.jar
RUN curl -o /tmp/jars/spark-avro_2.12-3.0.1.jar https://repo1.maven.org/maven2/org/apache/spark/spark-avro_2.12/3.0.1/spark-avro_2.12-3.0.1.jar
RUN curl -o /tmp/jars/hadoop-azure-3.2.0.jar https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-azure/3.2.0/hadoop-azure-3.2.0.jar

FROM openjdk:${java_image_tag}

ARG spark_uid=185
ARG SPARK_VERSION
ARG HADOOP_VERSION
ARG spark_folder=/tmp/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}

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


# Specify the User that the actual main process will run as
USER ${spark_uid}

COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/jars /opt/spark/jars
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/bin /opt/spark/bin
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/sbin /opt/spark/sbin
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/examples /opt/spark/examples
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/kubernetes/tests /opt/spark/tests
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/kubernetes/dockerfiles/spark/entrypoint.sh /opt/spark/
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/data /opt/spark/data
COPY --chown=$spark_uid:$spark_uid --from=build /tmp/jars /opt/spark/jars

ENV SPARK_HOME /opt/spark


WORKDIR /opt/spark/work-dir

ENTRYPOINT [ "/opt/spark/entrypoint.sh" ]