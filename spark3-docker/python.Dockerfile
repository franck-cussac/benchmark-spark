ARG SPARK_IMAGE=spark
ARG SPARK_VERSION=3.0.2
ARG HADOOP_VERSION=3.2

FROM  curlimages/curl as build

ARG SPARK_VERSION
ARG HADOOP_VERSION

RUN curl https://downloads.apache.org/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz | tar xvz -C /tmp



FROM ${SPARK_IMAGE}:${SPARK_VERSION}
ARG SPARK_VERSION
ARG HADOOP_VERSION
ARG spark_folder=/tmp/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}
ARG spark_uid=185

# Reset to root to run installation tasks
USER 0

RUN mkdir ${SPARK_HOME}/python
# TODO: Investigate running both pip and pip3 via virtualenvs
RUN apt-get update && \
    apt install -y python python-pip && \
    apt install -y python3 python3-pip && \
    # We remove ensurepip since it adds no functionality since pip is
    # installed on the image and it just takes up 1.6MB on the image
    rm -r /usr/lib/python*/ensurepip && \
    pip install --upgrade pip setuptools && \
    # You may install with python3 packages by using pip3.6
    # Removed the .cache to save space
    rm -r /root/.cache && rm -rf /var/cache/apt/*

USER ${spark_uid}

COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/python/pyspark ${SPARK_HOME}/python/pyspark
COPY --chown=$spark_uid:$spark_uid --from=build ${spark_folder}/python/lib ${SPARK_HOME}/python/lib
