package lava.bluepay.com.dbtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import lava.bluepay.com.dbtest.viewUtils.ContentView;
import lava.bluepay.com.dbtest.viewUtils.OnClick;
import lava.bluepay.com.dbtest.viewUtils.ViewInject;

/**
 * Created by bluepay on 2017/11/17.
 */

@ContentView(R.layout.activity_viewutils)
public class ViewUtilsActivity extends BaseActivity {

    @ViewInject(R.id.btn_1)
    Button btn_1;

    @ViewInject(R.id.btn_2)
    Button btn_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"."+btn_1,Toast.LENGTH_SHORT).show();
//        btn_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @OnClick(R.id.btn_1)
    private void onClick(View view){
        Toast.makeText(this,"TTT",Toast.LENGTH_SHORT).show();
    }

}
