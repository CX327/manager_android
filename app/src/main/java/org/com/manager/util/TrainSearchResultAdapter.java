package org.com.manager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.com.manager.R;
import org.com.manager.bean.StationStationModel;
import org.com.manager.bean.StepsModel;
import org.com.manager.frame.ManagerApplication;

import java.util.List;

/**
 * Created by jie.hua on 2016/4/24.
 * 站点搜索结果Adapter
 */
public class TrainSearchResultAdapter extends BaseAdapter {
    Context context;
    List<StationStationModel> stationStationModels;

    public TrainSearchResultAdapter(Context context, List<StationStationModel> stationStationModels) {
        this.context = context;
        this.stationStationModels = stationStationModels;
    }

    @Override
    public int getCount() {
        return this.stationStationModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        TextView trainOpp;
        TextView trainTypename;
        TextView startStation;
        TextView endStation;
        TextView leaveTime;
        TextView arrivedTime;
        TextView mileage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.train_item,
                    parent, false);
            holder = new ViewHolder();
            holder.trainOpp = (TextView) view.findViewById(R.id.train_opp);
            holder.trainTypename = (TextView) view.findViewById(R.id.train_typename);
            holder.startStation = (TextView) view.findViewById(R.id.start_staion);
            holder.endStation = (TextView) view.findViewById(R.id.end_station);
            holder.leaveTime = (TextView) view.findViewById(R.id.leave_time);
            holder.arrivedTime = (TextView) view.findViewById(R.id.arrived_time);
            holder.mileage = (TextView) view.findViewById(R.id.mileage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        holder.trainOpp.setText(stationStationModels.get(position).getTrainOpp());
        holder.trainTypename.setText(stationStationModels.get(position).getTrain_typename());
        holder.startStation.setText(stationStationModels.get(position).getStart_staion());
        holder.endStation.setText(stationStationModels.get(position).getEnd_station());
        holder.leaveTime.setText(stationStationModels.get(position).getLeave_time());
        holder.arrivedTime.setText(stationStationModels.get(position).getArrived_time());
        holder.mileage.setText(stationStationModels.get(position).getMileage() + "km");


        return view;
    }
}
