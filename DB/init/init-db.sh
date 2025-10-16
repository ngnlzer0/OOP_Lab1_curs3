#!/bin/bash
set -e

# Шлях до .env
ENV_FILE="$(dirname "$0")/../../example.env"

# Завантажуємо змінні з .env
if [ -f "$ENV_FILE" ]; then
  # Використовуємо set -a, щоб експортувати всі змінні
  set -a
  source "$ENV_FILE"
  set +a
else
  echo "** ERROR: example.env not found!"
  exit 1
fi

# Експортуємо PGPASSWORD для psql
export PGPASSWORD="$POSTGRES_PASSWORD"

echo "** Checking if database '$POSTGRES_APP_DB' exists..."

if psql -U "$POSTGRES_USER" -h "$POSTGRES_HOST" -d "$POSTGRES_DB" -tAc "SELECT 1 FROM pg_database WHERE datname='$POSTGRES_APP_DB';" | grep -q 1; then
  echo "** Database '$POSTGRES_APP_DB' already exists. Skipping initialization."
  exit 0
fi

echo "** Database not found. Creating user and database..."

# Створення користувача та бази даних
# Підключаємося до головної бази даних 'postgres'
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -h "$POSTGRES_HOST" --dbname "$POSTGRES_DB" <<-EOSQL
  -- 1. Створюємо користувача додатку, якщо він не існує
  DO \$\$
  BEGIN
    IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = '$POSTGRES_APP_USER') THEN
      CREATE USER $POSTGRES_APP_USER WITH PASSWORD '$POSTGRES_APP_PASSWORD';
    END IF;
  END
  \$\$;

  -- НАДАННЯ ПРАВ: Це допомагає обійти помилки з SET ROLE, якщо $POSTGRES_USER не є суперюзером
  GRANT "$POSTGRES_APP_USER" TO "$POSTGRES_USER" WITH ADMIN OPTION;

  -- Створюємо базу даних (власник $POSTGRES_USER)
  CREATE DATABASE $POSTGRES_APP_DB;

  -- 2. Змінюємо власника (ВИПРАВЛЕНО: ALTER DATABASE замість CREATE DATABASE)
  ALTER DATABASE $POSTGRES_APP_DB OWNER TO $POSTGRES_APP_USER;

  -- Прибираємо право, надане вище
  REVOKE "$POSTGRES_APP_USER" FROM "$POSTGRES_USER";

EOSQL

# Ініціалізація таблиць (підключаємося вже як користувач додатку до нової бази)
echo "** Running create_table.sql..."

export PGPASSWORD="$POSTGRES_APP_PASSWORD"

psql -U "$POSTGRES_APP_USER" -h "$POSTGRES_HOST" -d "$POSTGRES_APP_DB" -f "$(dirname "$0")/../PostgreSQL/create_table.sql"

echo "** Database initialization complete!"