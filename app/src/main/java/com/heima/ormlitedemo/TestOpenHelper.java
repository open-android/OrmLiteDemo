package com.heima.ormlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 1.1版本 1
 * 1.3版本 1
 * 2.1版本 2
 */

public class TestOpenHelper extends OrmLiteSqliteOpenHelper {
    public TestOpenHelper(Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //创建User表
        try {
            TableUtils.createTable(connectionSource, User.class);
//            TableUtils.createTable(connectionSource, RecepitAddressBean.class); //卸载重装，新用户
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //数据库版本变大时执行这里
//        try {
//            TableUtils.createTable(connectionSource, RecepitAddressBean.class);//覆盖安装 adb istall -r ，老用户
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
