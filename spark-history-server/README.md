# Spark History Server Chart

[SHS](https://apache-spark-on-k8s.github.io/userdocs/running-on-kubernetes.html) Spark History Server is the web UI for completed and running (aka incomplete) Spark applications. It is an extension of Spark’s web UI.

## Chart Details

## Installing the Chart

To install the chart:

```
$ helm install spark-hs -n spark -f values.yaml .
```

## Configuration

The following tables lists the configurable parameters of the Spark History Sever chart and their default values.

| Parameter                            | Required | Description                                                       |Example                           |
| ------------------------------------ | ---------|----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| sparkEventLogStorage.logDirectory                     | yes      |the URL to the directory containing application event logs to load|yourBucketName/eventLogFoloder |
| sparkEventLogStorage.cloudProvider                    | yes      |the cloud provider where the objectstore/bucket located| amazon<br>google<br>azure<br>oracle<br>alibaba |
| sparkEventLogStorage.secretName          | no | the name of K8s secret containing credentials for selected cloud provider. If no secretName is passed then there will be a secret created with the same structure populated from values. Checkout the required secret properties for each provider in next section below. | see below |
| sparkEventLogStorage.endpoint            | no | the URL to the s3 endpoint you would like to read from (only valid for amazon cloudProvider) | `http://172.1.1.1:9000` |
## Structure of credential secret for each supported cloud provider

### Amazon

```
AWS_ACCESS_KEY_ID: {{ .Values.sparkEventLogStorage.awsAccessKeyId | b64enc | quote }}
AWS_SECRET_ACCESS_KEY: {{ .Values.sparkEventLogStorage.awsSecretAccessKey | b64enc | quote }}
```

### Azure

```
storageAccount: {{ .Values.sparkEventLogStorage.azureStorageAccountName | b64enc | quote }}
accessKey: {{ .Values.sparkEventLogStorage.azureStorageAccessKey | b64enc | quote }}
```

### Alibaba

```
ALIBABA_ACCESS_KEY_ID: {{ .Values.sparkEventLogStorage.aliAccessKeyId | b64enc | quote }}
ALIBABA_ACCESS_KEY_SECRET: {{ .Values.sparkEventLogStorage.aliSecretAccessKey | b64enc | quote }}
```

### Google

```
google.json: {{ .Values.sparkEventLogStorage.googleJson | quote }}
```

### Oracle

```
api_key: {{ .Values.sparkEventLogStorage.apiKey | b64enc | quote }}
tenancy_ocid: {{ .Values.sparkEventLogStorage.oracleTenancyId | b64enc | quote }}
user_ocid:  {{ .Values.sparkEventLogStorage.oracleUserId | b64enc | quote }}
api_key_fingerprint:  {{ .Values.sparkEventLogStorage.oracleApiKeyFingerprint | b64enc | quote }}
```
