package com.heima.ormlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.sql.Savepoint;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testAddUser(View view) {
        boolean isLoginOk = false;
//        User(int id, String name, float balance, int discount, int integral, String phone)
        User user = new User(001, "zhaoliying", 0.01f, 900, 5, "13812345678");
        //2.保存到sdcard或者rom中，使用sp/文件、sqlite
        //OrmLite，Orm是指javabean的对象和表的每一行对应，如User的name和表中的username一个属性
        TestOpenHelper takeoutOpenHelper = new TestOpenHelper(this);
        //事务属于一个数据库连接的会话中内部处理
        AndroidDatabaseConnection connection = new AndroidDatabaseConnection(takeoutOpenHelper.getWritableDatabase(), true);
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavePoint("start"); //事务的保存点
            connection.setAutoCommit(false); //事务处理需要手动提交

            Dao<User,Integer> userDao = takeoutOpenHelper.getDao(User.class);
//            userDao.createIfNotExists(user);  //如果之前已经有该用户，不再创建，更新用户信息
            //TODO:产品说当日登录用户有多少是新用户，多少是老用户
            User oldUser = userDao.queryForId(001);
            if(oldUser!=null){
                userDao.update(user);
//                TecentTjSdk.submitUserInfo(false); //老用户登录
                Log.e("login","老用户登录");
            }else{
                userDao.create(user);
//                TecentTjSdk.submitUserInfo(false); //新用户登录
                Log.e("login","新用户登录");
            }
            connection.commit(savepoint);
            isLoginOk = true;
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback(savepoint);
                Log.e("login","保存用户信息失败");
                isLoginOk = false;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if(isLoginOk){
            onLogin(true);
        }else{
            onLogin(false);
        }
    }

    public void onLogin(boolean isOk) {
        if(isOk){
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "请仔细检查用户信息", Toast.LENGTH_SHORT).show();
        }
    }
}
