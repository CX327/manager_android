package org.com.manager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie.hua on 2016/4/22.
 * 食谱详细
 */
public class RecipesDetailModel implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String title;
    /**
     * 所属标签
     */
    private String tags;
    /**
     * 介绍
     */
    private String imtro;
    /**
     * 主要材料
     */
    private String ingredients;
    /**
     * 食物清单
     */
    private String burden;
    /**
     * 图片
     */
    private String albums;
    /**
     * 步骤列表
     */
    private List<StepsModel> steps;

    public RecipesDetailModel() {
    }

    public RecipesDetailModel(String id, String title, String tags,
                              String imtro, String ingredients,
                              String burden, String albums, List<StepsModel> steps) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.imtro = imtro;
        this.ingredients = ingredients;
        this.burden = burden;
        this.albums = albums;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public List<StepsModel> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsModel> steps) {
        this.steps = steps;
    }
}
