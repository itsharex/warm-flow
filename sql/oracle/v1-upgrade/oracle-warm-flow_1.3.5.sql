UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@eq@@|', 'eq|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@eq@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@ge@@|', 'ge|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@ge@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@gt@@|', 'gt|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@gt@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@le@@|', 'le|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@le@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@like@@|', 'like|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@like@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@lt@@|', 'lt|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@lt@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@ne@@|', 'ne|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@ne@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@notNike@@|', 'notNike|');
UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@notNike@@', '|');

UPDATE FLOW_SKIP SET SKIP_CONDITION = REPLACE(SKIP_CONDITION, '@@spel@@|', 'spel|');

ALTER TABLE flow_node ADD any_node_skip VARCHAR2(100) DEFAULT NULL;

COMMENT ON COLUMN flow_node.skip_any_node IS '是否可以退回任意节点（Y是 N否）即将删除';
COMMENT ON COLUMN flow_node.any_node_skip IS '任意结点跳转';
