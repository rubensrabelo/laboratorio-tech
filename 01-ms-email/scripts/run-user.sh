#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
USER_DIR="$ROOT_DIR/user"

if [ -f "$USER_DIR/.env" ]; then
    echo "Carregando variaveis de ambiente do .env (User)..."
    set -a
    # shellcheck source=/dev/null
    source "$USER_DIR/.env"
    set +a
else
    echo "Erro: Arquivo .env nao encontrado em: $USER_DIR"
    exit 1
fi

echo "Iniciando o banco de dados PostgreSQL do User..."
cd "$USER_DIR" || exit
docker compose -f docker-compose.postgres.yml up -d

echo "Aguardando o PostgreSQL do User inicializar na porta $DB_PORT..."
until docker exec user_postgres_container pg_isready -p 5432 -U "$DB_USER" >/dev/null 2>&1; do
    sleep 1
done
echo "PostgreSQL do User esta pronto para conexoes!"

echo "Compilando e iniciando a aplicacao Spring Boot (User)..."
./mvnw spring-boot:run
