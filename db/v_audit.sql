create or replace view v_audit 
as 
(
   select u.digital_account, u.pt_account, a.* from audit a
   left join user u on a.open_id_identifier = u.open_id_identifier
)

