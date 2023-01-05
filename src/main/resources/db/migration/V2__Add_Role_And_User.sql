INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_LOCAL_ADMIN');

INSERT INTO public.users(
    email, password, username,
                         activation_code, active)
VALUES ('admin@admin.ru', '$2a$10$zggKiN/GEst8X2JLI8lpQ.xqzC1qUvDsvvz/RIDnCuNt9zDAHXa7O', 'admin', '', true);
