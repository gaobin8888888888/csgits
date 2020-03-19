CREATE TABLE record_template(
     id serial primary key,
     sole varchar,
     related boolean,
     type integer,
     degree integer,
     place integer,
     home integer,
     salary double,
     comment varchar,
     create_time timestamp default current_timestamp
)
  WITH(
  OIDS=FALSE
      );
ALTER TABLE record_template OWNER TO postgres;
COMMENT ON TABLE  record_template IS '记录表';
COMMENT ON COLUMN  record_template.id IS 'id标识';
COMMENT ON COLUMN  record_template.sole IS '学生唯一id';
COMMENT ON COLUMN  record_template.related IS '是否从事与本专业相关的工作或者学习';
COMMENT ON COLUMN  record_template.type IS '目前的状态、工作或者学习';
COMMENT ON COLUMN  record_template.degree IS '目前最高的学历';
COMMENT ON COLUMN  record_template.place IS '所处的地区';
COMMENT ON COLUMN  record_template.home IS '是否在家乡';
COMMENT ON COLUMN  record_template.salary IS '工资';
COMMENT ON COLUMN  record_template.comment IS '备注';
COMMENT ON COLUMN  record_template.create_time IS '添加时间';
