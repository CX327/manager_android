package org.com.manager.train;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import org.com.manager.R;
import org.com.manager.bean.StationStationModel;
import org.com.manager.bean.TrainInfoModel;
import org.com.manager.database.ManagerDBHelper;
import org.com.manager.database.NoteTable;
import org.com.manager.database.TrainCollectionTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.util.AlarmManagerUtil;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.MyAlertDialog;
import org.com.manager.util.TrainSearchResultAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的行程页
 */
public class MyTripFragment extends Fragment {
    @Bind(R.id.my_trip_list)
    ListView myTripList;

    private Dao<TrainCollectionTable, Object> trainCollectionDao;
    ArrayList<HashMap<String, Object>> collectionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_trip, container, false);
        ButterKnife.bind(this, view);
        getDataFromDB();
        //    AlarmManagerUtil.getDataFromDB();
        return view;
    }

    /**
     * 从本地数据库获得保存行程
     */
    private void getDataFromDB() {
        collectionList = new ArrayList<>();
        try {
            trainCollectionDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(TrainCollectionTable.class);
            for (TrainCollectionTable tmp : trainCollectionDao) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(FrameUtils.LI_TRAIN_COLLECTION_ID, tmp.getCollectionId());
                hashMap.put(FrameUtils.LI_TRAIN_OPP, tmp.getTrainOpp());
                hashMap.put(FrameUtils.LI_TRAIN_TYPE_NAME, tmp.getTrain_typename());
                hashMap.put(FrameUtils.LI_TRAIN_START_STATION, tmp.getStart_station());
                hashMap.put(FrameUtils.LI_TRAIN_END_STATION, tmp.getEnd_station());
                hashMap.put(FrameUtils.LI_TRAIN_START_TIME, tmp.getLeave_time());
                hashMap.put(FrameUtils.LI_TRAIN_END_TIME, tmp.getArrived_time());
                hashMap.put(FrameUtils.LI_TRAIN_MILEAGE, tmp.getMileage());
                collectionList.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showData();
    }

    /**
     * 显示数据
     */
    private void showData() {
        if (collectionList.size() != 0) {
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                    collectionList, R.layout.train_item, new String[]{
                    FrameUtils.LI_TRAIN_OPP, FrameUtils.LI_TRAIN_TYPE_NAME,
                    FrameUtils.LI_TRAIN_START_STATION, FrameUtils.LI_TRAIN_END_STATION,
                    FrameUtils.LI_TRAIN_START_TIME, FrameUtils.LI_TRAIN_END_TIME,
                    FrameUtils.LI_TRAIN_MILEAGE},
                    new int[]{R.id.train_opp, R.id.train_typename,
                            R.id.start_staion, R.id.end_station,
                            R.id.leave_time, R.id.arrived_time, R.id.mileage});
            myTripList.setAdapter(simpleAdapter);
            myTripList.setOnItemClickListener(new itemClickListener());
            myTripList.setOnItemLongClickListener(new itemLongClick());
        }
    }

    /**
     * list（监听器）
     */
    class itemClickListener implements AdapterView.OnItemClickListener {
        String trainNumber;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            this.trainNumber = collectionList.get(position).get(FrameUtils.LI_TRAIN_OPP).toString();
            intent.putExtra(FrameUtils.IT_TRAIN_DETAIL, this.trainNumber);
            intent.putExtra(FrameUtils.IT_TRAIN_ISCOLLECTION, true);
            intent.setClass(getActivity(), TrainDetailActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);

        }
    }

    /**
     * 长按删除
     */
    class itemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            int collectionId = (Integer) collectionList.get(position).get(FrameUtils.LI_TRAIN_COLLECTION_ID);
            alertDlg(collectionId);
            return true;
        }
    }

    /**
     * 是否删除该行程
     */
    private void alertDlg(final int collectionId) {
        final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(),
                false);
        alertDialog.show();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("是否删除该行程？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCollectionDB(collectionId);
                getDataFromDB();
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

    /**
     * 从本地数据库删除
     */
    private void deleteCollectionDB(int collectionId) {
        try {
            DeleteBuilder<TrainCollectionTable, Object> deleteBuilder = trainCollectionDao.deleteBuilder();
            deleteBuilder.where().eq("collectionId", collectionId);
            int returnValue = deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
