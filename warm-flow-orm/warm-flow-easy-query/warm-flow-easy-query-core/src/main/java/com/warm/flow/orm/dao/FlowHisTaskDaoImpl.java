package com.warm.flow.orm.dao;

import com.easy.query.core.util.EasyArrayUtil;
import com.warm.flow.core.FlowFactory;
import com.warm.flow.core.dao.FlowHisTaskDao;
import com.warm.flow.core.enums.FlowStatus;
import com.warm.flow.core.enums.SkipType;
import com.warm.flow.core.utils.StringUtils;
import com.warm.flow.orm.entity.FlowHisTask;
import com.warm.flow.orm.entity.proxy.FlowHisTaskProxy;
import com.warm.flow.orm.utils.TenantDeleteUtil;

import java.util.List;
import java.util.Objects;

/**
 * 历史任务记录Mapper接口
 * @author link2fun
 */
public class FlowHisTaskDaoImpl extends WarmDaoImpl<FlowHisTask, FlowHisTaskProxy> implements FlowHisTaskDao<FlowHisTask> {

    /** 根据nodeCode获取未退回的历史记录 */
    @Override
    public List<FlowHisTask> getNoReject(String nodeCode, String targetNodeCode, Long instanceId) {
        FlowHisTask entity = TenantDeleteUtil.getEntity(newEntity());
        return entityQuery().queryable(entityClass())
            .where(proxy -> {
                proxy.nodeCode().eq(nodeCode); // 开始节点编码
                proxy.targetNodeCode().eq(StringUtils.isNotEmpty(targetNodeCode), targetNodeCode); // 目标节点编码
                proxy.instanceId().eq(instanceId); // 流程实例表id
                proxy.skipType().eq(SkipType.PASS.getKey()); // 跳转类型（PASS通过 REJECT退回 NONE无动作）
                proxy.delFlag().eq(StringUtils.isNotEmpty(entity.getDelFlag()), entity.getDelFlag()); // 逻辑删除过滤
                proxy.tenantId().eq(StringUtils.isNotEmpty(entity.getTenantId()),entity.getTenantId()); // 租户ID
            })
            .orderBy(hisTask -> hisTask.createTime().desc()).toList();
    }

    @Override
    public int deleteByInsIds(List<Long> instanceIds) {
        FlowHisTask entityFilter = TenantDeleteUtil.getEntity(newEntity());

        if (StringUtils.isNotEmpty(entityFilter.getDelFlag())) {
            // 有逻辑删除标识
            return (int) entityQuery().updatable(entityClass())
                .where(f -> f.instanceId().in(instanceIds))
                // 如果有租户则添加租户过滤
                .where(f -> f.tenantId().eq(StringUtils.isNotEmpty(entityFilter.getTenantId()), entityFilter.getTenantId()))
                // 更新为逻辑删除
                .setColumns(f -> f.delFlag().set(FlowFactory.getFlowConfig().getLogicDeleteValue()))

                .executeRows();


        }

        return (int) entityQuery().deletable(entityClass())
            .allowDeleteStatement(true)
            .where(proxy -> proxy.instanceId().in(instanceIds))
            .where(f -> {
                // 如果有租户， 则添加租户过滤
                f.tenantId().eq(StringUtils.isNotEmpty(entityFilter.getTenantId()),entityFilter.getTenantId());
            })
            .executeRows();
    }

    @Override
    public List<FlowHisTask> listByTaskIdAndCooperateTypes(Long taskId, Integer[] cooperateTypes) {


        FlowHisTask entity = newEntity();
        TenantDeleteUtil.getEntity(entity);


        return entityQuery().queryable(entityClass())
            .where(proxy -> {
                proxy.taskId().eq(Objects.nonNull(proxy.taskId()), taskId); // 任务表id
                proxy.cooperateType().in(EasyArrayUtil.isNotEmpty(cooperateTypes), cooperateTypes); //协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)
                proxy.delFlag().eq(StringUtils.isNotEmpty(entity.getDelFlag()), entity.getDelFlag()); // 删除标记
                proxy.tenantId().eq(StringUtils.isNotEmpty(entity.getTenantId()), entity.getTenantId()); // 租户ID
            })
            .toList();
    }

    @Override
    public Class<FlowHisTask> entityClass() {
        return FlowHisTask.class;
    }

    @Override
    public FlowHisTask newEntity() {
        return new FlowHisTask();
    }

    @Override
    public int delete(FlowHisTask entity) {
        TenantDeleteUtil.getEntity(entity);
        if (StringUtils.isNotEmpty(entity.getDelFlag())) {
            // 启用了逻辑删除
           return (int) entityQuery().updatable(entityClass())
                .where(proxy -> buildDeleteEqCondition(entity, proxy))
                .setColumns(f -> f.delFlag().eq(FlowFactory.getFlowConfig().getLogicDeleteValue()))
                .executeRows();
        }

        // 没有启用逻辑删除， 执行物理删除
        return (int) entityQuery().deletable(entityClass())
            .allowDeleteStatement(true)
            .where(proxy -> buildDeleteEqCondition(entity, proxy))
            .executeRows();

    }

    /** 参考 mybatis 实现 构建删除语句条件， 使用 = 拼接 */
    private static void buildDeleteEqCondition(FlowHisTask entity, FlowHisTaskProxy proxy) {
        proxy.id().eq(Objects.nonNull(entity.getId()), entity.getId()); // 主键
        proxy.nodeCode().eq(StringUtils.isNotEmpty(entity.getNodeCode()), entity.getNodeCode()); // 开始节点编码
        proxy.nodeName().eq(StringUtils.isNotEmpty(entity.getNodeName()), entity.getNodeName()); // 开始节点名称
        proxy.nodeType().eq(Objects.nonNull(entity.getNodeType()), entity.getNodeType()); // 开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
        proxy.targetNodeCode().eq(StringUtils.isNotEmpty(entity.getTargetNodeCode()), entity.getTargetNodeCode()); // 目标节点编码
        proxy.targetNodeName().eq(StringUtils.isNotEmpty(entity.getTargetNodeName()), entity.getTargetNodeName()); // 结束节点名称
        proxy.collaborator().eq(StringUtils.isNotEmpty(entity.getCollaborator()), entity.getCollaborator()); // 协作人(只有转办、会签、票签、委派)
        proxy.definitionId().eq(Objects.nonNull(entity.getDefinitionId()), entity.getDefinitionId()); // 对应flow_definition表的id
        proxy.instanceId().eq(Objects.nonNull(entity.getInstanceId()), entity.getInstanceId()); // 流程实例表id
        proxy.taskId().eq(Objects.nonNull(entity.getTaskId()), entity.getTaskId()); // 任务表id
        proxy.cooperateType().eq(Objects.nonNull(entity.getCooperateType()), entity.getCooperateType()); // 协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)
        proxy.flowStatus().eq(Objects.nonNull(entity.getFlowStatus()), entity.getFlowStatus()); // 流程状态（1审批中 2 审批通过 9已退回 10失效）
        proxy.skipType().eq(StringUtils.isNotEmpty(entity.getSkipType()), entity.getSkipType()); // 跳转类型（PASS通过 REJECT退回 NONE无动作）
        proxy.message().eq(StringUtils.isNotEmpty(entity.getMessage()), entity.getMessage()); // 审批意见
        proxy.ext().eq(StringUtils.isNotEmpty(entity.getExt()), entity.getExt()); // 业务详情 存业务类的json
        proxy.createTime().eq(Objects.nonNull(entity.getCreateTime()), entity.getCreateTime()); // 创建时间
        proxy.updateTime().eq(Objects.nonNull(entity.getUpdateTime()), entity.getUpdateTime()); // 更新时间
        proxy.tenantId().eq(StringUtils.isNotEmpty(entity.getTenantId()), entity.getTenantId()); // 租户ID
        proxy.delFlag().eq(StringUtils.isNotEmpty(entity.getDelFlag()), entity.getDelFlag()); // 删除标记
    }
}