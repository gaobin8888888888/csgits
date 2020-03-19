CREATE TABLE student_template(
     id serial primary key,
     sole varchar,
     no varchar,
     real_name varchar,
     password varchar,
     image_path varchar,
     school_id integer references  school(id),
     college varchar,
     class_no varchar,
     fine boolean default false,
     description varchar,
     credits integer default 0,
     grade integer,
     achievement jsonb,
     graduate boolean default false,
     create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE student_template OWNER TO postgres;
COMMENT ON TABLE  student_template IS '学生表';
COMMENT ON COLUMN  student_template.id IS 'id标识';
COMMENT ON COLUMN  student_template.sole IS '唯一id';
COMMENT ON COLUMN  student_template.no IS '学号';
COMMENT ON COLUMN  student_template.real_name IS '学生真实姓名';
COMMENT ON COLUMN  student_template.password IS '密码';
COMMENT ON COLUMN  student_template.image_path IS '学生头像地址';
COMMENT ON COLUMN  student_template.school_id IS '所在学校';
COMMENT ON COLUMN  student_template.college IS '所在学院';
COMMENT ON COLUMN  student_template.class_no IS '班级';
COMMENT ON COLUMN  student_template.fine IS '是否优秀校友';
COMMENT ON COLUMN  student_template.description IS '个人描述';
COMMENT ON COLUMN  student_template.credits IS '积分数';
COMMENT ON COLUMN  student_template.grade IS '年级';
COMMENT ON COLUMN  student_template.achievement IS '大学成绩';
COMMENT ON COLUMN  student_template.graduate IS '是否毕业';
COMMENT ON COLUMN  student_template.create_time IS '添加时间';
