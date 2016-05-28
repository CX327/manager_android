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
    private FlagModel flagModel;
    /**
     * 分类列表
     */
    private List<RecipesDetailModel> itemModels;

    public RecommendModel() {
    }

    public RecommendModel(FlagModel flagModel, List<RecipesDetailModel> itemModels) {
        this.flagModel = flagModel;
        this.itemModels = itemModels;
    }

    public FlagModel getFlagModel() {
        return flagModel;
    }

    public void setFlagModel(FlagModel flagModel) {
        this.flagModel = flagModel;
    }

    public List<RecipesDetailModel> getItemModels() {
        return itemModels;
    }

    public void setItemModels(List<RecipesDetailModel> itemModels) {
        this.itemModels = itemModels;
    }
}
