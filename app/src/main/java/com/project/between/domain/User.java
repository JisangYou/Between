package com.project.between.domain;

/**
 * Created by JisangYou on 2017-11-06.
 */

public class User {

    public String id;
    public String name;
    public String email;
    public String password;
    public String phone;
    public String friend_phone;
    public String message;
    public String token;
    public String notification;
    public String joinDate;
    public String birth;
    public String gender;
    public String imageUrl;

    public User() {

    }

    public User(String id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public User(String phone, String friend_phone) {
        this.phone = phone;
        this.friend_phone = friend_phone;
    }

    public User(String id, String name, String email, String password, String phone, String friend_phone, String message, String token, String notification, String joinDate, String birth, String gender, String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.friend_phone = friend_phone;
        this.message = message;
        this.token = token;
        this.notification = notification;
        this.joinDate = joinDate;
        this.birth = birth;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFriend_phone() {
        return friend_phone;
    }

    public void setFriend_phone(String friend_phone) {
        this.friend_phone = friend_phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", friend_phone='" + friend_phone + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", notification='" + notification + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", birth='" + birth + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}


