package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/22.
 * 全国车站
 */
public class StationModel implements Serializable{
    /**
     * 站名
     */
    private String sta_name;
    /**
     * */
    private String sta_ename;
    /**
     * 代码
     */
    private String sta_code;

    public StationModel() {
    }

    public StationModel(String sta_name, String sta_ename, String sta_code) {
        this.sta_name = sta_name;
        this.sta_ename = sta_ename;
        this.sta_code = sta_code;
    }

    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getSta_ename() {
        return sta_ename;
    }

    public void setSta_ename(String sta_ename) {
        this.sta_ename = sta_ename;
    }

    public String getSta_code() {
        return sta_code;
    }

    public void setSta_code(String sta_code) {
        this.sta_code = sta_code;
    }
}
