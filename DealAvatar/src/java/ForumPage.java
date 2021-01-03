
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
public class ForumPage {

    private final Account account;

    public ForumPage(Account account) {
        this.account = account;
    }

    //get all the threads for forum page, order by deal id descending order
    public ArrayList<Thread> getThreadsByDeal() {
        ArrayList<Thread> threads = account.getAllThreads();
        Collections.sort(threads, Collections.reverseOrder(new threadDealIdComparator()));
        return threads;
    }

}
