package lava.bluepay.com.dbtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import lava.bluepay.com.dbtest.viewUtils.InjectUtils;

/**
 * Created by bluepay on 2017/11/17.
 */

public class BaseActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);

    }
}
