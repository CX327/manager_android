package org.com.manager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ManagerDBHelper extends DatabaseHelper {

    private final static String NAME = "managerdb";
    private final static int DATABASEVERSION = 1;

    public ManagerDBHelper(Context context) {
        super(context, NAME, null, DATABASEVERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        // TODO Auto-generated method stub
        super.onCreate(arg0, arg1);
        try {
            TableUtils.createTableIfNotExists(connectionSource,
                    NoteTable.class);
            TableUtils.createTableIfNotExists(connectionSource,
                    ConsumeTable.class);
            TableUtils.createTableIfNotExists(connectionSource,
                    TrainCollectionTable.class);
            TableUtils.createTableIfNotExists(connectionSource,
                    AlarmTable.class);


        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
                          int arg3) {
        // TODO Auto-generated method stub
        super.onUpgrade(arg0, arg1, arg2, arg3);
        try {
            TableUtils.dropTable(connectionSource, NoteTable.class,
                    true);
            TableUtils.dropTable(connectionSource, ConsumeTable.class,
                    true);
            TableUtils.dropTable(connectionSource, TrainCollectionTable.class,
                    true);
            TableUtils.dropTable(connectionSource, AlarmTable.class,
                    true);
        } catch (Exception e) {
            // TODO: handle exception
        }
        onCreate(arg0, arg1);
    }

}
