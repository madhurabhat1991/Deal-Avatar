
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhura Bhat
 */
class threadDealIdComparator implements Comparator<Thread> {

    @Override
    public int compare(Thread t1, Thread t2) {
        return t1.getDealId() - t2.getDealId();
    }

}

class threadRatingComparator implements Comparator<Thread> {

    @Override
    public int compare(Thread t1, Thread t2) {
        if (t1.getRating() == t2.getRating()) {
            return t1.getDealId() - t2.getDealId();
        } else {
            return t1.getRating() - t2.getRating();
        }
    }

}

class threadStoreComparator implements Comparator<Thread> {

    @Override
    public int compare(Thread t1, Thread t2) {
        //consider lower case and upper case while sorting
        return t1.getStore().toLowerCase().compareTo(t2.getStore().toLowerCase());
    }

}
