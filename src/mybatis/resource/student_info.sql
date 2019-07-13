drop table if exists t_lecture;

drop table if exists t_student;

drop table if exists t_student_health_female;

drop table if exists t_student_health_male;

drop table if exists t_student_lecture;

drop table if exists t_student_selfcard;

create table t_lecture
(
id int(20) not null comment '编号',
lecture_name varchar(60) not null comment '课程名称',
note varchar(1024) comment '备注',
primary key (id)
);

create table t_student
(
id int(20) not null auto_increment comment '编号',
cnname varchar(60) not null comment '学生',
sex tinyint(60) not null comment '性别',
selfcard_no int(20) not null comment '学生证号',
note varchar(1024) comment '备注',
primary key (id)
);

create table t_student_health_female
(
id int(20) not null auto_increment comment '编号',
student_id varchar(60) not null comment '学生编号',
check_date varchar(60) not null comment '检查日期',
heart varchar(60) not null comment '心',
liver varchar(60) not null comment '肝',
spleen varchar(60) not null comment '脾',
lung varchar(60) not null comment '肺',
kidney varchar(60) not null comment '肾',
uterus varchar(60) not null comment '子宫',
note varchar(1024) not null comment '备注',
primary key (id)
);

create table t_student_health_male
(
id int(20) not null auto_increment comment '编号',
student_id varchar(60) not null comment '学生编号',
check_date varchar(60) not null comment '检查日期',
heart varchar(60) not null comment '心',
liver varchar(60) not null comment '肝',
spleen varchar(60) not null comment '脾',
lung varchar(60) not null comment '肺',
kidney varchar(60) not null comment '肾',
prostate varchar(60) not null comment '前列腺',
note varchar(1024) not null comment '备注',
primary key (id)
);

create table t_student_lecture
(
id int(20) not null auto_increment comment '编号',
student_id int(20) not null comment '学生编号',
lecture_id int(20) not null comment '课程编号',
grade decimal(16,2) not null comment '评分',
note varchar(1024) not null comment '备注',
primary key (id)
);

create table t_student_selfcard
(
id int(20) not null auto_increment comment '编号',
student_id int(20) not null comment '学生编号',
native varchar(60) not null comment '籍贯',
issue_date date not null comment '发证日期',
end_date date not null comment '结束日期',
note varchar(1024) comment '备注',
primary key (id)
);





















