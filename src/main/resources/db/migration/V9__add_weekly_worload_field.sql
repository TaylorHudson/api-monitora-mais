ALTER TABLE users
ADD weekly_workload INTEGER,
ADD missing_weekly_workload INTEGER;

DELETE FROM monitoring_schedules;

UPDATE users
SET weekly_workload = NULL,
    missing_weekly_workload = NULL
WHERE user_type = 'TEACHER';

UPDATE users
SET weekly_workload = 600,
    missing_weekly_workload = 600
WHERE user_type = 'STUDENT';