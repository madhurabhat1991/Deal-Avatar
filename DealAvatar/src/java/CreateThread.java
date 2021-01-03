/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Madhura Bhat
 */
@Named(value = "createThread")
@RequestScoped
public class CreateThread {

    private String title;
    private String content;
    private double price;
    private String store;
    private DataStorage data = new SQL_Database();

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

    public String createNewThread(String author) {
        //load the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            return ("Internal Error! Please try again later.");
        }

        return data.createNewThread(title, content, price, store, author);

    }
}
