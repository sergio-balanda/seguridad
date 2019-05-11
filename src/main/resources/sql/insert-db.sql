INSERT INTO "PUBLIC"."ROL" ( "AUTHORITY" ) VALUES ( 'Administrador','Usuario');

INSERT INTO "PUBLIC"."USUARIO" ( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" ) VALUES (  'admin@mail.com', '1', 'habilitado', 'aca el texto del admin',1 )
INSERT INTO "PUBLIC"."USUARIO" ( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" ) VALUES (  'user@mail.com', '1', 'habilitado', 'aca el texto del usuario',2 )
INSERT INTO "PUBLIC"."USUARIO" ( "EMAIL", "PASSWORD", "ESTADO", "TEXTO", "ROL" ) VALUES (  'deshabilitado@mail.com', '123456', 'deshabilitado', 'aca el texto del usuario',2 )

INSERT INTO "PUBLIC"."AUDITORIA" ( "ACCION", "USUARIO", "CREADO", "ACTUALIZADO" ) VALUES (  'registro del admin', 1, '2018-01-01 19:00:00', '2018-01-01 19:00:00' )
INSERT INTO "PUBLIC"."AUDITORIA" ( "ACCION", "USUARIO", "CREADO", "ACTUALIZADO" ) VALUES (  'modificacion admin', 1, '2018-01-01 19:00:00', '2019-01-01 19:00:00' )
INSERT INTO "PUBLIC"."AUDITORIA" ( "ACCION", "USUARIO", "CREADO", "ACTUALIZADO" ) VALUES (  'cambio de estado de user', 2, '2018-01-01 19:00:00', '2019-03-01 19:00:00' )