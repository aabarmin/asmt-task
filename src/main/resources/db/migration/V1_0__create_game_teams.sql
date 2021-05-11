create table if not exists GAME_TEAMS
(
    TEAM_ID int auto_increment,
    TEAM_NAME varchar(255) not null,
    constraint GAME_TEAM_PK primary key (TEAM_ID)
);
