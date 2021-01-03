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
@Named(value = "threadBody")
@SessionScoped
public class ThreadBody implements Serializable {

    private Thread thread;
    private String inputText;
    private String voteMessage;
    DataStorage data = new SQL_Database();

    /**
     * Creates a new instance of ThreadBody
     */
    public ThreadBody() {

    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        if (!inputText.isEmpty()) {
            this.inputText = inputText;
        }
    }

    public String getVoteMessage() {
        return voteMessage;
    }

    public void setVoteMessage(String voteMessage) {
        this.voteMessage = voteMessage;
    }

    public String updateThreadBody(Thread thread) {
        this.thread = thread;

        return "threadBody";
    }

    public boolean upOrDown(String accountId, String action) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }

        return data.upOrDown(accountId, thread.getDealId(), action);

    }

    public String thumbsUpDown(String accountId, String action) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        ArrayList<Object> pageRating = data.thumbsUpDown(accountId, thread.getDealId(), action);

        if (!(((String) pageRating.get(0)).equals("threadBody"))) {
            //not threadBody, means already voted same action, so display error message
            voteMessage = (String) pageRating.get(0);
            return "voteConfirmation";
        }

        //threadBody, means user voted opposite action or new vote
        //set the rating so that new vote is reflected on thread body
        thread.setRating((int) pageRating.get(1));

        return "threadBody";
    }

    public String saveComment(String accountId, int replyFor) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        String fileName = data.reply(thread.getDealId(), accountId, inputText, replyFor);
        inputText = "";
        //setInputText("");
        return fileName;
    }

    public ArrayList<ThreadReply> getThreadReplies() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            System.out.println("internalError");
        }
        return data.getThreadReplies(thread.getDealId());
    }

    public String getOriginalReply(int threadReplyId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        return data.getOriginalReply(threadReplyId);
    }

    public String saveItem(String accountId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        String fileName = data.saveItem(thread.getDealId(), accountId);

        if (fileName.equals("voteConfirmation")) {
            voteMessage = "This item is already saved in your account!";
        }

        return fileName;

    }

}
