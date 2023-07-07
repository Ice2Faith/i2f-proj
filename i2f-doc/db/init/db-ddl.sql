
CREATE TABLE sys_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  username varchar(300)  NOT NULL COMMENT '登录用户名',
  password varchar(300)  NOT NULL COMMENT '登录密码',
  realname varchar(300)   COMMENT '用户名',
  phone varchar(50)   COMMENT '电话号码',
  email varchar(300)   COMMENT '电子邮箱',
  reg_date datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  del_flag tinyint(4) DEFAULT '1' COMMENT '是否可删除：0 不可，1 可以',
  sys_flag tinyint(4) DEFAULT '0' COMMENT '是否系统：0 否，1 是',
  status int(11) DEFAULT '1' COMMENT '状态：0 禁用，1 启用，99 删除',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)   COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status_username_password (status,username,password),
  KEY idx_status_phone_password (status,phone,password),
  KEY idx_status_email_password (status,email,password)
)   COMMENT='用户表';

CREATE TABLE sys_role (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  role_key varchar(300)   COMMENT '角色键',
  role_name varchar(300)   COMMENT '角色名称',
  status int(11) DEFAULT '1' COMMENT '状态：0 禁用，1 启用，99 删除',
  del_flag tinyint(4) DEFAULT '1' COMMENT '是否可删除：0 不可，1 可以',
  sys_flag tinyint(4) DEFAULT '0' COMMENT '是否系统：0 否，1 是',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)   COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status_rkey (status,role_key)
)   COMMENT='角色表';

CREATE TABLE sys_user_role (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  user_id bigint(20) NOT NULL COMMENT '用户ID，见：sys_user.id',
  role_id bigint(20) NOT NULL COMMENT '角色ID，见：sys_role.id',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  PRIMARY KEY (id),
  UNIQUE KEY unq_user_role (user_id,role_id)
)   COMMENT='用户-角色表';

CREATE TABLE sys_resources (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  name varchar(300)   COMMENT '名称',
  menu_key varchar(300)   COMMENT '菜单键',
  type int(11) NOT NULL DEFAULT '0' COMMENT '类型：0 菜单，1 权限',
  url varchar(4096)   COMMENT 'URL',
  perm_key varchar(300)   COMMENT '权限键',
  remark varchar(300)   COMMENT '备注',
  parent_id bigint(20)  COMMENT '父资源ID，见：sys_resources.id',
  status int(11) DEFAULT '1' COMMENT '状态：0 禁用，1 启用，99 删除',
  order_index int(11)  COMMENT '排序字段',
  icon varchar(300)   COMMENT 'ICON',
  style varchar(500)   COMMENT '样式',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)   COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status_type (status,type)
)   COMMENT='资源表';

CREATE TABLE sys_role_resources (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  role_id bigint(20) NOT NULL COMMENT '角色ID，见：sys_role.id',
  res_id bigint(20) NOT NULL COMMENT '资源ID，见：sys_resources.id',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  PRIMARY KEY (id),
  UNIQUE KEY unq_role_res (role_id,res_id)
)   COMMENT='角色-资源表';

CREATE TABLE sys_dept (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  dept_key varchar(300)   COMMENT '部门键',
  name varchar(300)   COMMENT '名称',
  remark varchar(1024)   COMMENT '备注',
  parent_id bigint(20)  COMMENT '父资源ID，见：sys_dept.id',
  level int(11)  COMMENT '层级',
  status int(11) DEFAULT '1' COMMENT '状态：0 禁用，1 启用，99 删除',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)   COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_status_parent_id (status,parent_id)
)   COMMENT='部门表';

CREATE TABLE sys_user_dept (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  user_id bigint(20) NOT NULL COMMENT '用户ID，见：sys_user.id',
  dept_id bigint(20) NOT NULL COMMENT '部门ID，见：sys_dept.id',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)   COMMENT '创建人',
  PRIMARY KEY (id),
  UNIQUE KEY unq_user_dept (user_id,dept_id)
)   COMMENT='用户-部门表';

CREATE TABLE sys_config (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  config_key varchar(300)  NOT NULL COMMENT '配置KEY',
  name varchar(300)    COMMENT '名称',
  remark varchar(1024)    COMMENT '描述',
  mod_flag tinyint(4) DEFAULT '1' COMMENT '是否可修改：0 不可，1 可以',
  del_flag tinyint(4) DEFAULT '1' COMMENT '是否可删除：0 不可，1 可以',
  sys_flag tinyint(4) DEFAULT '0' COMMENT '是否系统：0 否，1 是',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)    COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)    COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY unq_config (config_key)
)  COMMENT '配置表';


CREATE TABLE sys_config_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  config_id bigint(20) NOT NULL COMMENT '配置ID：见sys_config.id',
  entry_id bigint(20) NOT NULL COMMENT '项ID',
  entry_key varchar(1024)    COMMENT '项键',
  entry_name varchar(1024)    COMMENT '项名称',
  entry_desc varchar(1024)    COMMENT '项描述',
  entry_order int(11)  COMMENT '项排序',
  parent_entry_id bigint(20)  COMMENT '父项ID，见：sys_config_item.entry_id',
  param_desc varchar(1024)    COMMENT '参数描述',
  param1 varchar(2048)    COMMENT '参数1',
  param2 varchar(2048)    COMMENT '参数2',
  param3 varchar(2048)    COMMENT '参数3',
  param4 varchar(2048)    COMMENT '参数4',
  param5 varchar(2048)    COMMENT '参数5',
  status int(11) DEFAULT '1' COMMENT '状态：0 禁用，1 启用，99 删除',
  level int(11)  COMMENT '层级',
  valid_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
  invalid_time datetime DEFAULT '3000-01-01 00:00:00' COMMENT '失效时间',
  mod_flag tinyint(4) DEFAULT '1' COMMENT '是否可修改：0 不可，1 可以',
  del_flag tinyint(4) DEFAULT '1' COMMENT '是否可删除：0 不可，1 可以',
  sys_flag tinyint(4) DEFAULT '0' COMMENT '是否系统：0 否，1 是',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  create_user varchar(300)    COMMENT '创建人',
  update_time datetime  COMMENT '更新时间',
  update_user varchar(300)    COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY unq_config_entry (config_id,entry_id,status),
  KEY idx_status_valid_invalid (status,valid_time,invalid_time),
  KEY idx_status_parent (status,parent_entry_id)
)   COMMENT='配置项表';
