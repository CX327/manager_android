package org.com.manager.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by xi.cheng on 2016/4/27.
 */
@DatabaseTable(tableName = "TrainCollectionTable")
public class TrainCollectionTable {
    /**
     * 列车号；如T8
     */
    @DatabaseField(generatedId = true)
    int collectionId;
    /**
     * 行程
     */
    @DatabaseField
    long collectionTime;
    /**
     * 列车号；如T8
     */
    @DatabaseField
    String trainOpp;
    /**
     * 列车类型
     */
    @DatabaseField
    String train_typename;
    /**
     * 起点站
     */
    @DatabaseField
    String start_station;
    /**
     * 终点站
     */
    @DatabaseField
    String end_station;
    /**
     * 出发时间
     */
    @DatabaseField
    String leave_time;
    /**
     * 到达时间
     */
    @DatabaseField
    String arrived_time;
    /**
     * 行程
     */
    @DatabaseField
    String mileage;

    public TrainCollectionTable() {
    }


    public TrainCollectionTable(String trainOpp, String train_typename,
                                String start_station, String end_station, String leave_time,
                                String arrived_time, String mileage) {

        this.trainOpp = trainOpp;
        this.train_typename = train_typename;
        this.start_station = start_station;
        this.end_station = end_station;
        this.leave_time = leave_time;
        this.arrived_time = arrived_time;
        this.mileage = mileage;
    }

    public TrainCollectionTable(int collectionId, String trainOpp, String train_typename,
                                String start_station, String end_station,
                                String leave_time, String arrived_time, String mileage) {
        this.collectionId = collectionId;
        this.trainOpp = trainOpp;
        this.train_typename = train_typename;
        this.start_station = start_station;
        this.end_station = end_station;
        this.leave_time = leave_time;
        this.arrived_time = arrived_time;
        this.mileage = mileage;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getTrainOpp() {
        return trainOpp;
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

    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
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
