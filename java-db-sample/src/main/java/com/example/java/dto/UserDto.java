package com.example.java.dto;

import java.util.Objects;

/**
 * Java版のDTO（Data Transfer Object）
 *
 * 特徴:
 * - private フィールドとgetter/setterを持つPOJO
 * - equals(), hashCode(), toString()を手動で実装
 * - ボイラープレートコードが多い
 * - イミュータブルにするには全フィールドをfinalにし、setterを削除する必要がある
 * - nullableなフィールド（nickname, phoneNumber, address）に対して、
 *   使用時に常にnullチェックが必要（NullPointerExceptionのリスク）
 */
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;
    // Nullable fields (DBでNULLが許可されているカラム)
    private String nickname;      // ニックネーム（任意）
    private String phoneNumber;   // 電話番号（任意）
    private String address;       // 住所（任意）

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public UserDto(Long id, String name, String email, int age, String nickname, String phoneNumber, String address) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return age == userDto.age &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(nickname, userDto.nickname) &&
                Objects.equals(phoneNumber, userDto.phoneNumber) &&
                Objects.equals(address, userDto.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, nickname, phoneNumber, address);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
