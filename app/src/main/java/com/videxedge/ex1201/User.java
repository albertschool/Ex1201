package com.videxedge.ex1201;

public class User {
    private int key_id;
    private String name;
    private String password;
    private int age;

    public User(int key_id, String name, String password, int age) {
        this.key_id = key_id;
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public User(){}

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
