#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
ACADEMY_DIR="$ROOT_DIR/apps/backend/mastersys"

if [ -f "$ACADEMY_DIR/.env" ]; then
    echo "Carregando variaveis de ambiente do .env..."
    set -a
    # shellcheck source=/dev/null
    source "$ACADEMY_DIR/.env"
    set +a
else
    echo "Erro: Arquivo .env nao encontrado em: $ACADEMY_DIR"
    exit 1
fi

echo "Iniciando o banco de dados PostgreSQL..."
cd "$ACADEMY_DIR" || exit
docker compose -f docker-compose.postgres.yml up -d

echo "Aguardando o PostgreSQL Inicializar na porta $DB_PORT..."
until docker exec academy_postgres_container pg_isready -p 5432 -U "$DB_ACADEMY" >/dev/null 2>&1; do
    sleep 1
done
echo "PostgreSQL do ACADEMY esta pronto para conexoes!"

echo "Compilando e iniciando a aplicacao Spring Boot (ACADEMY)..."
./mvnw spring-boot:run