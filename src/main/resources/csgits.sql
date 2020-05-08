CREATE DATABASE "CSGITS"
    WITH OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE school(
                     id  serial primary key,
                     name varchar,
                     description varchar,
                     image_path varchar,
                     teacher_num integer,
                     total_num integer,
                     school_num integer,
                     create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE school OWNER TO postgres;
COMMENT ON TABLE school IS '学校信息表';
COMMENT ON COLUMN school.id IS 'id标识';
COMMENT ON COLUMN school.name IS '学校名称';
COMMENT ON COLUMN school.description IS '学校描述';
COMMENT ON COLUMN school.image_path IS '学校图片路径';
COMMENT ON COLUMN school.teacher_num IS '学校中教师人数';
COMMENT ON COLUMN school.total_num IS '该校学生总人数';
COMMENT ON COLUMN school.school_num IS '该校在校生人数';
COMMENT ON COLUMN school.create_time IS '记录添加时间';

CREATE TABLE manager(
                      id  serial primary key,
                      no varchar,
                      real_name varchar,
                      password varchar,
                      image_path varchar,
                      tel varchar,
                      school_id integer references school(id),
                      college varchar,
                      fine boolean default false,
                      description varchar,
                      create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE manager OWNER TO postgres;
COMMENT ON TABLE manager IS '管理者信息表';
COMMENT ON COLUMN manager.id IS 'id标识';
COMMENT ON COLUMN manager.no IS '老师编号，管理员为admin';
COMMENT ON COLUMN manager.real_name IS '真实名字';
COMMENT ON COLUMN manager.password IS '密码';
COMMENT ON COLUMN manager.image_path IS '头像路径';
COMMENT ON COLUMN manager.tel IS '联系方式';
COMMENT ON COLUMN manager.school_id IS '学校id';
COMMENT ON COLUMN manager.college IS '所属学院';
COMMENT ON COLUMN manager.fine IS '是否为优秀教师';
COMMENT ON COLUMN manager.description IS '个人描述';
COMMENT ON COLUMN manager.create_time IS '创建时间';

CREATE TABLE notice(
                     id  serial primary key,
                     manager_id integer,
                     title varchar,
                     notice_type integer,
                     content varchar,
                     create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE notice OWNER TO postgres;
COMMENT ON TABLE notice IS '通知信息表';
COMMENT ON COLUMN notice.id IS 'id标识';
COMMENT ON COLUMN notice.manager_id IS '管理者id';
COMMENT ON COLUMN notice.title IS '通知标题';
COMMENT ON COLUMN notice.notice_type IS '通知类型';
COMMENT ON COLUMN notice.content IS '通知内容';
COMMENT ON COLUMN notice.create_time IS '添加时间';



CREATE TABLE recruit(
                      id  serial primary key,
                      name varchar,
                      description varchar,
                      url varchar,
                      manager_id integer references manager(id),
                      status integer default 0,
                      create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE recruit OWNER TO postgres;
COMMENT ON TABLE recruit IS '企业招聘信息表';
COMMENT ON COLUMN recruit.id IS 'id标识';
COMMENT ON COLUMN recruit.name IS '公司名称';
COMMENT ON COLUMN recruit.description IS '公司描述';
COMMENT ON COLUMN recruit.url IS '公司招聘官网';
COMMENT ON COLUMN recruit.manager_id IS '管理员id';
COMMENT ON COLUMN recruit.status IS '招聘状态';
COMMENT ON COLUMN recruit.create_time IS '记录添加时间';

CREATE TABLE goods(
                    id  serial primary key,
                    name varchar,
                    description varchar,
                    image_path varchar,
                    num integer,
                    credits integer,
                    create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE goods OWNER TO postgres;
COMMENT ON TABLE goods IS '物品信息表';
COMMENT ON COLUMN goods.id IS 'id标识';
COMMENT ON COLUMN goods.name IS '物品名称';
COMMENT ON COLUMN goods.description IS '物品描述';
COMMENT ON COLUMN goods.image_path IS '物品图片路径';
COMMENT ON COLUMN goods.num IS '物品剩余数量';
COMMENT ON COLUMN goods.credits IS '兑换所需积分';
COMMENT ON COLUMN goods.create_time IS '记录添加时间';

CREATE TABLE redeem(
                     id serial primary key,
                     sole varchar,
                     good_id integer references goods(id),
                     status integer,
                     create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE redeem OWNER TO postgres;
COMMENT ON TABLE redeem IS '兑换记录表';
COMMENT ON COLUMN redeem.id IS 'id标识';
COMMENT ON COLUMN redeem.sole IS '学生唯一标识';
COMMENT ON COLUMN redeem.good_id IS '物品id';
COMMENT ON COLUMN redeem.status IS '兑换状态';
COMMENT ON COLUMN redeem.create_time IS '记录添加时间';

CREATE TABLE create_record(
                            id  serial primary key,
                            name varchar,
                            description varchar,
                            manager_id integer references manager(id),
                            start_time varchar,
                            end_time varchar,
                            create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE create_record OWNER TO postgres;
COMMENT ON TABLE create_record IS '创建记录信息表';
COMMENT ON COLUMN create_record.id IS 'id标识';
COMMENT ON COLUMN create_record.name IS '标题';
COMMENT ON COLUMN create_record.description IS '描述';
COMMENT ON COLUMN create_record.manager_id IS '管理员id';
COMMENT ON COLUMN create_record.start_time IS '记录开始时间';
COMMENT ON COLUMN create_record.end_time IS '记录结束时间';
COMMENT ON COLUMN create_record.create_time IS '记录添加时间';

CREATE TABLE write_record_data(
                            id  serial primary key,
                            create_record_id integer,
                            school_id integer,
                            college varchar,
                            date varchar,
                            current_num integer,
                            engaged_num integer,
                            work_num integer,
                            study_num integer,
                            college_num integer,
                            graduate_num integer,
                            doctor_num integer,
                            hometown_num integer,
                            salary0_num integer,
                            salary1_num integer,
                            salary2_num integer,
                            salary3_num integer,
                            salary4_num integer,
                            create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE write_record_data OWNER TO postgres;
COMMENT ON TABLE write_record_data IS '填写记录统计信息表';
COMMENT ON COLUMN write_record_data.id IS 'id标识';
COMMENT ON COLUMN write_record_data.create_record_id IS '创建记录id';
COMMENT ON COLUMN write_record_data.school_id IS '学校id';
COMMENT ON COLUMN write_record_data.college IS '学院';
COMMENT ON COLUMN write_record_data.date IS '记录时间';
COMMENT ON COLUMN write_record_data.current_num IS '当前记录总数';
COMMENT ON COLUMN write_record_data.engaged_num IS '从事本专业的人数';
COMMENT ON COLUMN write_record_data.work_num IS '工作的人数';
COMMENT ON COLUMN write_record_data.study_num IS '学习人数';
COMMENT ON COLUMN write_record_data.college_num IS '大学生人数';
COMMENT ON COLUMN write_record_data.graduate_num IS '研究生人数';
COMMENT ON COLUMN write_record_data.doctor_num IS '博士人数';
COMMENT ON COLUMN write_record_data.hometown_num IS '在家乡人数';
COMMENT ON COLUMN write_record_data.salary0_num IS '5000以下';
COMMENT ON COLUMN write_record_data.salary1_num IS '5000~8000';
COMMENT ON COLUMN write_record_data.salary2_num IS '8000~12000';
COMMENT ON COLUMN write_record_data.salary3_num IS '12000~20000';
COMMENT ON COLUMN write_record_data.salary4_num IS '20000以上';
COMMENT ON COLUMN write_record_data.create_time IS '记录添加时间';