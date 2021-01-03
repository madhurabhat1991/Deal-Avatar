
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
public class Store {

    private final Account account;
    private ArrayList<Thread> storeDealList;
    private String storeName;
    DataStorage data = new SQL_Database();

    public Store(Account account) {
        this.account = account;
    }

    public ArrayList<Thread> getStoreDealList() {
        return storeDealList;
    }

    public void setStoreDealList(ArrayList<Thread> storeDealList) {
        this.storeDealList = storeDealList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    //get all the stores from approved deals
    public ArrayList<String> getStores() {
        //get threads that are approved
        ArrayList<Thread> approvedDeals = account.getApprovedDeals();

        //order approved deals by Store name ascending order
        Collections.sort(approvedDeals, new threadStoreComparator());

        ArrayList<String> stores = new ArrayList<>();
        for (Thread each : approvedDeals) {
            //check for distinct store name, do not add if it is present
            if (stores.stream().noneMatch(s -> s.equalsIgnoreCase(each.getStore()))) {
                stores.add(each.getStore());
            }
        }

        return stores;
    }

    //get approved deals for selected store
    public String getStoreDeals(String storeName) {
        //get threads that are approved
        ArrayList<Thread> approvedDeals = account.getApprovedDeals();
        storeDealList = new ArrayList<>();

        //filter threds for selected store
        for (Thread each : approvedDeals) {
            if (each.getStore().equalsIgnoreCase(storeName)) {
                storeDealList.add(each);
            }
        }
        this.storeName = storeName;

        //order deal list by deal id descending order
        Collections.sort(storeDealList, Collections.reverseOrder(new threadDealIdComparator()));

        return "storeDeals";
    }

}
