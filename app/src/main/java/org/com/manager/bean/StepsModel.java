package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/22.
 * <p/>
 * 详细步骤
 */
public class StepsModel implements Serializable {
    /**
     * 图片
     */
    String img;
    /**
     * 文字表述
     */
    String step;

    public StepsModel() {
    }

    public StepsModel(String img, String step) {
        this.img = img;
        this.step = step;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
