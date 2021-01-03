/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Madhura Bhat
 */
public class Thread {

    private int dealId;
    private String title;
    private String content;
    private double price;
    private String store;
    private String author;
    private String dateTime;
    private int rating;
    private String status;
    private int replyCount;
    DataStorage data = new SQL_Database();

    public Thread(int dealId, String title, String content, double price, String store,
            String author, String dateTime, int rating, String status, int replyCount) {
        this.dealId = dealId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.store = store;
        this.author = author;
        this.dateTime = dateTime;
        this.rating = rating;
        this.status = status;
        this.replyCount = replyCount;
    }

    public Thread() {
        //dummy
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

}
