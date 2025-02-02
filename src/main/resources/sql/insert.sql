-- 🚀 Ensure `ROLE_ADMIN` exists before assigning it
MERGE INTO roles AS r
    USING (SELECT 'ROLE_ADMIN' AS role_name) AS temp
ON r.role_name = temp.role_name
WHEN NOT MATCHED THEN
    INSERT (role_name) VALUES (temp.role_name);

-- 🚀 Insert `admin` user only if it does not already exist
MERGE INTO users AS u
    USING (SELECT 'admin' AS username,
                  '$2a$10$7qJ0rEhzjqPbPqK/Gs30Ke.FB8pF8Vsz6VhRXgJdErbhNGiW9wHG.' AS password,
                  (SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN') AS role_id) AS temp
ON u.username = temp.username
WHEN NOT MATCHED THEN
    INSERT (username, password, role_id) VALUES (temp.username, temp.password, temp.role_id);
