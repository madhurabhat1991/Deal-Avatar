
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
public class Editor extends Account {

    public Editor(String accountId) {
        super(accountId);
    }

    //get threads for editor approval
    @Override
    public ArrayList<Thread> showNotifications() {
        //get all threads
        ArrayList<Thread> threads = getAllThreads();

        //filter threads that are new and rating >= 2
        ArrayList<Thread> editorDeals = new ArrayList<>();
        for (Thread each : threads) {
            if (each.getStatus().equalsIgnoreCase("new") && each.getRating() >= 2) {
                editorDeals.add(each);
            }
        }

        //order by deal id descending order
        Collections.sort(editorDeals, Collections.reverseOrder(new threadDealIdComparator()));

        return editorDeals;
    }

    public String approveOrReject(int dealId, String action) {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        //approve or reject
        setNotificationMessage(data.approveOrReject(dealId, action));
        return "approvalConfirmation";
    }

}
