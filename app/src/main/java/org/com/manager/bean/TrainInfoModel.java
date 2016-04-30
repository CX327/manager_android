package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/22.
 * 车次信息
 * 车次查询嵌套
 */
public class TrainInfoModel implements Serializable {
    /**
     * 列车号
     */
    private String name;
    /**
     * 起点站
     */
    private String start;
    /**
     * 终点站
     */
    private String end;
    /**
     * 起始时间
     */
    private String starttime;
    /**
     * 到达时间
     */
    private String endtime;
    /**
     * 长度
     */
    private String mileage;

    public TrainInfoModel() {
    }

    public TrainInfoModel(String name, String start, String end, String starttime, String endtime, String mileage) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.starttime = starttime;
        this.endtime = endtime;
        this.mileage = mileage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
}
