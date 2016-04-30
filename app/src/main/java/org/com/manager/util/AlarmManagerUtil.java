package org.com.manager.util;

import com.j256.ormlite.dao.Dao;

import org.com.manager.database.AlarmTable;
import org.com.manager.database.ManagerDBHelper;
import org.com.manager.frame.ManagerApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xi.cheng on 2016/4/29.
 * 查看提醒辅助类，暂没有显示出来
 */
public class AlarmManagerUtil {

    private static Dao<AlarmTable, String> alarmDao = null;

    public static void saveAlarmDB(String title, String content, int requestCode, String timeSet) {

        AlarmTable alarmTable = new AlarmTable(title, content, requestCode, timeSet);
        try {
            alarmDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(AlarmTable.class);
            alarmDao.createOrUpdate(alarmTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, Object>> getDataFromDB() {
        ArrayList<HashMap<String, Object>> alarmList = new ArrayList<>();
        try {
            ManagerDBHelper managerDBHelper = ManagerApplication.getInstance()
                    .getManagerDBHelper();
            alarmDao = managerDBHelper.getDao(AlarmTable.class);
            for (AlarmTable tmp : alarmDao) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(FrameUtils.LI_ALARM_ID, tmp.getAlarmId());
                hashMap.put(FrameUtils.LI_ALARM_TITLE, tmp.getTitle());
                hashMap.put(FrameUtils.LI_ALARM_CONTENT, tmp.getContent());
                hashMap.put(FrameUtils.LI_ALARM_CODE, tmp.getRequestCode());
                hashMap.put(FrameUtils.LI_ALARM_TIME, tmp.getTimeSet());
                alarmList.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarmList;
    }
}
