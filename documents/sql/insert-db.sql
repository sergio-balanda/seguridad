
INSERT INTO "PUBLIC"."ROL" ( "AUTHORITY" ) VALUES ( 'Administrador','Usuario');
INSERT INTO "PUBLIC"."USUARIO"
( "EMAIL", "PASSWORD", "ESTADO", "TEXT", "ROL" )
VALUES (  'admin@mail.com', '123456', 'habilitado', 'aca el texto del admin',1 )
INSERT INTO "PUBLIC"."USUARIO"
( "EMAIL", "PASSWORD", "ESTADO", "TEXT", "ROL" )
VALUES (  'usuario@mail.com', '123456', 'habilitado', 'aca el texto del usuario',2 )