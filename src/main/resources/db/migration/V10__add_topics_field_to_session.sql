ALTER TABLE monitoring_sessions
RENAME COLUMN description TO topics;

DELETE FROM monitoring_schedules;
DELETE FROM monitoring_sessions;