package org.com.manager.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.com.manager.R;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.recipes.RecipesDetailActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie.hua on 2016/4/24.
 * 食谱标签列表Adapter
 */
public class RecipesFlagAdapter extends BaseAdapter {
    Context context;
    List<RecipesDetailModel> recipesDetailModels;

    public RecipesFlagAdapter(Context context, List<RecipesDetailModel> recipesDetailModels) {
        this.context = context;
        this.recipesDetailModels = recipesDetailModels;
    }

    @Override
    public int getCount() {
        return this.recipesDetailModels.size();
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
        public LinearLayout itemLayout;
        public ImageView itemImg;
        public TextView itemTitle;
        public TextView itemImtro;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recipes_item,
                    parent, false);
            holder = new ViewHolder();
            holder.itemLayout = (LinearLayout) view.findViewById(R.id.recipes_item_layout);
            holder.itemImg = (ImageView) view.findViewById(R.id.recipes_item_image);
            holder.itemTitle = (TextView) view.findViewById(R.id.recipes_item_title);
            holder.itemImtro = (TextView) view.findViewById(R.id.recipes_item_imtro);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        holder.itemTitle.setText(recipesDetailModels.get(position).getTitle());
        holder.itemImtro.setText(recipesDetailModels.get(position).getImtro());
        String albums = recipesDetailModels.get(position).getAlbums();

        if (albums.contains("[")) {
            albums = albums.replace("[\"", "").replace("\"]", "");
        }
        ImageLoader.getInstance().displayImage(albums,
                holder.itemImg,
                ManagerApplication.getInstance().getImageDisplayOption());

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(FrameUtils.IT_RECIPES_DETAIL, recipesDetailModels.get(position));
                intent.setClass(context, RecipesDetailActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
