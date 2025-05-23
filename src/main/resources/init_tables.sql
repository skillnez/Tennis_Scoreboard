CREATE TABLE MATCHES
(
    id         INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    PLAYER1_ID INT,
    PLAYER2_ID INT,
    WINNER     INT,
    CONSTRAINT pk_matches PRIMARY KEY (id)
);

CREATE TABLE PLAYERS
(
    ID   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    NAME VARCHAR(64)                          NOT NULL,
    CONSTRAINT pk_players PRIMARY KEY (ID)
);

ALTER TABLE PLAYERS
    ADD CONSTRAINT uc_players_name UNIQUE (NAME);

ALTER TABLE MATCHES
    ADD CONSTRAINT FK_MATCHES_ON_PLAYER1 FOREIGN KEY (PLAYER1_ID) REFERENCES PLAYERS (ID);

ALTER TABLE MATCHES
    ADD CONSTRAINT FK_MATCHES_ON_PLAYER2 FOREIGN KEY (PLAYER2_ID) REFERENCES PLAYERS (ID);