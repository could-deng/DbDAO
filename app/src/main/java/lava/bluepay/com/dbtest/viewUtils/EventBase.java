package lava.bluepay.com.dbtest.viewUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间三要素(设置监听、事件类型、回调)
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    String listenerSetter();
    Class<?> listenerType();
    String callbackMethod();
}
