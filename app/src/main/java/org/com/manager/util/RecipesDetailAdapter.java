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
import org.com.manager.bean.RecommendItemModel;
import org.com.manager.bean.StepsModel;
import org.com.manager.frame.ManagerApplication;

import java.util.List;

/**
 * Created by jie.hua on 2016/4/24.
 * 详细食谱Adapter
 */
public class RecipesDetailAdapter extends BaseAdapter {
    Context context;
    List<StepsModel> stepsModels;

    public RecipesDetailAdapter(Context context, List<StepsModel> stepsModels) {
        this.context = context;
        this.stepsModels = stepsModels;
    }

    @Override
    public int getCount() {
        return this.stepsModels.size();
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
        public ImageView recommendImg;
        public TextView recommendTitle;
        public TextView recommendImtro;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recipes_item,
                    parent, false);
            holder = new ViewHolder();
            holder.recommendImg = (ImageView) view.findViewById(R.id.recipes_item_image);
            holder.recommendTitle = (TextView) view.findViewById(R.id.recipes_item_title);
            holder.recommendImtro = (TextView) view.findViewById(R.id.recipes_item_imtro);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        holder.recommendTitle.setText("第" + (position + 1) + "步");
        holder.recommendImtro.setText(stepsModels.get(position).getStep());

        String albums = stepsModels.get(position).getImg();

        if (albums.contains("[")) {
            albums = albums.replace("[\"", "").replace("\"]", "");
        }
        ImageLoader.getInstance().displayImage(albums,
                holder.recommendImg,
                ManagerApplication.getInstance().getImageDisplayOption());
        return view;
    }
}
