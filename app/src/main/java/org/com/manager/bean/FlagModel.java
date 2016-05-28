package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/21.
 * 标签
 */
public class FlagModel implements Serializable {

    private String id;
    private String parentId;
    private String name;

    public FlagModel() {
    }

    public FlagModel(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
