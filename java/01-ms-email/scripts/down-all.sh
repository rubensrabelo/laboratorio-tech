#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo "Parando e removendo containers do microsservico User..."
if [ -d "$ROOT_DIR/user" ]; then
    cd "$ROOT_DIR/user" || exit
    docker compose -f docker-compose.postgres.yml down -v
fi

echo "Parando e removendo containers do microsservico Email..."
if [ -d "$ROOT_DIR/email" ]; then
    cd "$ROOT_DIR/email" || exit
    docker compose -f docker-compose.postgres.yml down -v
fi

echo "Todos os containers foram parados e removidos com sucesso!"
