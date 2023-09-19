package com.warm.flow.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程结点对象 flow_node
 *
 * @author warm
 * @date 2023-03-29
 */
public class FlowNode extends FlowEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 结点类型（0开始结点 1中间结点 2结束结点 3互斥网关 4并行网关）
     */
    private Integer nodeType;

    /**
     * 流程id
     */
    private Long definitionId;

    /**
     * 流程结点编码   每个流程的nodeCode是唯一的,即definitionId+nodeCode唯一,在数据库层面做了控制
     */
    private String nodeCode;

    /**
     * 流程结点名称
     */
    private String nodeName;

    /**
     * 权限标识（权限类型:权限标识，可以多个，如role:1,role:2)
     */
    private String permissionFlag;

    /**
     * 版本
     */
    private String version;

    /**
     * 跳转规则描述
     */
    private String skipDescribe;

    /**
     * 跳转条件
     */
    List<FlowSkip> skipList = new ArrayList<>();

    public Integer getNodeType() {
        return nodeType;
    }

    public FlowNode setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public Long getDefinitionId() {
        return definitionId;
    }

    public FlowNode setDefinitionId(Long definitionId) {
        this.definitionId = definitionId;
        return this;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public FlowNode setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public FlowNode setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public String getPermissionFlag() {
        return permissionFlag;
    }

    public FlowNode setPermissionFlag(String permissionFlag) {
        this.permissionFlag = permissionFlag;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public FlowNode setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getSkipDescribe() {
        return skipDescribe;
    }

    public FlowNode setSkipDescribe(String skipDescribe) {
        this.skipDescribe = skipDescribe;
        return this;
    }

    public List<FlowSkip> getSkipList() {
        return skipList;
    }

    public FlowNode setSkipList(List<FlowSkip> skipList) {
        this.skipList = skipList;
        return this;
    }
}