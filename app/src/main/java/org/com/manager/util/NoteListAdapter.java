package org.com.manager.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.bean.NoteModel;
import org.com.manager.bean.StepsModel;

import java.util.List;

/**
 * Created by xi.cheng on 2016/5/10.
 */
public class NoteListAdapter extends BaseAdapter {
    Context context;
    List<NoteModel> noteModels;

    public NoteListAdapter(Context context, List<NoteModel> noteModels) {
        this.context = context;
        this.noteModels = noteModels;
    }

    @Override
    public int getCount() {
        return noteModels.size();
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
        public TextView noteTitle;
        public TextView noteTime;
        public TextView noteContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.note_item,
                    parent, false);
            holder = new ViewHolder();
            holder.noteTitle = (TextView) view.findViewById(R.id.note_title);
            holder.noteTime = (TextView) view.findViewById(R.id.note_time);
            holder.noteContent = (TextView) view.findViewById(R.id.note_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        holder.noteTitle.setText(noteModels.get(position).getNoteTitle());
        holder.noteTime.setText(noteModels.get(position).getNoteTime());
        holder.noteContent.setText(noteModels.get(position).getNoteContent());
        return view;
    }
}
