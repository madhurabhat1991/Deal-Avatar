/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Madhura Bhat
 */
public class SavedItem {

    private int saveId;
    private Thread thread;
    private String accountId;

    public SavedItem(int saveId, Thread thread, String accountId) {
        this.saveId = saveId;
        this.thread = thread;
        this.accountId = accountId;
    }

    public int getSaveId() {
        return saveId;
    }

    public void setSaveId(int saveId) {
        this.saveId = saveId;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
