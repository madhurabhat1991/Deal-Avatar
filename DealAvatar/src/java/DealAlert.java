/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Madhura Bhat
 */
@Named(value = "dealAlert")
@SessionScoped
public class DealAlert implements Serializable {

    private String alert;
    private String alertConfirmation;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of DealAlert
     */
    public DealAlert() {
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlertConfirmation() {
        return alertConfirmation;
    }

    public void setAlertConfirmation(String alertConfirmation) {
        this.alertConfirmation = alertConfirmation;
    }

    public String addAlert(String accountId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        alertConfirmation = data.addAlert(accountId, alert);
        setAlert("");
        return "alertConfirmation";
    }

    public ArrayList<Alert> getAlerts(String accountId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        return data.getAlerts(accountId);
    }

    public String deleteAlert(int alertId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        alertConfirmation = data.deleteAlert(alertId);
        return "alertConfirmation";
    }

}
