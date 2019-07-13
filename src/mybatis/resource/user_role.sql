/*Table: T_ROLE*/
create table T_ROLE
(
 id int(20) not null auto_increment comment '编号',
 role_name varchar(60) not null comment '角色名称',
 note varchar(1024) comment '备注',
 primary key (id)
);

/*Table: T_USER*/
create table T_USER
(
id bigint(20) not null auto_increment comment '编号',
user_name varchar(60) not null comment '用户名称',
cnname varchar(60) not null comment '姓名',
sex tinyint(3) not null comment '性别',
mobile varchar(20) not null comment '手机号码',
email varchar(60) comment '电子邮件',
note varchar(1024) comment '备注',
primary key (id)
);

/*Table: T_USER_ROLE*/
create table T_USER_ROLE
(
user_id bigint(20) not null comment '编号',
role_id int(20) not null comment '角色编号',
primary key (user_id, role_id)
);

alter table T_USER_ROLE add constraint FK_Reference_1 foreign key (user_id)
	references T_USER (id) on delete restrict on update restrict;
	
alter table T_USER_ROLE add constraint FK_Reference_2 foreign key (role_id)
	references T_ROLE (id) on delete restrict on update restrict;