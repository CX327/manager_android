package org.com.manager.database;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * 派生自OrmLiteSqliteOpenHelper数据库助手，通过此类操作数据库。创建和更新数据库可以分别在
 * {@link #onCreate(SQLiteDatabase, ConnectionSource)}
 * {@link #onUpgrade(SQLiteDatabase, ConnectionSource, int, int)} 添加响应代码。表格操作参照
 * {@link TableUtils}类的使用说明。 使用举例：
 * 
 * <pre>
 * Dao<Account, Integer> accountDao = null;
 * try {
 * accountDao =this.db.getDao(Account.class);
 * }
 * catch (SQLException e1) {
 *  Log.e(getClass().getSimpleName().toString(),"qurey account dao error!", e1); 
 *  return; 
 * }
 * Account ac = null; 
 * for (Account account : accountDao) { 
 * ac = account;
 * }
 * 
 * <pre>
 * @author weixvn
 */
public abstract class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	/**
	 * 数据库帮助类的构造方法
	 * 
	 * @param context
	 *            程序上下文
	 * @param databaseName
	 *            数据库名称
	 * @param factory
	 *            一般传入null
	 * @param databaseVersion
	 *            数据库版本
	 */
	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, File configFile) {
		super(context, databaseName, factory, databaseVersion, configFile);
	}

	public DatabaseHelper(Context arg0, String arg1, CursorFactory arg2,
			int arg3, InputStream arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

	public DatabaseHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, int configFileId) {
		super(context, databaseName, factory, databaseVersion, configFileId);
	}

	/**
	 * 数据库创建时调用此方法。<br>
	 * 可以在此中创建数据库的表。如：TableUtils.createTableIfNotExists(connectionSource,
	 * Account.class); 就在数据库中创建了一张Account表
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {

	}

	/**
	 * 数据库版本更新时调用此方法。在此方法中做一些数据库版本更新的相关操作。
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {

	}
}
