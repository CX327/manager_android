package org.com.manager.database;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jie.hua on 2016/3/27.
 */
@DatabaseTable(tableName = "AlarmTable")
public class AlarmTable {
    /**
     * id
     */
    @DatabaseField(generatedId = true)
    int alarmId;
    /**
     * 标题
     */
    @DatabaseField
    String title;
    /**
     * 内容
     */
    @DatabaseField
    String content;
    /**
     * code
     */
    @DatabaseField
    int requestCode;
    /**
     * 时间
     */
    @DatabaseField
    String timeSet;

    public AlarmTable() {
    }

    public AlarmTable(int alarmId, String title,
                      String content, int requestCode, String timeSet) {
        this.alarmId = alarmId;
        this.title = title;
        this.content = content;
        this.requestCode = requestCode;
        this.timeSet = timeSet;
    }

    public AlarmTable(String title, String content, int requestCode, String timeSet) {
        this.title = title;
        this.content = content;
        this.requestCode = requestCode;
        this.timeSet = timeSet;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(String timeSet) {
        this.timeSet = timeSet;
    }
}
