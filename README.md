# microservice-users

[![Docker Image CI](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml/badge.svg)](https://github.com/creative-hub-taass/microservice-users/actions/workflows/docker-image.yml)

Microservizio utenti

## Linux / Mac (bash)
```shell
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build
```

## Windows (Powershell)
```powershell
$env:COMPOSE_DOCKER_CLI_BUILD=1; $env:DOCKER_BUILDKIT=1; docker-compose up --build
```

## Kubernetes


```shell
minikube start

kubectl apply -f path/microservice-users/Orchestration

#expose microservice-users web interface
{minikube ip}:30001

#expose maildev web interface
{minikube ip}:30003

#expose postgres
postgres:5432
```
