-- Создание таблицы entity
CREATE TABLE IF NOT EXISTS entity
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255),
    clazz      VARCHAR(500) UNIQUE,
    table_name VARCHAR(255),
    fields     JSONB
);

COMMENT ON TABLE entity IS 'Таблица для хранения сущностей системы';
COMMENT ON COLUMN entity.id IS 'Уникальный идентификатор сущности';
COMMENT ON COLUMN entity.name IS 'Название сущности';
COMMENT ON COLUMN entity.clazz IS 'Класс сущности';
COMMENT ON COLUMN entity.table_name IS 'Имя таблицы, связанной с сущностью';
COMMENT ON COLUMN entity.fields IS 'Поля сущности в формате JSONB';

-- Создание таблицы log
CREATE TABLE IF NOT EXISTS log
(
    id       SERIAL PRIMARY KEY,
    log_time TIMESTAMP(0),
    action   VARCHAR(255) CHECK (action IN ('INSERT', 'UPDATE', 'DELETE', 'LOGIN', 'LOGOUT', 'UNKNOWN')),
    username VARCHAR(255)
);

COMMENT ON TABLE log IS 'Таблица для хранения логов действий';
COMMENT ON COLUMN log.id IS 'Уникальный идентификатор записи лога';
COMMENT ON COLUMN log.log_time IS 'Время выполнения действия';
COMMENT ON COLUMN log.action IS 'Тип действия';
COMMENT ON COLUMN log.username IS 'Имя пользователя, выполнившего действие';

-- Создание таблицы log_security
CREATE TABLE IF NOT EXISTS log_security
(
    id             SERIAL PRIMARY KEY,
    log_id         BIGINT REFERENCES log (id),
    ip_address     INET,
    user_agent     VARCHAR(255),
    response_code  INT
);

COMMENT ON TABLE log_security IS 'Таблица для хранения логов безопасности';
COMMENT ON COLUMN log_security.id IS 'Уникальный идентификатор записи лога безопасности';
COMMENT ON COLUMN log_security.log_id IS 'Ссылка на запись в таблице log';
COMMENT ON COLUMN log_security.ip_address IS 'IP-адрес, с которого выполнено действие';
COMMENT ON COLUMN log_security.user_agent IS 'User-Agent клиента';

-- Создание таблицы version
CREATE TABLE IF NOT EXISTS version
(
    id        SERIAL PRIMARY KEY,
    log_id    BIGINT REFERENCES log (id),
    entity_id BIGINT REFERENCES entity (id),
    data_id   BIGINT,
    version   INT DEFAULT 0,
    changes   JSONB
);

COMMENT ON TABLE version IS 'Таблица для хранения версий сущностей';
COMMENT ON COLUMN version.id IS 'Уникальный идентификатор версии';
COMMENT ON COLUMN version.log_id IS 'Ссылка на запись в таблице log';
COMMENT ON COLUMN version.entity_id IS 'Ссылка на сущность в таблице entity';
COMMENT ON COLUMN version.data_id IS 'Идентификатор данных сущности';
COMMENT ON COLUMN version.version IS 'Номер версии';
COMMENT ON COLUMN version.changes IS 'Изменения в формате JSONB';

-- Создание индексов для улучшения производительности
CREATE INDEX IF NOT EXISTS idx_version_log_id ON version (log_id);
CREATE INDEX IF NOT EXISTS idx_version_entity_id ON version (entity_id);
CREATE INDEX IF NOT EXISTS idx_version_data_id ON version (data_id);
CREATE INDEX IF NOT EXISTS idx_log_log_time ON log (log_time);
CREATE INDEX IF NOT EXISTS idx_log_action ON log (action);
CREATE INDEX IF NOT EXISTS idx_log_security_log_id ON log_security (log_id);
CREATE INDEX IF NOT EXISTS idx_log_security_ip_address ON log_security (ip_address);
CREATE INDEX IF NOT EXISTS idx_entity_clazz ON entity (clazz);