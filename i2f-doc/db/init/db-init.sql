create table sys_user
(
 id bigint auto_increment primary key comment 'ID',
 username varchar(300) not null comment '登录用户名',
 password varchar(300) not null comment '登录密码',
 realname varchar(300) comment '用户名',
 phone varchar(50) comment '电话号码',
 email varchar(300) comment '电子邮箱',
 reg_date datetime default now() comment '注册时间',
 del_flag tinyint default 1 comment '是否可删除：0 不可，1 可以',
 sys_flag tinyint default 0 comment '是否系统：0 否，1 是',
 status int default 1 comment '状态：0 禁用，1 启用，99 删除',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人',
 update_time datetime comment '更新时间',
 update_user varchar(300) comment '更新时间'
) comment '用户表';

create index idx_status_username_password
on sys_user(status,username,password);

create index idx_status_phone_password
on sys_user(status,phone,password);

create index idx_status_email_password
on sys_user(status,email,password);

create table sys_role
(
 id bigint auto_increment primary key comment 'ID',
 role_key varchar(300) comment '角色键',
 role_name varchar(300) comment '角色名称',
 status int default 1 comment '状态：0 禁用，1 启用，99 删除',
 del_flag tinyint default 1 comment '是否可删除：0 不可，1 可以',
 sys_flag tinyint default 0 comment '是否系统：0 否，1 是',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人',
 update_time datetime comment '更新时间',
 update_user varchar(300) comment '更新时间'
) comment '角色表';

create index idx_status_rkey
on sys_role(status,role_key);

create table sys_user_role
(
 id bigint auto_increment primary key comment 'ID',
 user_id bigint not null comment '用户ID，见：sys_user.id',
 role_id bigint not null comment '角色ID，见：sys_role.id',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人'
) comment '用户-角色表';

create unique index unq_user_role
on sys_user_role(user_id,role_id);

create table sys_resources
(
 id bigint auto_increment primary key comment 'ID',
 name varchar(300) comment '名称',
 menu_key varchar(300) comment '菜单键',
 type int not null default 0 comment '类型：0 菜单，1 权限',
 url varchar(4096) comment 'URL',
 perm_key varchar(300) comment '权限键',
 remark varchar(300) comment '备注',
 parent_id bigint comment '父资源ID，见：sys_resources.id',
 status int default 1 comment '状态：0 禁用，1 启用，99 删除',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人',
 update_time datetime comment '更新时间',
 update_user varchar(300) comment '更新时间'
) comment '资源表';

create index idx_status_type
on sys_resources(status,type);

create table sys_role_resources
(
 id bigint auto_increment primary key comment 'ID',
 role_id bigint not null comment '角色ID，见：sys_role.id',
 res_id bigint not null comment '资源ID，见：sys_resources.id',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人'
) comment '角色-资源表';

create unique index unq_role_res
on sys_role_resources(role_id,res_id);

create table sys_config
(
 id bigint auto_increment primary key comment 'ID',
 group_key int not null comment '分组KEY',
 group_name varchar(300) comment '分组名称',
 type_key int not null comment '类型KEY',
 type_name varchar(300) comment '类型名称',
 entry_id bigint not null comment '项ID',
 entry_key varchar(1024) comment '项键',
 entry_name varchar(1024) comment '项名称',
 entry_desc varchar(1024) comment '项描述',
 entry_order int comment '项排序',
 parent_entry_id bigint comment '父项ID，见：sys_config.entry_id',
 param_desc varchar(1024) comment '参数描述',
 param1 varchar(2048) comment '参数1',
 param2 varchar(2048) comment '参数2',
 param3 varchar(2048) comment '参数3',
 param4 varchar(2048) comment '参数4',
 param5 varchar(2048) comment '参数5',
 status int default 1 comment '状态：0 禁用，1 启用，99 删除',
 level int comment '层级',
 valid_time datetime default now() comment '生效时间',
 invalid_time datetime default '3000-01-01 00:00:00' comment '失效时间',
 mod_flag tinyint default 1 comment '是否可修改：0 不可，1 可以',
 del_flag tinyint default 1 comment '是否可删除：0 不可，1 可以',
 sys_flag tinyint default 0 comment '是否系统：0 否，1 是',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人',
 update_time datetime comment '更新时间',
 update_user varchar(300) comment '更新时间'
) comment '配置表';

create unique index unq_group_type_entry
on sys_config(group_key,type_key,entry_id);

create index idx_status_valid_invalid
on sys_config(status,valid_time,invalid_time);

create index idx_status_parent
on sys_config(status,parent_entry_id);

create table sys_dept
(
 id bigint auto_increment primary key comment 'ID',
 dept_key varchar(300) NULL COMMENT '部门键',
 name varchar(300) comment '名称',
 remark varchar(1024) comment '备注',
 parent_id bigint comment '父资源ID，见：sys_dept.id',
 level int comment '层级',
 status int default 1 comment '状态：0 禁用，1 启用，99 删除',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人',
 update_time datetime comment '更新时间',
 update_user varchar(300) comment '更新时间'
) comment '部门表';

create index idx_status_parent_id
on sys_dept(status,parent_id);

create table sys_user_dept
(
 id bigint auto_increment primary key comment 'ID',
 user_id bigint not null comment '用户ID，见：sys_user.id',
 dept_id bigint not null comment '部门ID，见：sys_dept.id',
 create_time datetime default now() comment '创建时间',
 create_user varchar(300) comment '创建人'
) comment '用户-部门表';

create unique index unq_user_dept
on sys_user_dept(user_id,dept_id);



insert into sys_config (group_key,group_name,type_key,type_name,entry_id,entry_key,entry_name,entry_desc,entry_order,parent_entry_id,param_desc,param1,param2,param3,param4,param5,status,`level`,valid_time,invalid_time,mod_flag,del_flag,sys_flag,create_time,create_user,update_time,update_user) values
	 (1,'系统',1,'配置',1,'sys:name','SSWP','应用名称',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2023-07-04 14:55:04','3000-01-01 00:00:00',1,0,1,'2023-07-04 14:55:04','sys',NULL,NULL),
	 (1,'系统',1,'配置',2,'sys:icon','icon-sswp','应用图标',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,'2023-07-04 14:55:04','3000-01-01 00:00:00',1,0,1,'2023-07-04 14:55:04','sys',NULL,NULL);

insert into sys_dept (id,dept_key,name,remark,parent_id,`level`,status,create_time,create_user,update_time,update_user) values
	 (1,'/','根',NULL,NULL,0,1,'2023-07-04 14:51:51','sys',NULL,NULL);

insert into sys_resources (id,name,menu_key,`type`,url,perm_key,remark,parent_id,status,create_time,create_user,update_time,update_user) values
	 (1,'首页','home',0,'/home','home',NULL,NULL,1,'2023-07-04 14:50:21','sys',NULL,NULL);

insert into sys_role (id,role_key,role_name,status,del_flag,sys_flag,create_time,create_user,update_time,update_user) values
	 (1,'root','超级管理员',1,0,1,'2023-07-04 14:49:00','sys',NULL,NULL),
	 (2,'admin','管理员',1,1,0,'2023-07-04 14:49:00','root',NULL,NULL);

insert into sys_role_resources (id,role_id,res_id,create_time,create_user) values
	 (1,1,1,'2023-07-04 14:51:04','sys'),
	 (2,2,1,'2023-07-04 14:51:04','sys');

insert into sys_user (id,username,password,realname,phone,email,reg_date,del_flag,sys_flag,status,create_time,create_user,update_time,update_user) values
	 (1,'root','-','超级管理员',NULL,NULL,'2023-07-04 14:56:44',0,1,1,'2023-07-04 14:56:44','sys',NULL,NULL),
	 (2,'admin','-','管理员',NULL,NULL,'2023-07-04 14:56:44',1,0,1,'2023-07-04 14:56:44','sys',NULL,NULL);

insert into sys_user_dept (id,user_id,dept_id,create_time,create_user) values
	 (1,1,1,'2023-07-04 14:57:51','sys'),
	 (2,2,1,'2023-07-04 14:57:51','sys');

insert into sys_user_role (id,user_id,role_id,create_time,create_user) values
	 (1,1,1,'2023-07-04 14:57:20','sys'),
	 (2,2,2,'2023-07-04 14:57:20','sys');
