
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
public class FrontPage {

    private final Account account;

    public FrontPage(Account account) {
        this.account = account;
    }

    //get best deals for front page, order by rating, deal id descending order
    public ArrayList<Thread> getBestDeals(int column) {
        //get threads that are approved
        ArrayList<Thread> approvedDeals = account.getApprovedDeals();

        //order approved deals by rating and deal id descending order
        Collections.sort(approvedDeals, Collections.reverseOrder(new threadRatingComparator()));

        //Distribute deals into 3 columns
        ArrayList<Thread> bestDeals = new ArrayList<>();
        for (int i = column; i <= approvedDeals.size(); i = i + 3) {
            bestDeals.add(approvedDeals.get(i - 1));
        }
        return bestDeals;
    }
}
