# OrmLiteDemo
OrmLiteDemo

Orm是对象与数据的映射，简单说就是让javabean和数据库表建立绑定关系。
OrmLite是对android原生SQLite的封装，OrmLiteDemo是Orm快速实现的示例工程。

开始

在build.gradle添加依赖
compile 'com.j256.ormlite:ormlite-android:5.0'
 
需要的权限
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>

第一步：建立Orm关系。以User.class为例，对应数据库的t_user表。
	@DatabaseField(id = true)
	private int id;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "balance")
	private float balance;
	@DatabaseField(columnName = "discount")
	private int discount;
	@DatabaseField(columnName = "integral")
	private int integral;
	@DatabaseField(columnName = "phone")
	private String phone;
注意事项：1.必须要添加无参构造方法；
	  2.id=true时id可以由我们自己赋值，如果使用generateId=true，则id由数据库自己维护，自动增长效果，auto increment。

第二步：创建OpenHelper继承OrmLiteOpenHelper，OrmLiteOpenHelper是SQLiteOpenHelper的子类。
在初始化数据库的oncreate中创建数据库。由于做了Orm，则指定User.class就可以创建t_user表。
TableUtils.createTable(connectionSource, User.class);

第三步：由于OrmLite封装了SQLite，所以不用再拼装SQL语句字段了，直接获取DAO层插入User对象即可。

	1.先获取数据库连接：AndroidDatabaseConnection connection = new AndroidDatabaseConnection(takeoutOpenHelper.getWritableDatabase(), true);
	2.开启事务：
		SavePoint savepoint = connection.setSavePoint("start"); //事务的保存点
                  connection.setAutoCommit(false); //事务处理需要手动提交
	3.获取DAO层：
		Dao<User,Integer> userDao = takeoutOpenHelper.getDao(User.class);
	4.执行增删改插。插入前要判断是否已经有该用户，做防重复处理，所以有两个步骤。
		User oldUser = userDao.queryForId(001);
		 if(oldUser!=null){
                userDao.update(user);
//                TecentTjSdk.submitUserInfo(false); //老用户登录
                Log.e("login","老用户登录");
            }else{
                userDao.create(user);
//                TecentTjSdk.submitUserInfo(true); //新用户登录
                Log.e("login","新用户登录");
            }
	5.提交事务。connection.commit(savepoint); 
	6.如果出现异常，回滚事务即可。connection.rollback(savepoint);

最后说明：可以使用userDao.createIfNotExists(user);代替上面两个步骤，这样就可以不用开启事务了，因为这个API本身封装了事务，
而手动事务可以更灵活，比如步骤内部增加新的逻辑（代码统计，其他业务逻辑等等，如上方注释）。
