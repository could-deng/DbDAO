package lava.bluepay.com.dbtest.viewUtils;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by bluepay on 2017/11/17.
 */

public class ListenerInvocation implements InvocationHandler {

    //真正代理的对象
    private Context context;
    //onClick
    private Map<String,Method> methodMap;

    public ListenerInvocation(Context context, Map<String,Method> methodMap) {
        this.context = context;
        this.methodMap = methodMap;
    }

    //只要view点击了 OnClickListener接口
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        Method mtd = methodMap.get(name);
        if(mtd == null){
            //不需要代理
            return method.invoke(proxy,args);
        }else{
            //真正代理的方法
            return mtd.invoke(context,args);
        }
//        onClick.setAccessible(true);

    }
}
