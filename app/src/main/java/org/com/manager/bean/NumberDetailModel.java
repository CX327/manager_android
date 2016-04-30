package org.com.manager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie.hua on 2016/4/22.
 * 列车车次查询结果
 */
public class NumberDetailModel implements Serializable {
    /**
     * 列车信息
     */
    TrainInfoModel train_info;
    /**
     * 站列表
     */
    List<StationListModel> station_list;

    public NumberDetailModel() {
    }

    public NumberDetailModel(TrainInfoModel train_info, List<StationListModel> station_list) {
        this.train_info = train_info;
        this.station_list = station_list;
    }

    public TrainInfoModel getTrain_info() {
        return train_info;
    }

    public void setTrain_info(TrainInfoModel train_info) {
        this.train_info = train_info;
    }

    public List<StationListModel> getStation_list() {
        return station_list;
    }

    public void setStation_list(List<StationListModel> station_list) {
        this.station_list = station_list;
    }
}
