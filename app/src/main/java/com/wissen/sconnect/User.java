package com.wissen.sconnect;

/**
 * Created by omer on 19.06.2016.
 */
public class User {
    private long userId;
    private String userName;
    private String userSurname;
    private String userNick;
    private String userEmail;
    private String userBirthDate;
    private String userPassword;
    private String cihazBilgisi;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCihazBilgisi() {
        return cihazBilgisi;
    }

    public void setCihazBilgisi(String cihazBilgisi) {
        this.cihazBilgisi = cihazBilgisi;
    }
}
