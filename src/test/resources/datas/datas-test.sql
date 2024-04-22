set REFERENTIAL_INTEGRITY FALSE;
truncate TABLE Movie;
set REFERENTIAL_INTEGRITY TRUE;

insert into Movie (name, id) values ('Inception', -1L);
insert into Movie (name, id) values ('Memento', -2L);
