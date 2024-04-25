set REFERENTIAL_INTEGRITY FALSE;
truncate TABLE Review;
truncate TABLE Movie_Genre;
truncate TABLE Movie;
truncate TABLE Genre;
truncate TABLE Movie_Details;
set REFERENTIAL_INTEGRITY TRUE;

insert into Movie (name, certification, id) values ('Inception', 1, -1L);
insert into Movie (name, certification, id) values ('Memento',2,  -2L);

insert into Review (author, content, movie_id, id) values ('max', 'au top !', -1L, -1L);
insert into Review (author, content, movie_id, id) values ('ernest', 'bof bof', -1L, -2L);

insert into Genre (name, id) values ('Action', -1L);