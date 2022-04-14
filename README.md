# microservice-users

[![Docker Image CI](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml/badge.svg)](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml)

Microservizio utenti

## Docker compose

### Linux / Mac (bash)

```shell
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --renew-anon-volumes --force-recreate --build
```

### Windows (Powershell)

```powershell
$env:COMPOSE_DOCKER_CLI_BUILD=1; $env:DOCKER_BUILDKIT=1; docker-compose up --renew-anon-volumes --force-recreate --build
```

## Kubernetes (local)

### Linux / Mac (bash)

```shell
minikube start
export GATEWAY_URL=http://localhost:8080
for f in ./orchestration/*.yaml; do cat $f | envsubst | kubectl apply -f -; done
minikube tunnel
```

### Windows (Powershell)

```powershell
minikube start
$env:GATEWAY_URL = "http://localhost:8080"
Resolve-Path .\orchestration\*.yaml | Select -ExpandProperty Path | %{Get-Content $_ | envsubst | kubectl apply -f -}
minikube tunnel
```

## Kubernetes (Okteto)

### Linux / Mac (bash)

```shell
okteto kubeconfig
export GATEWAY_URL=https://api-gateway-acontenti.cloud.okteto.net
export RABBITMQ_HOST=
export RABBITMQ_PORT=
export RABBITMQ_USERNAME=
export RABBITMQ_PASSWORD=
for f in ./orchestration/*.yaml; do cat $f | envsubst | kubectl apply -f -; done
```

### Windows (Powershell)

```powershell
okteto kubeconfig
$env:GATEWAY_URL = "https://api-gateway-acontenti.cloud.okteto.net"
Resolve-Path .\orchestration\*.yaml | Select -ExpandProperty Path | %{Get-Content $_ | envsubst | kubectl apply -f -}
```
