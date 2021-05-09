create table if not exists GAME_RESULTS
(
    GAME_ID int auto_increment,
    GAME_DATE date not null,
    TEAM_HOME_ID int not null,
    TEAM_AWAY_ID int not null,
    TEAM_WINNER_ID int not null,
    SCORE_HOME int default 0,
    SCORE_AWAY int default 0,
    SCORE_WINNER int default 0,
    GAME_TOURNAMENT varchar(255) not null,
    GAME_CITY varchar(255) not null,
    GAME_COUNTRY varchar(255) not null,
    constraint GAME_RESULTS_pk
        primary key (GAME_ID)
);
