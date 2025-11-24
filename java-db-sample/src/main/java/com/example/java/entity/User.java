package com.example.java.entity;

/**
 * Java版のEntityクラス
 * DBテーブルと対応するドメインモデル
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private int age;
    // Nullable fields (DBでNULLが許可されているカラム)
    private String nickname;
    private String phoneNumber;
    private String address;

    public User() {
    }

    public User(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public User(Long id, String name, String email, int age, String nickname, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
