package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by  jie.hua on 2016/4/24.
 * 推荐菜单详细
 */
public class RecommendItemModel implements Serializable {
    /**
     * 标签id
     */
    private String flagId;
    /**
     * 标签名称
     */
    private String flagName;
    /**
     * 食谱标题
     */
    private String recipesTitle;
    /**
     * 食谱简介
     */
    private String recipesImtro;
    /**
     * 食谱图片
     */
    private String recipesImg;

    public RecommendItemModel() {
    }

    public RecommendItemModel(String flagId, String flagName,
                              String recipesTitle, String recipesImtro,
                              String recipesImg) {
        this.flagId = flagId;
        this.flagName = flagName;
        this.recipesTitle = recipesTitle;
        this.recipesImtro = recipesImtro;
        this.recipesImg = recipesImg;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public String getRecipesImg() {
        return recipesImg;
    }

    public void setRecipesImg(String recipesImg) {
        this.recipesImg = recipesImg;
    }

    public String getFlagId() {
        return flagId;
    }

    public void setFlagId(String flagId) {
        this.flagId = flagId;
    }

    public String getRecipesTitle() {
        return recipesTitle;
    }

    public void setRecipesTitle(String recipesTitle) {
        this.recipesTitle = recipesTitle;
    }

    public String getRecipesImtro() {
        return recipesImtro;
    }

    public void setRecipesImtro(String recipesImtro) {
        this.recipesImtro = recipesImtro;
    }
}
