package lava.bluepay.com.dbtest.bean;

import lava.bluepay.com.dbtest.sqlite.DbField;
import lava.bluepay.com.dbtest.sqlite.DbTable;

/**
 * 数据库操作的java类
 */

@DbTable("tb_user")
public class User {
    @DbField("_id")
    private Integer id;
    @DbField("name")
    private String name;
    @DbField("password")
    private String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
