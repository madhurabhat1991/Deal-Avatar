
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhura Bhat
 */
public abstract class Account {

    private String accountId;
    private String name;
    private String type;
    private String notificationMessage;
    DataStorage data = new SQL_Database();

    public Account(String accountId) {
        this.accountId = accountId;
        getUserById();
    }

    private void getUserById() {
        ArrayList<Object> details = new ArrayList<>();
        details = data.getUserById(accountId);
        setName((String) details.get(0));
        setType((String) details.get(1));
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    //show notifications for account
    public abstract ArrayList<Thread> showNotifications();

    //get all the threads from database
    public ArrayList<Thread> getAllThreads() {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        //get all the threads from database
        ArrayList<Thread> threads = data.getAllThreads();

        return threads;
    }

    //get all approved deals
    public ArrayList<Thread> getApprovedDeals() {
        //get all the threads from database
        ArrayList<Thread> threads = getAllThreads();

        //filter threads that are approved
        ArrayList<Thread> approvedDeals = new ArrayList<>();
        for (Thread each : threads) {
            if (each.getStatus().equalsIgnoreCase("approve")) {
                approvedDeals.add(each);
            }
        }
        return approvedDeals;
    }

    //get all alert notifications for account
    public ArrayList<AlertNotification> getAllAlertNotifications() {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        //get all alert notifications for account id's deal alerts
        ArrayList<AlertNotification> allDealAlerts = data.getAllAlertNotifications(accountId);

        return allDealAlerts;
    }

    public ArrayList<SavedItem> getSavedItems() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        return data.getSavedItems(accountId);
    }

    public String deleteSavedItem(int saveId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        return data.deleteSavedItem(saveId);
    }

    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";

    }

}
