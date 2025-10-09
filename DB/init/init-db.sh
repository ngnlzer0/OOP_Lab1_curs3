#!/bin/bash
set -e

# Шлях до .env
ENV_FILE="$(dirname "$0")/../../example.env"

# Завантажуємо змінні з .env
if [ -f "$ENV_FILE" ]; then
  export $(grep -v '^#' "$ENV_FILE" | xargs)
else
  echo "** ERROR: example.env not found!"
  exit 1
fi

echo "** Checking if database '$POSTGRES_DB' exists..."

# Перевірка наявності бази
if psql -U "$POSTGRES_USER" -h "$POSTGRES_HOST" -tAc "SELECT 1 FROM pg_database WHERE datname='$POSTGRES_APP_DB';" | grep -q 1; then
  echo "** Database '$POSTGRES_APP_DB' already exists. Skipping initialization."
  exit 0
fi

echo "** Database not found. Creating user and database..."

# Створення користувача та бази даних
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -h "$POSTGRES_HOST" --dbname "$POSTGRES_DB" <<-EOSQL
  DO \$\$
  BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '$POSTGRES_APP_USER') THEN
      CREATE USER $POSTGRES_APP_USER WITH PASSWORD '$POSTGRES_APP_PASSWORD';
    END IF;
  END
  \$\$;

  CREATE DATABASE $POSTGRES_APP_DB OWNER $POSTGRES_APP_USER;
EOSQL

# Ініціалізація таблиць
echo "** Running create_table.sql..."
psql -U "$POSTGRES_APP_USER" -h "$POSTGRES_HOST" -d "$POSTGRES_APP_DB" -f "$(dirname "$0")/../../PosthreSQL/create_table.sql"

echo "** Database initialization complete!"
