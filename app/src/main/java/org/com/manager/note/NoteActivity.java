package org.com.manager.note;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.com.manager.R;
import org.com.manager.bean.NoteModel;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.database.NoteTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.MyAlertDialog;
import org.com.manager.util.NoteListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 便签
 */
public class NoteActivity extends Activity {

    @Bind(R.id.edit_note)
    FloatingActionButton editNote;

    @Bind(R.id.note_remind_entrance)
    ImageView noteRemindEntrance;

    @Bind(R.id.note_list)
    ListView noteListView;

    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    private List<NoteModel> noteList;

    private Dao<NoteTable, String> noteDao = null;
    private ProgressDialog progressDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);
        initNoteList();
    }

    private void initNoteList() {
        noteList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        noteListNet();

    }

    private void noteListNet() {
        ManagerApplication.getInstance().getApiHttpClient()
                .noteListNet(ManagerApplication.getInstance().getUserId(),
                        new AsyncApiResponseHandler(NoteActivity.this) {
                            @Override
                            public void onApiResponse(JSONObject response) {
                                super.onApiResponse(response);
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                try {
                                    if (response != null) {
                                        JSONArray jsonArray = response.getJSONArray("data");
                                        List<NoteModel> noteModels = JSON.parseArray(jsonArray.toString(),
                                                NoteModel.class);
                                        showData(noteModels);
                                    }
                                } catch (JSONException e) {
                                    showData(null);
                                }
                            }
                        });
    }

    private void showData(List<NoteModel> noteModels) {
        if (noteModels!=null&&noteModels.size() != 0) {
            noteList = noteModels;
            noDataLayout.setVisibility(View.GONE);
            noteListView.setVisibility(View.VISIBLE);
            noteListView.setAdapter(new NoteListAdapter(NoteActivity.this, noteModels));
            noteListView.setOnItemClickListener(new itemOnClick());
            noteListView.setOnItemLongClickListener(new itemLongOnClick());
        } else {
            noteListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

   /* */

    /**
     * 从数据获得便签
     *//*
    private ArrayList<HashMap<String, Object>> getNotesFromDB() {

        ArrayList<HashMap<String, Object>> noteListTmp = new ArrayList<>();
        try {
            noteDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(NoteTable.class);
            for (NoteTable noteTable : noteDao) {

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(FrameUtils.IT_NOTE_ID, noteTable.getNoteId());
                hashMap.put(FrameUtils.IT_NOTE_TITLE, noteTable.getNoteTitle());
                hashMap.put(FrameUtils.IT_NOTE_TIME, noteTable.getNoteTime());
                hashMap.put(FrameUtils.IT_NOTE_REMIND_TIME, noteTable.getNoteRemindTime());
                hashMap.put(FrameUtils.IT_NOTE_CONTENT, noteTable.getNoteContent());
                noteListTmp.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteListTmp;
    }*/
    private void deleteNoteNet(int noteId) {
        ManagerApplication.getInstance().getApiHttpClient().noteDeleteNet(
                ManagerApplication.getInstance().getUserId(),noteId,
                new AsyncApiResponseHandler(NoteActivity.this){
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                    }
                });
    }
    /**
     * 删除
     */
  /*  private void deleteNote(int noteId) {
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
    }*/

    /**
     * 监听器，单击进入新增便签
     */
    class itemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NoteModel noteModel = noteList.get(position);
            Intent intent = new Intent();
            intent.setClass(NoteActivity.this, NoteEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(FrameUtils.IT_NOTE_MAP, noteModel);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    /**
     * 长按删除
     */
    class itemLongOnClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            int noteId = noteList.get(position).getId();
            alertDlg(noteId);
            return true;
        }
    }

    /**
     * 进入提醒便签页面（监听器）
     */
    @OnClick(R.id.note_remind_entrance)
    public void gotoNoteRemind() {
        Intent intent = new Intent();
        intent.setClass(NoteActivity.this, RemindNoteActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @OnClick(R.id.edit_note)
    public void gotoNoteEdit() {
        NoteModel noteModel=new NoteModel();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FrameUtils.IT_NOTE_MAP, noteModel);
        intent.putExtras(bundle);
        intent.setClass(NoteActivity.this, NoteEditActivity.class);
        startActivityForResult(intent, 1);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 是否删除该便签
     */
    private void alertDlg(final int noteId) {
        final MyAlertDialog alertDialog = new MyAlertDialog(NoteActivity.this,
                false);
        alertDialog.show();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("是否删除该便签？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNoteNet(noteId);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                initNoteList();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            NoteActivity.this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
