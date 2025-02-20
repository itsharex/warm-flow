-- 替换 skip_condition 字段中的特定字符串
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@eq@@|', 'eq|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@eq@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@ge@@|', 'ge|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@ge@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@gt@@|', 'gt|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@gt@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@le@@|', 'le|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@le@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@like@@|', 'like|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@like@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@lt@@|', 'lt|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@lt@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@ne@@|', 'ne|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@ne@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@notNike@@|', 'notNike|');
UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@notNike@@', '|');

UPDATE flow_skip SET skip_condition = REPLACE(skip_condition, '@@spel@@|', 'spel|');

ALTER TABLE flow_node ADD COLUMN any_node_skip varchar(100) DEFAULT NULL;

COMMENT ON COLUMN flow_node.skip_any_node IS '是否可以退回任意节点（Y是 N否）即将删除';
COMMENT ON COLUMN flow_node.any_node_skip IS '任意结点跳转';

CREATE TABLE flow_form (
   id int8 NOT NULL,
   form_code VARCHAR(40) NOT NULL,
   form_name VARCHAR(100) NOT NULL,
   version VARCHAR(20) NOT NULL,
   is_publish SMALLINT NOT NULL DEFAULT 0,
   form_type SMALLINT DEFAULT 0,
   form_path VARCHAR(100) DEFAULT NULL,
   form_content TEXT DEFAULT NULL,
   ext VARCHAR(400) DEFAULT NULL,
   create_time TIMESTAMP DEFAULT NULL,
   update_time TIMESTAMP DEFAULT NULL,
   del_flag CHAR(1) DEFAULT '0',
   tenant_id VARCHAR(40) DEFAULT NULL,
   PRIMARY KEY (id)
);

COMMENT ON TABLE flow_form IS '流程表单表';
COMMENT ON COLUMN flow_form.id IS '主键id';
COMMENT ON COLUMN flow_form.form_code IS '表单编码';
COMMENT ON COLUMN flow_form.form_name IS '表单名称';
COMMENT ON COLUMN flow_form.version IS '表单版本';
COMMENT ON COLUMN flow_form.is_publish IS '是否发布（0未发布 1已发布 9失效）';
COMMENT ON COLUMN flow_form.form_type IS '表单类型（0内置表单 存 form_content 1外挂表单 存form_path）';
COMMENT ON COLUMN flow_form.form_path IS '表单路径';
COMMENT ON COLUMN flow_form.form_content IS '表单内容';
COMMENT ON COLUMN flow_form.ext IS '表单扩展，用户自行使用';
COMMENT ON COLUMN flow_form.create_time IS '创建时间';
COMMENT ON COLUMN flow_form.update_time IS '更新时间';
COMMENT ON COLUMN flow_form.del_flag IS '删除标志';
COMMENT ON COLUMN flow_form.tenant_id IS '租户id';

ALTER TABLE flow_his_task ADD variable text NULL;
COMMENT ON COLUMN flow_his_task.variable IS '任务变量';
