CREATE TABLE IF NOT EXISTS monitoring_sessions (
    id SERIAL PRIMARY KEY,
    monitor_registration VARCHAR(255) NOT NULL,
    discipline_id BIGINT NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    description TEXT,
    is_started BOOLEAN NOT NULL,

    CONSTRAINT fk_monitor FOREIGN KEY (monitor_registration) REFERENCES users(registration),
    CONSTRAINT fk_discipline FOREIGN KEY (discipline_id) REFERENCES disciplines(id)
);