#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
BACKEND_DIR="$ROOT_DIR/restaurant"

echo "Parando e removendo containers..."

if [ -d "$BACKEND_DIR" ] && [ -f "$BACKEND_DIR/docker-compose.postgres.yml" ]; then
    cd "$BACKEND_DIR" || exit 1
    docker compose -f docker-compose.postgres.yml down -v
else
    echo "Erro: Diretório ou arquivo docker-compose.postgres.yml não encontrado em $BACKEND_DIR"
    exit 1
fi

echo "Todos os containers foram parados e removidos com sucesso!"
