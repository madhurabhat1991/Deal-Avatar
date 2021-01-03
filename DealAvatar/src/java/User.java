
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhura Bhat
 */
public class User extends Account {

    public User(String accountId) {
        super(accountId);
        updateAlertNotifications();
    }

    //get threads for unread deal alerts
    @Override
    public ArrayList<Thread> showNotifications() {
        //get all alert notifications for account
        ArrayList<AlertNotification> allDealAlerts = getAllAlertNotifications();

        //get all the threads from database
        ArrayList<Thread> threads = getAllThreads();

        //get unread deal alerts from all alert notifications
        ArrayList<Thread> unreadAlerts = new ArrayList<>();
        for (AlertNotification alert : allDealAlerts) {
            if (alert.getMarkRead().equalsIgnoreCase("N")) {
                for (Thread thread : threads) {
                    if (alert.getDealId() == thread.getDealId()) {
                        unreadAlerts.add(thread);
                    }
                }
            }
        }

        //order by deal id descending order
        Collections.sort(unreadAlerts, Collections.reverseOrder(new threadDealIdComparator()));

        return unreadAlerts;
    }

    //update alert notifications upon login of account
    public void updateAlertNotifications() {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        getData().updateAlertNotifications(getAccountId());
    }

    //mark the notification as read
    public String markRead(int dealId) {

        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        //approve or reject
        setNotificationMessage(data.markRead(dealId, getAccountId()));
        return "approvalConfirmation";
    }

    //get threads for read deal alerts
    public ArrayList<Thread> showReadNotifications() {
        //get all alert notifications for account
        ArrayList<AlertNotification> allDealAlerts = getAllAlertNotifications();

        //get all the threads from database
        ArrayList<Thread> threads = getAllThreads();

        //get read deal alerts from all alert notifications
        ArrayList<Thread> readAlerts = new ArrayList<>();
        for (AlertNotification alert : allDealAlerts) {
            if (alert.getMarkRead().equalsIgnoreCase("Y")) {
                for (Thread thread : threads) {
                    if (alert.getDealId() == thread.getDealId()) {
                        readAlerts.add(thread);
                    }
                }
            }
        }

        //order by deal id descending order
        Collections.sort(readAlerts, Collections.reverseOrder(new threadDealIdComparator()));

        return readAlerts;
    }

}
