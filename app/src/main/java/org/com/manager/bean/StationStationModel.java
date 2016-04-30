package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/22.
 * 站站查询结果
 */
public class StationStationModel implements Serializable {
    /**
     * 列车号；如T8
     */
    private String trainOpp;
    /**
     * 列车类型
     */
    private String train_typename;
    /**
     * 起点站
     */
    private String start_staion;
    /**
     * 终点站
     */
    private String end_station;
    /**
     * 离开时间
     */
    private String leave_time;
    /**
     * 到达时间
     */
    private String arrived_time;
    /**
     * 行程
     */
    private String mileage;

    public StationStationModel() {
    }

    public StationStationModel(String trainOpp, String train_typename,
                               String start_staion, String end_station,
                               String leave_time, String arrived_time,
                               String mileage) {
        this.trainOpp = trainOpp;
        this.train_typename = train_typename;
        this.start_staion = start_staion;
        this.end_station = end_station;
        this.leave_time = leave_time;
        this.arrived_time = arrived_time;
        this.mileage = mileage;
    }

    public String getTrainOpp() {
        return trainOpp;
    }

    public String getStart_staion() {
        return start_staion;
    }

    public void setStart_staion(String start_staion) {
        this.start_staion = start_staion;
    }

    public void setTrainOpp(String trainOpp) {
        this.trainOpp = trainOpp;
    }

    public String getTrain_typename() {
        return train_typename;
    }

    public void setTrain_typename(String train_typename) {
        this.train_typename = train_typename;
    }



    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(String arrived_time) {
        this.arrived_time = arrived_time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }
}
