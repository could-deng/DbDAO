package lava.bluepay.com.dbtest.sqlite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 用来映射字段（用来映射字段）
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)//运行时都一直存在该注释
public @interface DbField {
    String value();
}
