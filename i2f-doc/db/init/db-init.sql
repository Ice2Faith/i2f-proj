
-- 密码都为 123456
INSERT INTO sys_user (id,username,password,realname,phone,email,reg_date,del_flag,sys_flag,status,create_time,create_user,update_time,update_user) VALUES
	 (1,'root','$2a$10$oDTbIQm3XK0lyU2J/yxP8.c75G790o.t26bKOfBIg0alQQSt8YL4q','超级管理员',NULL,NULL,'2023-07-04 14:56:44',0,1,1,'2023-07-04 14:56:44','sys','2023-07-06 14:09:05',NULL),
	 (2,'admin','$2a$10$fE1vduX2l6xgvC/7ZEpF3ek0vp7KA3UfIQf9muaqvjqrVf2YgYwG2','管理员',NULL,NULL,'2023-07-04 14:56:44',1,0,1,'2023-07-04 14:56:44','sys',NULL,NULL);

INSERT INTO sys_role (id,role_key,role_name,status,del_flag,sys_flag,create_time,create_user,update_time,update_user) VALUES
	 (1,'root','超级管理员',1,0,1,'2023-07-04 14:49:00','sys',NULL,NULL),
	 (2,'admin','管理员',1,1,0,'2023-07-04 14:49:00','root',NULL,NULL);

INSERT INTO sys_user_role (id,user_id,role_id,create_time,create_user) VALUES
	 (1,1,1,'2023-07-04 14:57:20','sys'),
	 (2,2,2,'2023-07-04 14:57:20','sys');

INSERT INTO sys_resources (id,name,menu_key,`type`,url,perm_key,remark,parent_id,status,order_index,icon,`style`,create_time,create_user,update_time,update_user) VALUES
	 (1,'首页','home',0,'/home','home',NULL,NULL,1,NULL,NULL,NULL,'2023-07-04 14:50:21','sys',NULL,NULL);

INSERT INTO sys_role_resources (id,role_id,res_id,create_time,create_user) VALUES
	 (1,1,1,'2023-07-04 14:51:04','sys'),
	 (2,2,1,'2023-07-04 14:51:04','sys');

INSERT INTO sys_dept (id,dept_key,name,remark,parent_id,`level`,status,create_time,create_user,update_time,update_user) VALUES
	 (id,'root','根',NULL,NULL,0,1,'2023-07-04 14:51:51','sys',NULL,NULL);

INSERT INTO sys_user_dept (id,user_id,dept_id,create_time,create_user) VALUES
	 (1,1,1,'2023-07-04 14:57:51','sys'),
	 (2,2,1,'2023-07-04 14:57:51','sys');

INSERT INTO sys_config (id,config_key,name,remark,mod_flag,del_flag,sys_flag,create_time,create_user,update_time,update_user) VALUES
	 (1,'sys:info','系统信息',NULL,1,1,0,'2023-07-06 17:02:12','sys',NULL,NULL);

INSERT INTO sys_config_item (id,config_id,entry_id,entry_key,entry_name,entry_desc,entry_order,parent_entry_id,param_desc,param1,param2,param3,param4,param5,status,`level`,valid_time,invalid_time,mod_flag,del_flag,sys_flag,create_time,create_user,update_time,update_user) VALUES
	 (1,1,1,'name','secure-app',NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2023-07-06 16:42:56','3000-01-01 00:00:00',1,0,1,'2023-07-06 16:42:56','sys',NULL,NULL),
	 (2,1,2,'icon','icon-secure',NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2023-07-06 16:42:56','3000-01-01 00:00:00',1,0,1,'2023-07-06 16:42:56','sys',NULL,NULL);
