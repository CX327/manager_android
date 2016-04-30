package org.com.manager.bean;

import java.io.Serializable;

/**
 * Created by jie.hua on 2016/4/22.
 * 列车详细 站次
 */
public class StationListModel implements Serializable {
    /**
     * 序号
     */
    private String train_id;
    /**
     * 站名
     */
    private String station_name;
    /**
     * 到达时间
     */
    private String arrived_time;
    /**
     * 离开时间
     */
    private String leave_time;
    /**
     * 停留
     */
    private String stay;

    public StationListModel() {
    }

    public StationListModel(String train_id, String station_name,
                            String arrived_time, String leave_time, String stay) {
        this.train_id = train_id;
        this.station_name = station_name;
        this.arrived_time = arrived_time;
        this.leave_time = leave_time;
        this.stay = stay;
    }

    public String getTrain_id() {
        return train_id;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getArrived_time() {
        return arrived_time;
    }

    public void setArrived_time(String arrived_time) {
        this.arrived_time = arrived_time;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getStay() {
        return stay;
    }

    public void setStay(String stay) {
        this.stay = stay;
    }
}
