CREATE TABLE IF NOT EXISTS monitoring_students (
    monitoring_id BIGINT NOT NULL,
    student_registration VARCHAR(255) NOT NULL,
    PRIMARY KEY (monitoring_id, student_registration),
    CONSTRAINT fk_monitoring_student FOREIGN KEY (monitoring_id)
        REFERENCES monitoring(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_student_registration FOREIGN KEY (student_registration)
        REFERENCES users(registration) ON DELETE CASCADE ON UPDATE CASCADE
);
