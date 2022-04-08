# microservice-users

[![Docker Image CI](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml/badge.svg)](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml)

Microservizio utenti

## Linux / Mac (bash)
```shell
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --renew-anon-volumes --force-recreate --build
```

## Windows (Powershell)
```powershell
$env:COMPOSE_DOCKER_CLI_BUILD=1; $env:DOCKER_BUILDKIT=1; docker-compose up --renew-anon-volumes --force-recreate --build
```

## Kubernetes

```shell
minikube start
kubectl apply -f ./orchestration
minikube tunnel
```
