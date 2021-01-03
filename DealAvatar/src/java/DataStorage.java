
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhura Bhat
 */
public interface DataStorage {

    void initializeDatabase();

    boolean uniqueId(String id);

    String createAccount(String accountId, String password, String name);

    String login(String id, String password);

    ArrayList<Object> getUserById(String id);

    String createNewThread(String title, String content, double price, String store, String author);

    ArrayList<Thread> getAllThreads();

    String approveOrReject(int dealId, String action);

    boolean upOrDown(String accountId, int dealId, String action);

    ArrayList<Object> thumbsUpDown(String accountId, int dealId, String action);

    String reply(int dealId, String accountId, String inputText, int replyFor);

    ArrayList<ThreadReply> getThreadReplies(int dealId);

    String getOriginalReply(int threadReplyId);

    String addAlert(String accountId, String alert);

    ArrayList<Alert> getAlerts(String accountId);

    String deleteAlert(int alertId);

    void updateAlertNotifications(String accountId);

    ArrayList<AlertNotification> getAllAlertNotifications(String accountId);

    String markRead(int dealId, String accountId);

    String saveItem(int dealId, String accountId);

    ArrayList<SavedItem> getSavedItems(String accountId);

    String deleteSavedItem(int saveId);
    
    String updateName(String accountId, String name);
    
    String changePassword(String accountId, String oldPassword, String newPassword);
    
    String deleteAccount(String accountId);
}
