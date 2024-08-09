drop table if exists ERSuser cascade;
drop table if exists roles cascade;
drop table if exists reimbursment cascade;
drop table if exists ERSstatus cascade;
drop table if exists ERStype cascade;

create table ERSuser(
	userid serial primary key,
	username varchar(50) not null unique,
	erspassword varchar(50) not null,
	firstname VARCHAR(100) NOT null,
	lastname VARCHAR(100) NOT null,
	email VARCHAR(150) not null unique--roleid integer not null,--FOREIGN KEY (roleid) REFERENCES roles (roleid) on delete cascade
);

create table roles(
	roleid serial primary key,
	ersroles varchar(10) not null,
	userid integer not null,
	FOREIGN KEY (userid) REFERENCES ersuser (userid) on delete cascade
);

CREATE table reimbursment(
	reimbursmentid serial primary key,
	author integer not null,
	description varchar(250),
	amount numeric(12,2) not null,
	submitted timestamp not null,
	receipt bytea,
	resolver integer,
	resolved timestamp,--statusid integer not null,--typeid integer not null,
	FOREIGN KEY (author) REFERENCES ERSuser (userid) on delete cascade,
	FOREIGN KEY (resolver) REFERENCES ERSuser (userid) on delete cascade--FOREIGN KEY (statusid) REFERENCES ERSstatus (statusid) on delete cascade,--FOREIGN KEY (typeid) REFERENCES ERStype (typeid) on delete cascade
);

CREATE table ERSstatus( 
	statusid serial primary key,
	status varchar(10) not null,
	reimbursmentid integer not null,
	FOREIGN KEY (reimbursmentid) REFERENCES reimbursment (reimbursmentid) on delete cascade
);

create table ERStype(
	typeid serial primary key,
	erstype varchar(10) not null,
	reimbursmentid integer not null,
	FOREIGN KEY (reimbursmentid) REFERENCES reimbursment (reimbursmentid) on delete cascade
);

create or REPLACE PROCEDURE insert_user(username text, erspassword text, firstname text, lastname text, email text)
AS $$
	BEGIN 
		insert into ersuser (username, erspassword, firstname, lastname,email) values (username, erspassword, firstname, lastname, email);
	END;
$$ LANGUAGE plpgsql;

create OR REPLACE FUNCTION submit_time()
returns TRIGGER AS 
$$
BEGIN 
	new.submitted = now();
	return new;
END;
$$ language plpgsql;

create trigger set_current_time
before insert on reimbursment
for each row EXECUTE PROCEDURE submit_time();

create or replace PROCEDURE insert_statustype(erstype text, reimbursmentid integer)
AS $$
	BEGIN 
		insert into ERSstatus(status,reimbursmentid) values ('PENDING',reimbursmentid);
		INSERT into ERStype(erstype,reimbursmentid) values (erstype,reimbursmentid);
	END
$$ LANGUAGE plpgsql;

create OR REPLACE FUNCTION resolve_time()
returns TRIGGER AS 
$$
BEGIN 
	new.resolved = now();
	return new;
END;
$$ language plpgsql;

create trigger r_current_time
before update on reimbursment
for each row EXECUTE PROCEDURE resolve_time();

--DROP PROCEDURE update_reim(integer,text);
create or REPLACE PROCEDURE update_reim(rid integer, s text, manager integer)
AS $$
	BEGIN 
		update ersstatus set status = s where reimbursmentid = rid;
		update reimbursment set resolver = manager where reimbursmentid = rid;
	END;
$$ LANGUAGE plpgsql;

CALL insert_user('mareo1997', 'password', 'Mareo', 'Yapp', 'mareo1997@gmail.com');
CALL insert_user('marwil', 'william', 'Marcia', 'Williamson', 'mother@gmail.com');
Call insert_user('king', 'george', 'Kingsley', 'Yapp', 'father@gmail.com');
insert into roles(ersroles,userid) values ('EMPLOYEE',1);
insert into roles(ersroles,userid) values ('EMPLOYEE',2);
insert into roles(ersroles,userid) values ('MANAGER',3);

INSERT into reimbursment (amount,description,author) values (85.4,'test2',1);
call insert_statustype('FOOD', 5);
CALL update_reim(5,'DENIED',2);

select * from ersuser;
select * from roles;
select * from reimbursment;
select * from ersstatus;
select * from erstype;

select * FROM ersuser e inner join roles r on r.userid =e.userid WHERE r.ersroles ='EMPLOYEE';

select *
from reimbursment r
inner join ersstatus s on r.reimbursmentid =s.reimbursmentid 
inner join erstype t on t.reimbursmentid =s.reimbursmentid;

select * from reimbursment r
inner join ersstatus s on r.reimbursmentid =s.reimbursmentid
inner join erstype t on s.reimbursmentid = t.reimbursmentid 
where s.status ='PENDING';

SELECT s.statusid, s.status, t.typeid, t.erstype
from ersstatus s inner join erstype t on s.reimbursmentid = t.reimbursmentid;

update reimbursment set description = 'description' where id=1;
update ersstatus set status ='Approved' where statusid =3;
delete from reimbursment where id=1;

rollback;