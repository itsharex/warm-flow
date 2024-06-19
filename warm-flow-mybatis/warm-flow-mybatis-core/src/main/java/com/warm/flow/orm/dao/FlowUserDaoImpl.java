package com.warm.flow.orm.dao;

import com.warm.flow.core.FlowFactory;
import com.warm.flow.core.dao.FlowUserDao;
import com.warm.flow.core.invoker.FrameInvoker;
import com.warm.flow.core.utils.CollUtil;
import com.warm.flow.core.utils.StringUtils;
import com.warm.flow.orm.entity.FlowUser;
import com.warm.flow.orm.mapper.FlowUserMapper;
import com.warm.flow.orm.utils.TenantDeleteUtil;

import java.util.List;

/**
 * 流程用户Mapper接口
 *
 * @author warm
 * @date 2023-03-29
 */
public class FlowUserDaoImpl extends WarmDaoImpl<FlowUser> implements FlowUserDao<FlowUser> {

    @Override
    public FlowUserMapper getMapper() {
        return FrameInvoker.getBean(FlowUserMapper.class);
    }

    @Override
    public FlowUser newEntity() {
        return new FlowUser();
    }

    @Override
    public int deleteByTaskIds(List<Long> taskIdList) {
        FlowUser entity = TenantDeleteUtil.getEntity(newEntity());
        if (StringUtils.isNotEmpty(entity.getDelFlag())) {
            getMapper().updateByTaskIdsLogic(taskIdList, entity, FlowFactory.getFlowConfig().getLogicDeleteValue(),
                    entity.getDelFlag());
        }
        return getMapper().deleteByTaskIds(taskIdList, entity);
    }

    @Override
    public List<FlowUser> listByAssociatedAndTypes(List<Long> associateds, String[] types) {
        if (CollUtil.isNotEmpty(associateds) && associateds.size() == 1) {
            return getMapper().listByAssociatedAndTypes(types, null
                    , TenantDeleteUtil.getEntity(newEntity()).setAssociated(associateds.get(0)));
        }
        return getMapper().listByAssociatedAndTypes(types, associateds
                , TenantDeleteUtil.getEntity(newEntity()));
    }

    @Override
    public List<FlowUser> listByProcessedBys(Long associated, List<String> processedBys, String[] types) {
        if (CollUtil.isNotEmpty(processedBys) && processedBys.size() == 1) {
            return getMapper().listByProcessedBys(types, null, TenantDeleteUtil
                    .getEntity(newEntity()).setAssociated(associated).setProcessedBy(processedBys.get(0)));
        }
        return getMapper().listByProcessedBys(types, processedBys
                , TenantDeleteUtil.getEntity(newEntity()).setAssociated(associated));
    }
}
