ALTER TABLE users
ADD COLUMN IF NOT EXISTS weekly_workload INTEGER;
ADD COLUMN IF NOT EXISTS missing_weekly_workload INTEGER;

INSERT INTO users (registration, name, email, role, user_type, weekly_workload, missing_weekly_workload)
VALUES
  ('0000000', '', '', 'TEACHER', 'TEACHER', NULL),
  ('1111111', '', '', 'TEACHER', 'TEACHER', NULL),
  ('202315020036', '', '', 'STUDENT', 'STUDENT', 10, 10);