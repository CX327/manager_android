package org.com.manager.note;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.com.manager.R;
import org.com.manager.bean.NoteModel;
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

public class RemindNoteActivity extends Activity {
    @Bind(R.id.note_list)
    ListView noteListView;

    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;


    private List<NoteModel> noteList;

    private ProgressDialog progressDialog = null;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        noteRemindListNet();

    }

    private void noteRemindListNet() {
        ManagerApplication.getInstance().getApiHttpClient()
                .noteRemindListNet(ManagerApplication.getInstance().getUserId(),
                        new AsyncApiResponseHandler(RemindNoteActivity.this) {
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
        if (noteModels != null && noteModels.size() != 0) {
            noteList = noteModels;
            noDataLayout.setVisibility(View.GONE);
            noteListView.setVisibility(View.VISIBLE);
            noteListView.setAdapter(new NoteListAdapter(RemindNoteActivity.this, noteModels));
            noteListView.setOnItemClickListener(new itemOnClick());
            noteListView.setOnItemLongClickListener(new itemLongOnClick());
        } else {
            noteListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 删除
     */
    private void deleteNote(int noteId) {
        ManagerApplication.getInstance().getApiHttpClient().noteDeleteNet(
                ManagerApplication.getInstance().getUserId(), noteId,
                new AsyncApiResponseHandler(RemindNoteActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                    }
                });
    }

    /**
     * 进入查看/编辑
     */
    class itemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NoteModel noteModel = noteList.get(position);
            Intent intent = new Intent();
            intent.setClass(RemindNoteActivity.this, NoteEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(FrameUtils.IT_NOTE_MAP, noteModel);
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
            int noteId = noteList.get(position).getId();
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
