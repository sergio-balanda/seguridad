
INSERT INTO "PUBLIC"."ROL" ( "AUTHORITY" ) VALUES ( 'Administrador','Usuario');

INSERT INTO "PUBLIC"."USUARIO"
( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" )
VALUES (  'admin', '1', 'habilitado', 'aca el texto del admin',1 )
INSERT INTO "PUBLIC"."USUARIO"
( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" )
VALUES (  'user', '1', 'habilitado', 'aca el texto del usuario',2 )
INSERT INTO "PUBLIC"."USUARIO"
( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" )
VALUES (  'deshabilitado@mail.com', '123456', 'deshabilitado', 'aca el texto del usuario',2 )
