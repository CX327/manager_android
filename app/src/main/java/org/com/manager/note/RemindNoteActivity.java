package org.com.manager.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.com.manager.R;
import org.com.manager.database.NoteTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.MyAlertDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemindNoteActivity extends Activity {
    @Bind(R.id.note_list)
    ListView noteListView;

    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    ArrayList<HashMap<String, Object>> noteList;

    private Dao<NoteTable, String> noteDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_note);
        ButterKnife.bind(this);
        initNoteList();
    }

    /**
     * 初始化列表
     */
    private void initNoteList() {
        noteList = new ArrayList<>();
        noteList = getNotesFromDB();
        if (noteList.size() != 0) {
            noDataLayout.setVisibility(View.GONE);
            noteListView.setVisibility(View.VISIBLE);
            SimpleAdapter simpleAdapter = new SimpleAdapter(RemindNoteActivity.this, noteList,
                    R.layout.note_item,
                    new String[]{FrameUtils.IT_NOTE_TITLE, FrameUtils.IT_NOTE_TIME, FrameUtils.IT_NOTE_CONTENT},
                    new int[]{R.id.note_title, R.id.note_time, R.id.note_content});
            noteListView.setAdapter(simpleAdapter);
            noteListView.setOnItemClickListener(new itemOnClick());
            noteListView.setOnItemLongClickListener(new itemLongOnClick());
        } else {
            noteListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 从本地数据库获得notes
     */
    private ArrayList<HashMap<String, Object>> getNotesFromDB() {

        ArrayList<HashMap<String, Object>> noteListTmp = new ArrayList<>();
        try {
            noteDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(NoteTable.class);
            for (NoteTable noteTable : noteDao) {

                if (!noteTable.getNoteRemindTime().equals("")) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put(FrameUtils.IT_NOTE_ID, noteTable.getNoteId());
                    hashMap.put(FrameUtils.IT_NOTE_TITLE, noteTable.getNoteTitle());
                    hashMap.put(FrameUtils.IT_NOTE_TIME, noteTable.getNoteTime());
                    hashMap.put(FrameUtils.IT_NOTE_REMIND_TIME, noteTable.getNoteRemindTime());
                    hashMap.put(FrameUtils.IT_NOTE_CONTENT, noteTable.getNoteContent());
                    noteListTmp.add(hashMap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteListTmp;
    }

    /**
     * 删除
     */
    private void deleteNote(int noteId) {
        try {
            noteDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(NoteTable.class);
            DeleteBuilder<NoteTable, String> deleteBuilder = noteDao.deleteBuilder();
            deleteBuilder.where().eq("noteId", noteId);
            int returnValue = deleteBuilder.delete();
            initNoteList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入查看/编辑
     */
    class itemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, Object> hashMap = noteList.get(position);
            Intent intent = new Intent();
            intent.setClass(RemindNoteActivity.this, NoteEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(FrameUtils.IT_NOTE_MAP, hashMap);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                initNoteList();
                break;
            default:
                break;
        }
    }

    /**
     * 长按删除
     */
    class itemLongOnClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            int noteId = (Integer) noteList.get(position).get(FrameUtils.IT_NOTE_ID);
            alertDlg(noteId);
            return true;
        }
    }


    /**
     * 是否删除该便签
     */
    private void alertDlg(final int noteId) {
        final MyAlertDialog alertDialog = new MyAlertDialog(RemindNoteActivity.this,
                false);
        alertDialog.show();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("是否删除该便签？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(noteId);
                alertDialog.cancel();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            RemindNoteActivity.this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
