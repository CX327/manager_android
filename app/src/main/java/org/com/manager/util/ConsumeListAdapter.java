package org.com.manager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.bean.ConsumeModel;
import org.com.manager.bean.ConsumeTypeEnum;
import org.com.manager.bean.NoteModel;

import java.util.List;

/**
 * Created by xi.cheng on 2016/5/10.
 */
public class ConsumeListAdapter extends BaseAdapter {
    Context context;
    List<ConsumeModel> consumeModels;
    /**
     * 当月支出
     */
    private float thisMonthPay = 0;
    /**
     * 当月收入
     */
    private float thisMonthIncome = 0;
    public ConsumeListAdapter(Context context, List<ConsumeModel> consumeModels) {
        this.context = context;
        this.consumeModels = consumeModels;
    }

    @Override
    public int getCount() {
        return consumeModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public float getThisMonthPay() {
        return thisMonthPay;
    }


    public float getThisMonthIncome() {
        return thisMonthIncome;
    }



    private class ViewHolder {
        public ImageView consumeType;
        public TextView consumeDate;
        public TextView consumeMoney;
        public TextView consumeRematk;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.accounting_cosume_item,
                    parent, false);
            holder = new ViewHolder();
            holder.consumeType = (ImageView) view.findViewById(R.id.accounting_consume_item_type);
            holder.consumeDate = (TextView) view.findViewById(R.id.accounting_consume_item_date);
            holder.consumeMoney = (TextView) view.findViewById(R.id.accounting_consume_item_money);
            holder.consumeRematk = (TextView) view.findViewById(R.id.accounting_consume_item_remarks);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        ConsumeTypeEnum consumeType = getConsumeTypeEnum(consumeModels.get(position).getConsumeTypeId());
        holder.consumeType.setImageResource(consumeType.getTypeImage());
        holder.consumeDate.setText(consumeModels.get(position).getConsumeTime());
        holder.consumeMoney.setText(consumeModels.get(position).getConsumeMoney() + "");
        holder.consumeRematk.setText(consumeModels.get(position).getRemarks());
        if (consumeModels.get(position).isConsumeIsPay()) {
            thisMonthPay += consumeModels.get(position).getConsumeMoney();
        } else {
            thisMonthIncome += consumeModels.get(position).getConsumeMoney();
        }
        return view;
    }

    /**
     * 获得消费类型枚举
     */
    public ConsumeTypeEnum getConsumeTypeEnum(int id) {
        for (ConsumeTypeEnum consumeTypeEnum : ConsumeTypeEnum.values()) {
            if (consumeTypeEnum.getTypeId() == id) {
                return consumeTypeEnum;
            }
        }
        return ConsumeTypeEnum.YIBAN;
    }
}
