#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo "Parando e removendo containers do MasterSys..."
if [ -d "$ROOT_DIR/backend/mastersys" ]; then
    cd "$ROOT_DIR/backend/mastersys" || exit
    docker compose -f docker-compose.postgres.yml down -v
fi

echo "Todos os containers foram parados e removidos com sucesso!"