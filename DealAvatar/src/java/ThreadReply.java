/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Madhura Bhat
 */
public class ThreadReply {

    private int threadReplyId;
    private int dealId;
    private String reply;
    private String replyBy;
    private String dateTime;
    private int replyFor;

    public ThreadReply(int threadReplyId, int dealId, String reply, String replyBy, String dateTime, int replyFor) {
        this.threadReplyId = threadReplyId;
        this.dealId = dealId;
        this.reply = reply;
        this.replyBy = replyBy;
        this.dateTime = dateTime;
        this.replyFor = replyFor;
    }

    public int getThreadReplyId() {
        return threadReplyId;
    }

    public void setThreadReplyId(int threadReplyId) {
        this.threadReplyId = threadReplyId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getReplyFor() {
        return replyFor;
    }

    public void setReplyFor(int replyFor) {
        this.replyFor = replyFor;
    }

    public String showOriginalReply() {
        String replyStr = reply;
        if (replyStr.length() > 55) {
            replyStr = replyStr.substring(0, 55);
        }
        return String.format("Quote from %s  -  %s ...", replyBy, replyStr);
    }
}
