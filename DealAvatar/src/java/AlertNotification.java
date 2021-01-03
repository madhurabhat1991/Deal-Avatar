/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madhu
 */
public class AlertNotification {

    private int notifId;
    private int alertId;
    private int dealId;
    private String markRead;

    public AlertNotification(int notifId, int alertId, int dealId, String markRead) {
        this.notifId = notifId;
        this.alertId = alertId;
        this.dealId = dealId;
        this.markRead = markRead;
    }

    public int getNotifId() {
        return notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getMarkRead() {
        return markRead;
    }

    public void setMarkRead(String markRead) {
        this.markRead = markRead;
    }

}
