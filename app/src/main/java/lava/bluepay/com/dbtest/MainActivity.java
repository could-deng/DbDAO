package lava.bluepay.com.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import lava.bluepay.com.dbtest.bean.User;
import lava.bluepay.com.dbtest.sqlite.BaseDao;
import lava.bluepay.com.dbtest.sqlite.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {
    TextView tv_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = (TextView) findViewById(R.id.tv_test);
        testReflect();
    }

    public void onclick(View view){
        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        userBaseDao.insert(new User(1,"could","2222"));
        Toast.makeText(this,"finish done!",Toast.LENGTH_SHORT).show();
    }






    /**
     * java反射
     */
    private void testReflect(){
        try {
            Class c = Class.forName("lava.bluepay.com.dbtest.bean.User");
//            try {
//                User c_user = (User) c.newInstance();//newInstance()前提是有空的构造器。反射获得类对象
//
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
            Field[] fields = c.getDeclaredFields();
            StringBuffer sb = new StringBuffer();
            sb.append(Modifier.toString(c.getModifiers())).append(" class ").append(c.getSimpleName()+"{\n");
            for(Field field:fields){
                sb.append("\t");// 空格
                sb.append(Modifier.toString(field.getModifiers()) + " ");//修饰符，public static等等
                sb.append(field.getType().getSimpleName() + " ");
                sb.append(field.getName()+";\n ");
            }


//            // 获取所有的方法
//            Method[] ms = c.getDeclaredMethods();
//            //遍历输出所有方法
//            for (Method method : ms) {
//                //获取方法所有参数
//                Parameter[] parameters = method.getParameterTypes();
//                method.getTypeParameters()
//                String params = "";
//                if (parameters.length > 0) {
//                    StringBuffer stringBuffer = new StringBuffer();
//                    for (Parameter parameter : parameters) {
//                        stringBuffer.append(parameter.getType().getSimpleName() + " " + parameter.getName() + ",");
//                    }
//                    //去掉最后一个逗号
//                    params = stringBuffer.substring(0, stringBuffer.length() - 1);
//                }
//                System.err.println(Modifier.toString(method.getModifiers())
//                        + " " + method.getReturnType().getSimpleName()
//                        + " " + method.getName()
//                        + " (" +params  + ")");
//            }
//
            tv_test.setText(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
