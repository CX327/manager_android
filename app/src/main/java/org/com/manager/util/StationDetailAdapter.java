package org.com.manager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.bean.StationListModel;
import org.com.manager.bean.StationStationModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jie.hua on 2016/4/24.
 * 站点详细Adapter
 */
public class StationDetailAdapter extends BaseAdapter {
    Context context;
    List<StationListModel> numberDetailModel;

    public StationDetailAdapter(Context context, List<StationListModel> numberDetailModel) {
        this.context = context;
        this.numberDetailModel = numberDetailModel;
    }

    @Override
    public int getCount() {
        return this.numberDetailModel.size();
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
        TextView trainId;
        TextView stationName;
        TextView arrivedTime;
        TextView leaveTime;
        TextView residenceTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.station_detail_item,
                    parent, false);
            holder = new ViewHolder();
            holder.trainId = (TextView) view.findViewById(R.id.train_id);
            holder.stationName = (TextView) view.findViewById(R.id.station_name);
            holder.leaveTime = (TextView) view.findViewById(R.id.leave_time);
            holder.arrivedTime = (TextView) view.findViewById(R.id.arrived_time);
            holder.residenceTime = (TextView) view.findViewById(R.id.residence_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        holder.trainId.setText((position + 1) + "");
        holder.stationName.setText(numberDetailModel.get(position).getStation_name());
        holder.leaveTime.setText(numberDetailModel.get(position).getLeave_time());
        holder.arrivedTime.setText(numberDetailModel.get(position).getArrived_time());
        if (numberDetailModel.get(position).getStay().contains("-")) {
            holder.residenceTime.setText( "- 分钟");
        }else{
            holder.residenceTime.setText(numberDetailModel.get(position).getStay() + "分钟");
        }

        return view;
    }
}
