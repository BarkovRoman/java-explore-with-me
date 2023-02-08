DROP TABLE IF EXISTS hits;
DROP TABLE IF EXISTS app;
create table if not exists app
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(30) NOT NULL,

    CONSTRAINT PC_APP_APPS PRIMARY KEY (id),
    CONSTRAINT UQ_NAME UNIQUE (name)
);

create table if not exists hits
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY,
    uri     VARCHAR(30) NOT NULL,
    ip      VARCHAR(20) NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    app_id BIGINT,
    CONSTRAINT pk_hits PRIMARY KEY (id),
    CONSTRAINT fk_app FOREIGN KEY (app_id) REFERENCES app (id)
);