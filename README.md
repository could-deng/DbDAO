## **注解反射的使用**
- 搭建数据库框架
- 仿照xUtils中viewUtils搭建视图框架（附加动态代理）


## Java反射（Reflection）定义

<p>Java反射机制是指在运行状态中</p>

- 对于任意一个类，都能知道这个类的所有属性和方法；
- 对于任何一个对象，都能够调用它的任何一个方法和属性；

<p>这样动态获取新的以及动态调用对象方法的功能就叫做反射。</p>

-java.lang.Class、编译后的class文件对象、Class c = Class.forName(java.lang.String)
<p>获取类的三种方法:</p>
<p>Class c = Class.forName("java.lang.String");  //这里一定要用完整的包名</p>
<p>Class c1=String.class;</p>
<p>String str = new String();</p>
<p>Class c2=str.getClass();</p>

- java.lang.reflect.Constructor、构造方法、c.getDeclaredConstructor()
- java.lang.reflect.Field、类成员变量、c.getDeclaredFields();
- java.lang.reflect.Method、类成员方法、c.getDeclaredMethods();
- java.lang.reflect.Modifier、方法类型、Modifier.toString(field.getModifiers())
- java.lang.annotation.Annotation、类注解、c.getAnnotations()

*****

## Java注解(Annotation)
<p>Annotation（注解）就是Java提供了一种源程序中的元素关联任何信息或者任何元数据（metadata）的途径和方法。</p>
<p>Annotation是被动的元数据，永远不会有主动行为</p>

###@Retention
- (RetentionPolicy.SOURCEO)
- (RetentionPolicy.CLASS)
- (RetentionPolicy.RUNTIME)

###@Target

- ElementType.TYPE：能修饰类、接口或枚举类型
- ElementType.FIELD：能修饰成员变量
- ElementType.METHOD：能修饰方法
- ElementType.PARAMETER：能修饰参数
- ElementType.CONSTRUCTOR：能修饰构造器
- ElementType.LOCAL_VARIABLE：能修饰局部变量
- ElementType.ANNOTATION_TYPE：能修饰注解
- ElementType.PACKAGE：能修饰包

##使用##

