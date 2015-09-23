package cn.zmlio.cas.support;

import org.jasig.cas.authentication.Credential;

import java.io.Serializable;

/**
 * Created by ZML on 2015/9/22.
 */
public class SSOCredential implements Credential, Serializable {


    private String userId;//用户ID，不是用int型而使用uuid是为了防止被人遍历

    private String email;

    private String phone;

    private boolean actived;

    private boolean enable;

    private boolean expired;

    private String password;

    private String salt;

    @Override
    public String getId() {
        return this.userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
