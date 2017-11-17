package lava.bluepay.com.dbtest.viewUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by bluepay on 2017/11/17.
 */

public class ListenerInvocation implements InvocationHandler {

    //代理activity
    private Object object;
    private Method onClick;

    public ListenerInvocation(Object object, Method onClick) {
        this.object = object;
        this.onClick = onClick;
    }

    //只要view点击了 OnClickListener接口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        onClick.setAccessible(true);
        return onClick.invoke(object,args);
    }
}
