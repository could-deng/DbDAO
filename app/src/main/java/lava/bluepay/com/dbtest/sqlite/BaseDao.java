package lava.bluepay.com.dbtest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 数据库实现类
 */

public class BaseDao<T> implements IBaseDao<T>{

    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;

    //持有操作数据库所对应的java类
    private Class<T> entityClass;

    //表名
    private String tableName;

    //缓存map<列名,注释反射对象的变量>
    private HashMap<String,Field> catchMap = null;

    //标记是否初始化
    private boolean isInit = false;

    public BaseDao() {
    }

    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;

        if(!isInit) {
            //自动建表
            //取到表名 tableName ="tb_user"
            tableName = entityClass.getAnnotation(DbTable.class).value();
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //确定表字段名
            //create table if not exist db_user(_user integer,name varchar(15),password varchar(20));

            String createTableSql = getCreateTableSql();

            Log.e("TT",createTableSql);
            this.sqLiteDatabase.execSQL(createTableSql);

            this.sqLiteDatabase.execSQL("create table if not exists autoSendRecord (imsi varchar(15) primary key, extendmsg varchar(15));");

            //初始化缓存
            catchMap = new HashMap<>();
            initCacheMap();

            isInit = true;
        }

        return isInit;
    }

    /**
     * 初始化缓存(减少注释反射的次数，性能优化)
     */
    private void initCacheMap(){
        //如果得到数据库的所有字段名（查空表）
        String sql = "select * from "+tableName+" limit 1,0";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);

        String[] columnNames = cursor.getColumnNames();

        //todo 不能使用entityClass.getFields(),否则数组为空，只能用getDeclaredFields(),获取自己定义的Field
        Field[] columnFields = entityClass.getDeclaredFields();
        for(String columnName:columnNames){
            Field resultField = null;
            for(Field field:columnFields){
                String fieldAnnotionName = field.getAnnotation(DbField.class).value();
                if(columnName.equals(fieldAnnotionName)){
                    resultField = field;
                    break;
                }
            }
            if(resultField!=null){
                catchMap.put(columnName,resultField);
            }
        }
    }

    /**
     * 执行创建表
     * @return
     */
    private String getCreateTableSql(){
        //create table if not exist db_user(_user integer,name varchar(15),password varchar(20));

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName+" (");
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field:fields){
            Class type = field.getType();
            if(type.equals(String.class)) {
                stringBuffer.append(field.getAnnotation(DbField.class).value()+" TEXT,");
            }
            else if(type.equals(Integer.class)){
                stringBuffer.append(field.getAnnotation(DbField.class).value()+" INTEGER,");
            }
            else if(type.equals(Long.class)){
                stringBuffer.append(field.getAnnotation(DbField.class).value()+" LONG,");
            }
            else if(type.equals(Double.class)){
                stringBuffer.append(field.getAnnotation(DbField.class).value()+" DOUBLE,");
            }
            else if(type.equals(Byte.class)){
                stringBuffer.append(field.getAnnotation(DbField.class).value()+" BLOB,");
            }else{
                continue;
            }
        }
        if((stringBuffer.charAt(stringBuffer.length()-1)) == ','){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        stringBuffer.append(");");

        return stringBuffer.toString();
    }

    @Override
    public long insert(T entity) {
        //准备好ContentValue所需的数据
        Map<String,String> map = getValues(entity);

        //把map中的数据放入ContentValue中
        ContentValues values =getContentValues(map);
        sqLiteDatabase.insert(tableName,null,values);

        return 0;
    }

    /**
     * 按照数据库表字段的顺序，获取map<列名,存储的值>
     * @param entity
     * @return
     */
    private Map<String,String> getValues(T entity){
        HashMap<String ,String > map = new HashMap<>();

        Iterator<Field> fieldIterator = catchMap.values().iterator();

        while (fieldIterator.hasNext()){
            Field field = fieldIterator.next();
            //在注释反射中,如果该字段是私有的,必须要这样设置才能访问该Field
            field.setAccessible(true);

            try {
                Object object = field.get(entity);
                if(object == null){
                    continue;
                }
                String value = object.toString();
                //获取列名
                String key = field.getAnnotation(DbField.class).value();

                if(!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)){
                    map.put(key,value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;

    }

    /**
     * 将HashMap整理成插入数据库表的对象ContentValues
     * @param map
     * @return
     */
    private ContentValues getContentValues(Map<String,String> map){

        ContentValues contentValues = new ContentValues();
        Set keys =map.keySet();
        Iterator<String> iterator= keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            contentValues.put(key,value);
        }

        return contentValues;
    }
}
