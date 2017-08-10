package com.sharekeg.streetpal.Settings;

/**
 * Created by HP on 05/08/17.
 */

public class ChangePassword {

    private String oldPassword;
    private String newPassword;
    private String confirmnewPassword;
    private String token;

    public ChangePassword(String oldUserPassword, String textnewpassword, String textconfirmpassword) {
        this.oldPassword= oldUserPassword;
        this.newPassword = textnewpassword;
        this. confirmnewPassword = textconfirmpassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmnewPassword(String confirmnewPassword) {
        this.confirmnewPassword = confirmnewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmnewPassword() {
        return confirmnewPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
