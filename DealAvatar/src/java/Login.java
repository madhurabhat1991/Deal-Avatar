/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author Madhura Bhat
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String accountId;
    private String password;
    private Account account;
    private FrontPage frontPage;
    private ForumPage forumPage;
    private Store store;
    private Editor editorAccount;
    private User userAccount;
    DataStorage data = new SQL_Database();
    DecimalFormat df = new DecimalFormat("$##.00");

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

    public Account getAccount() {
        return account;
    }

    public FrontPage getFrontPage() {
        return frontPage;
    }

    public ForumPage getForumPage() {
        return forumPage;
    }

    public Store getStore() {
        return store;
    }

    public DecimalFormat getDf() {
        return df;
    }

    public Editor getEditorAccount() {
        return editorAccount;
    }

    public void setEditorAccount(Editor editorAccount) {
        this.editorAccount = editorAccount;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    public String login() {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        String fileName = data.login(accountId, password);
        //outcomes - user, editor, loginFailed, internalError

        if (fileName.equals("user") || fileName.equals("editor")) {
            if (fileName.equals("user")) {
                account = new User(accountId);
                userAccount = (User) account;
            } else if (fileName.equals("editor")) {
                account = new Editor(accountId);
                editorAccount = (Editor) account;
            }
            frontPage = new FrontPage(account);
            forumPage = new ForumPage(account);
            store = new Store(account);

            return "frontPage";
        } else {
            return fileName;
        }
        //outcomes - frontPage, loginFailed, internalError
    }
}
