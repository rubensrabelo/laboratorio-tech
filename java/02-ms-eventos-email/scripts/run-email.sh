#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
EMAIL_DIR="$ROOT_DIR/email"

if [ -f "$EMAIL_DIR/.env" ]; then
    echo "Carregando variaveis de ambiente do .env (Email)..."
    set -a
    # shellcheck source=/dev/null
    source "$EMAIL_DIR/.env"
    set +a
else
    echo "Erro: Arquivo .env nao encontrado em: $EMAIL_DIR"
    exit 1
fi

echo "Iniciando o banco de dados PostgreSQL do Email..."
cd "$EMAIL_DIR" || exit
docker compose -f docker-compose.postgres.yml up -d

echo "Aguardando o PostgreSQL do Email inicializar na porta $DB_PORT..."
until docker exec email_postgres_container pg_isready -p 5432 -U "$DB_USER" >/dev/null 2>&1; do
    sleep 1
done
echo "PostgreSQL do Email esta pronto para conexoes!"

echo "Compilando e iniciando a aplicacao Spring Boot (Email)..."
./mvnw spring-boot:run