/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Madhura Bhat
 */
@Named(value = "signUp")
@RequestScoped
public class SignUp {

    private String accountId;
    private String password;
    private String name;
    private final DataStorage data = new SQL_Database();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String signup() {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later!");
        }

        if (validateId(accountId)) {
            if (!data.uniqueId(accountId)) {
                return ("This account id is already taken");
            }
        } else {
            return ("The account id must be 3-20 characters long and must contain at least one letter and one digit ");
        }

        if (!validatePassword(password)) {
            return ("The password must be 3-20 characters long and must contain at least one letter, one digit and one special character among (!@#$%^&*?)");
        } else if (accountId.equals(password)) {
            return ("Account id and password cannot be same");
        }

        return data.createAccount(accountId, password, name);
    }

//    Madhura Bhat
    public boolean validateId(String id) {
        //should contain atleast one digit, one letter, 3-20 characters        
        return id.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{3,20}$");
    }

//    Madhura Bhat
    public boolean validatePassword(String password) {
        //should contain atleast one digit, one letter and one special character among (!#$%^&*?), 3-20 characters        
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*?]).{3,20}$");
    }
}
