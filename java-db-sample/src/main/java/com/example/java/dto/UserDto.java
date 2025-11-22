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
 */
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private int age;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return age == userDto.age &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
