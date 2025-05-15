CREATE TABLE IF NOT EXISTS monitoring_schedules (
    id SERIAL PRIMARY KEY,
    monitor_registration VARCHAR(255) NOT NULL,
    discipline_id BIGINT NOT NULL,
    day_of_week VARCHAR(20),
    start_time TIME,
    end_time TIME,
    status VARCHAR(20),
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_monitor FOREIGN KEY (monitor_registration) REFERENCES users (registration),
    CONSTRAINT fk_discipline FOREIGN KEY (discipline_id) REFERENCES disciplines (id)
);