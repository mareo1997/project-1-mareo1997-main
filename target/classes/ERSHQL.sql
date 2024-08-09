delete from ersuser where userid > 3;
delete from reimbursement where reimbursementid > 7;
delete from status where statusid > 3;
delete from "type" where typeid > 4;
DELETE FROM "role" WHERE roleid>2;

select * from reimbursement where not status_statusid = 1;

update "type" set "type" = 'TRAVEL' where typeid =2;