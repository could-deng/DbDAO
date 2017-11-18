package lava.bluepay.com.dbtest.viewUtils;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bluepay on 2017/11/17.
 */

public class InjectUtils {

    public static void inject(Context context){
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }

    private static void injectLayout(Context context){
        int layoutId = 0;
        Class<?> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView == null){
            return;
        }
        layoutId = contentView.value();

        try {
            Method setContentView = clazz.getMethod("setContentView",int.class);
            if(setContentView == null){
                return;
            }
            setContentView.invoke(context,layoutId);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void injectView(Context context){
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if(viewInject == null){
                continue;
            }
            int viewId = viewInject.value();
            try {
                Method findViewById = clazz.getMethod("findViewById",int.class);
                View view = (View) findViewById.invoke(context,viewId);
                field.setAccessible(true);
                field.set(context,view);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectEvent(Context context){
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods){
            Annotation[] annotations = method.getAnnotations();
            for(Annotation annotation:annotations){
                Class<?> annotationType = annotation.annotationType();
                EventBase eventBase =annotationType.getAnnotation(EventBase.class);
                if(eventBase == null){
                    continue;
                }
                //设置事件监听的方法
                String listenerSetter = eventBase.listenerSetter();
                //事件监听的方法
                Class<?> listenerType = eventBase.listenerType();
                //回调
                String callback = eventBase.callbackMethod();

                Map<String,Method> methodMap = new HashMap<>();
                methodMap.put(callback,method);
                try {
                    Method value = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) value.invoke(annotation);

                    for(int id : viewIds){
                        Method findViewById = clazz.getMethod("findViewById",int.class);
                        View view = (View) findViewById.invoke(context,id);
                        if(view == null){
                            continue;
                        }
                        //目前位置事件三要素都具备。View也具备。

                        //listenerSetterMethod-->setonClickListener
                        Method listenerSetterMethod = view.getClass().getMethod(listenerSetter,listenerType);

                        //使用动态代理，
                        //[Button] ----setOnclickListener--->[onClickListener]-------(OnClick)------[Activity]
                        // 搞清楚要代理谁，要代理onClickListener类，因为实现onCLick*()回调。
                        // 但是代理模式是持有真正的对象引用.真正的对象引用就是Activity
                        //  Activity----------proxy---------(onClick)onClickListener
                        ListenerInvocation listenerInvocation = new ListenerInvocation(context,methodMap);

                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),new Class[]{listenerType},listenerInvocation);
                        listenerSetterMethod.invoke(view,proxy);

                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }



            }
        }

    }
}
