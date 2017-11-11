package lava.bluepay.com.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import lava.bluepay.com.dbtest.bean.User;
import lava.bluepay.com.dbtest.sqlite.BaseDao;
import lava.bluepay.com.dbtest.sqlite.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view){
        BaseDao<User> userBaseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        userBaseDao.insert(new User(1,"could","2222"));
        Toast.makeText(this,"finish done!",Toast.LENGTH_SHORT).show();
    }
}
