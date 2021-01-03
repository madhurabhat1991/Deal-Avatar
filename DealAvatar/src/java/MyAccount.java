/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author madhu
 */
@Named(value = "myAccount")
@SessionScoped
public class MyAccount implements Serializable {

    private String name;
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
    private String message;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of myAccount
     */
    public MyAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String updateName(Account account) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("internalError");
        }

        String fileName = data.updateName(account.getAccountId(), name);

        if (fileName.equals("editAccountInfo")) {
            account.setName(name);
            setName("");
        }
        return fileName;

    }

    public String changePassword(Account account) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("internalError");
        }

        if (!(newPassword.equals(newPassword2))) {
            //New password does not match
            message = "The new password entered does not match";
        } else {

            if (!validatePassword(newPassword)) {
                message = "The password must be 3-20 characters long and must contain at least one letter, one digit and one special character among (!@#$%^&*?)";
            } else if (account.getAccountId().equals(newPassword)) {
                message = "Account id and password cannot be same";
            } else {
                message = data.changePassword(account.getAccountId(), oldPassword, newPassword);
            }
        }
        return "myAccountConfirmation";

    }

    public String deleteAccount(Account account) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("internalError");
        }

        String fileName = data.deleteAccount(account.getAccountId());

        if (fileName.equals("deleted")) {
            return signOut();
        }
        return fileName;

    }

    public boolean validatePassword(String password) {
        //should contain atleast one digit, one letter and one special character among (!#$%^&*?), 3-20 characters        
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*?]).{3,20}$");
    }

    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }

}
