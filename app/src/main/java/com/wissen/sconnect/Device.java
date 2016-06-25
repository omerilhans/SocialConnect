package com.wissen.sconnect;

/**
 * Created by omer on 19.06.2016.
 */
public class Device {
    private long id;
    private String registrationId;
    private String cihazBilgisi;
    private String nickName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getCihazBilgisi() {
        return cihazBilgisi;
    }

    public void setCihazBilgisi(String cihazBilgisi) {
        this.cihazBilgisi = cihazBilgisi;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
