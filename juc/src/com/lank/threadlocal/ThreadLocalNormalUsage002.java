package com.lank.threadlocal;

/**
 * @author lank
 * @since 2020/10/21 11:59
 * ThreadLocal用法二：避免传递参数的麻烦
 */
public class ThreadLocalNormalUsage002 {


}

class Service1{

    public void process(){
        Thread thread = new Thread();
        User user = new User("lank");
    }
}


class User{
    String name;

    public User(String name) {
        this.name = name;
    }
}