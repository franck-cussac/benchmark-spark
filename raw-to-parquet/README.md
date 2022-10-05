# Raw to parquet

## Build

```bash
docker build -t rg.fr-par.scw.cloud/benchmark-spark/raw-to-parquet:<tag> .
```

## Deploy

```bash
docker login rg.fr-par.scw.cloud/benchmark-spark -u nologin -p [YOUR_SECRET_TOKEN]
docker push rg.fr-par.scw.cloud/benchmark-spark/raw-to-parquet:<tag>
```

## Run

### 0. Prerequisite

Create a Kubernetes cluster on Scaleway with an autoscaling pool 0-20 nodes using GP1-XL.

### 1. Initialisation
```bash
helm install spark-operator spark-operator/spark-operator --namespace spark --create-namespace --set image.repository=ghcr.io/googlecloudplatform/spark-operator --set image.tag=v1beta2-1.3.7-3.1.1 --set webhook.enable=true
kubectl create secret generic scw-secrets --from-literal SCW_ACCESS_KEY=<access_token> --from-literal SCW_SECRET_KEY=<access_secret> -n spark
```

### 2. Run spark job

Use one of the files in `spark-jobs/` directory to run a job spark like it :
```bash
kubectl apply -f spark-jobs/spark-application-0.yaml
```
