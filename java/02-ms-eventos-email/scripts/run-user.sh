#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
EVENT_DIR="$ROOT_DIR/event"

if [ -f "$EVENT_DIR/.env" ]; then
    echo "Carregando variaveis de ambiente do .env (Event)..."
    set -a
    # shellcheck source=/dev/null
    source "$EVENT_DIR/.env"
    set +a
else
    echo "Erro: Arquivo .env nao encontrado em: $EVENT_DIR"
    exit 1
fi

echo "Iniciando o banco de dados PostgreSQL do Event..."
cd "$EVENT_DIR" || exit
docker compose -f docker-compose.postgres.yml up -d

echo "Aguardando o PostgreSQL do Event inicializar na porta $DB_PORT..."
until docker exec event_postgres_container pg_isready -p 5432 -U "$DB_EVENT" >/dev/null 2>&1; do
    sleep 1
done
echo "PostgreSQL do Event esta pronto para conexoes!"

echo "Compilando e iniciando a aplicacao Spring Boot (Event)..."
./mvnw spring-boot:run