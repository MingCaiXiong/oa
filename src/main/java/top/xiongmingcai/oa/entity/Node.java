package top.xiongmingcai.oa.entity;

import java.io.Serializable;

/**
 * (Node)实体类
 *
 * @author makejava
 * @since 2021-04-06 19:47:00
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 450842639920314890L;
    /**
    * 节点编号
    */
    private Long nodeId;
    /**
    * 节点类型 1-模块 2-功能
    */
    private Integer nodeType;
    /**
    * 节点名称
    */
    private String nodeName;
    /**
    * 功能地址
    */
    private String url;
    /**
    * 节点编码,用于排序
    */
    private Integer nodeCode;
    /**
    * 上级节点编号
    */
    private Long parentId;


    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(Integer nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


}