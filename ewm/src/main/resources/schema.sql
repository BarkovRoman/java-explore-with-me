DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS categories cascade;
DROP TABLE IF EXISTS compilation cascade;
DROP TABLE IF EXISTS events cascade;
DROP TABLE IF EXISTS request cascade;
DROP TABLE IF EXISTS events_compilations cascade;

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(50),
    CONSTRAINT PK_CATEGORIES PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORIES_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilation
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR(50),
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(30),
    email VARCHAR(30),
    CONSTRAINT PK_USER PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                                 NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    confirmed_requests INTEGER,
    created_on         TIMESTAMP,
    description        VARCHAR(7000),
    event_date         TIMESTAMP                               NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  INTEGER,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN,
    state              VARCHAR,
    title              VARCHAR(120)                            NOT NULL,
    views              INTEGER,
    location_lat       FLOAT,
    location_lon       FLOAT,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT FK_EVENT_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS request
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event     BIGINT                                  NOT NULL,
    requester BIGINT                                  NOT NULL,
    status    VARCHAR,
    created   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  events_compilations
(
    compilation_id bigint not null,
    event_id       bigint not null,
    CONSTRAINT PK_EVENT_COMPILATIONS PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT FK_COMPILATIONS FOREIGN KEY (compilation_id) REFERENCES compilation (id),
    CONSTRAINT FK_EVENT FOREIGN KEY (event_id) REFERENCES events (id)

);