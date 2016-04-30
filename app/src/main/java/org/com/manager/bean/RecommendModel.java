package org.com.manager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by  jie.hua on 2016/4/24.
 * 推荐
 */
public class RecommendModel implements Serializable {
    /**
     * 推荐分类名称
     */
    private String title;
    /**
     * 分类列表
     */
    private List<RecommendItemModel> itemModels;

    public RecommendModel() {
    }

    public RecommendModel(String title, List<RecommendItemModel> itemModels) {
        this.title = title;
        this.itemModels = itemModels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RecommendItemModel> getItemModels() {
        return itemModels;
    }

    public void setItemModels(List<RecommendItemModel> itemModels) {
        this.itemModels = itemModels;
    }
}
