package lava.bluepay.com.dbtest.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * 通过单例工厂让调用层获取数据库操作所需要用到的对象
 */

public class BaseDaoFactory {

    private static final BaseDaoFactory ourInstance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return ourInstance;
    }

    private String sqliteDatabasePath = "";
    private SQLiteDatabase sqLiteDatabase = null;
    private BaseDaoFactory() {
        sqliteDatabasePath = "data/data/lava.bluepay.com.dbtest/could.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath,null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass){
        BaseDao baseDao = null;
        try {
            //TODO ???
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase,entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseDao;
    }


}
