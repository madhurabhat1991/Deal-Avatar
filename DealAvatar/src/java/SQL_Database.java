
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhura Bhat
 */
public class SQL_Database implements DataStorage {

    private String DATABASE_URL;
    private String db_id;
    private String db_psw;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public void initializeDatabase() {
        DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/java_db?useSSL=false";
        db_id = "root";
        db_psw = "madhu123";

        connection = null;
        statement = null;
        resultSet = null;
    }

    @Override
    public boolean uniqueId(String id) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select account_id from account "
                    + "where account_id = '" + id + "'");

            while (resultSet.next()) {
                return false;
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error while checking if account id is unique");
            e.printStackTrace();
            return false;

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String createAccount(String accountId, String password, String name) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            int r = statement.executeUpdate("insert into account values "
                    + "('" + accountId + "', '" + password + "', '" + name + "', 'user')");

            connection.commit();
            connection.setAutoCommit(true);
            return ("Account creation is successful");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Error during account creation");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String login(String id, String password) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from account "
                    + "where account_id = '" + id + "'");

            if (resultSet.next()) {
                //id is present - compare password
                if (password.equals(resultSet.getString("password"))) {
                    return resultSet.getString("type");
                } else {
                    return "loginFailed";
                }
            } else {
                return "loginFailed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError";

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Object> getUserById(String id) {
        ArrayList<Object> details = new ArrayList<Object>();

        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from account "
                    + "where account_id = '" + id + "'");

            if (resultSet.next()) {
                details.add(resultSet.getString("name"));
                details.add(resultSet.getString("type"));
            } else {
                System.out.println("User id not found");
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user details");
            e.printStackTrace();

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return details;
    }

    @Override
    public String createNewThread(String title, String content, double price, String store, String author) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            int dealId = 0;
            resultSet = statement.executeQuery("select MAX(deal_id) from thread ");

            if (resultSet.next()) {
                //deal id is present
                dealId = resultSet.getInt(1) + 1;
            } else {
                dealId = 1;
            }

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("insert into thread values "
                    + "(" + dealId + ", '" + title.replace("'", "\\'") + "', '" + content.replace("'", "\\'")
                    + "', " + price + ", '" + store.replace("'", "\\'") + "', '" + author + "', '"
                    + DateAndTime.DateTime() + "', " + 0 + ", 'new')");

            connection.commit();
            connection.setAutoCommit(true);
            return ("Thread creation is successful");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Error during thread creation");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Thread> getAllThreads() {
        ArrayList<Thread> threads = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            //get all threads for forum page
            resultSet = statement.executeQuery("select t.*,\n"
                    + "(select count(thread_reply_id) from thread_reply where deal_id = t.deal_id) replies\n"
                    + "from thread t\n");

            while (resultSet.next()) {
                threads.add(new Thread(resultSet.getInt("deal_id"), resultSet.getString("title"),
                        resultSet.getString("content"), resultSet.getDouble("price"), resultSet.getString("store"),
                        resultSet.getString("author"), resultSet.getString("date_time"), resultSet.getInt("rating"),
                        resultSet.getString("status"), resultSet.getInt("replies")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Internal error while getting threads/deals");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return threads;
    }

    @Override
    public String approveOrReject(int dealId, String action) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            if (action.equals("approve")) {
                int r = statement.executeUpdate("update thread\n"
                        + "set status = 'approve'\n"
                        + "where deal_id = " + dealId);

                connection.commit();
                connection.setAutoCommit(true);
                return ("Thread approved");

            } else if (action.equals("reject")) {
                int r = statement.executeUpdate("update thread\n"
                        + "set status = 'reject'\n"
                        + "where deal_id = " + dealId);

                connection.commit();
                connection.setAutoCommit(true);
                return ("Thread rejected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Error during thread approval/rejection");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public boolean upOrDown(String accountId, int dealId, String action) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select *\n"
                    + "from thread_vote\n"
                    + "where account_id = '" + accountId + "'\n"
                    + "and deal_id = " + dealId);

            if (resultSet.next()) {
                //for user/deal - there is either up/down action
                if (action.equals(resultSet.getString("action"))) {
                    //same vote exists for user/deal - display error message
                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Error while checking if same vote exists");
            e.printStackTrace();
            return false;

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Object> thumbsUpDown(String accountId, int dealId, String action) {
        ArrayList<Object> pageRating = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select *\n"
                    + "from thread_vote\n"
                    + "where account_id = '" + accountId + "'\n"
                    + "and deal_id = " + dealId);

            if (resultSet.next()) {
                //for user/deal - there is either up/down action
                if (action.equals(resultSet.getString("action"))) {
                    //same vote exists for user/deal - display error message
                    pageRating.add(0, "You have already voted this " + action + "!");
                    return pageRating;
                } else {
                    //opposite vote exists for user/deal - update old action of same record with new action
                    connection.setAutoCommit(false);

                    int r = statement.executeUpdate("update thread_vote\n"
                            + "set action = '" + action + "'\n"
                            + "where account_id = '" + accountId + "'\n"
                            + "and deal_id = " + dealId);

                    connection.commit();
                    connection.setAutoCommit(true);
                }
            } else {
                //user never voted up/down for this deal - insert record with new action
                connection.setAutoCommit(false);

                int r = statement.executeUpdate("insert into thread_vote values "
                        + "('" + accountId + "', " + dealId + ", '" + action + "')");

                connection.commit();
                connection.setAutoCommit(true);
            }

            //increment or decrement rating in thread - for opposite action or new vote
            connection.setAutoCommit(false);

            if (action.equals("up")) {
                int r = statement.executeUpdate("update thread\n"
                        + "set rating = rating + 1\n"
                        + "where deal_id = " + dealId);
            } else if (action.equals("down")) {
                int r = statement.executeUpdate("update thread\n"
                        + "set rating = rating - 1\n"
                        + "where deal_id = " + dealId);
            }

            connection.commit();
            connection.setAutoCommit(true);

            pageRating.add(0, "threadBody");

            resultSet = statement.executeQuery("select rating\n"
                    + "from thread\n"
                    + "where deal_id = " + dealId);

            if (resultSet.next()) {
                pageRating.add(1, resultSet.getInt("rating"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pageRating;
    }

    @Override
    public String reply(int dealId, String accountId, String inputText, int replyFor) {

        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            int replyId = 0;
            resultSet = statement.executeQuery("select MAX(thread_reply_id) from thread_reply ");

            if (resultSet.next()) {
                //reply id is present
                replyId = resultSet.getInt(1) + 1;
            } else {
                replyId = 1;
            }

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("insert into thread_reply values "
                    + "(" + replyId + ", " + dealId + ", '" + inputText.replace("'", "\\'") + "', '" + accountId
                    + "', '" + DateAndTime.DateTime() + "', " + replyFor + " )");

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ("threadBody");
    }

    @Override
    public ArrayList<ThreadReply> getThreadReplies(int dealId) {
        ArrayList<ThreadReply> replies = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from thread_reply where deal_id = " + dealId);

            while (resultSet.next()) {
                replies.add(new ThreadReply(resultSet.getInt("thread_reply_id"), resultSet.getInt("deal_id"),
                        resultSet.getString("reply"), resultSet.getString("reply_by"),
                        resultSet.getString("date_time"), resultSet.getInt("reply_for")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Internal error while getting thread replies");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return replies;
    }

    @Override
    public String getOriginalReply(int threadReplyId) {
        ThreadReply threadReply;
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from thread_reply where thread_reply_id = " + threadReplyId);

            if (resultSet.next()) {
                threadReply = new ThreadReply(resultSet.getInt("thread_reply_id"), resultSet.getInt("deal_id"),
                        resultSet.getString("reply"), resultSet.getString("reply_by"),
                        resultSet.getString("date_time"), resultSet.getInt("reply_for"));

                return threadReply.showOriginalReply();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal error while getting original thread reply");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public String addAlert(String accountId, String alert) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from deal_alert "
                    + "where account_id = '" + accountId + "'"
                    + "and upper(alert) = upper('" + alert + "')");

            if (resultSet.next()) {
                return "This alert already exists in your account!";
            }

            int alertId = 0;
            resultSet = null;
            resultSet = statement.executeQuery("select MAX(alert_id) from deal_alert ");

            if (resultSet.next()) {
                //alert id is present
                alertId = resultSet.getInt(1) + 1;
            } else {
                alertId = 1;
            }

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("insert into deal_alert values "
                    + "(" + alertId + ", '" + accountId + "' , '" + alert.replace("'", "\\'") + "')");

            connection.commit();
            connection.setAutoCommit(true);
            return ("Alert added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal error while adding alert!");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Alert> getAlerts(String accountId) {
        ArrayList<Alert> alerts = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from deal_alert where account_id = '" + accountId + "'");

            while (resultSet.next()) {
                alerts.add(new Alert(resultSet.getInt("alert_id"), resultSet.getString("account_id"),
                        resultSet.getString("alert")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Internal error while getting alerts");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return alerts;
    }

    @Override
    public String deleteAlert(int alertId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("delete from deal_alert\n"
                    + "where alert_id = " + alertId);

            r = statement.executeUpdate("delete from alert_notification\n"
                    + "where alert_id = " + alertId);

            connection.commit();
            connection.setAutoCommit(true);
            return ("Alert deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Internal error while deleting alert!");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateAlertNotifications(String accountId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();
            Statement statement2 = connection.createStatement();

            int notifId = 0;
            resultSet = statement.executeQuery("select MAX(notif_id) from alert_notification ");

            if (resultSet.next()) {
                //notif id is present
                notifId = resultSet.getInt(1) + 1;
            } else {
                notifId = 1;
            }

            resultSet = null;
            resultSet = statement.executeQuery("select distinct t.deal_id, da.alert_id\n"
                    + "from thread t, deal_alert da\n"
                    + "where da.account_id = '" + accountId + "'\n"
                    + "and ((lower(t.title) like CONCAT('%', lower(da.alert), '%'))\n"
                    + "OR (lower(t.content) like CONCAT('%', lower(da.alert), '%'))\n"
                    + "OR (lower(t.store) like CONCAT('%', lower(da.alert), '%')))\n"
                    + "and t.status = 'approve'"
                    + "and NOT EXISTS\n"
                    + "	(select 1\n"
                    + "	from alert_notification an\n"
                    + "	where an.alert_id = da.alert_id\n"
                    + "	and an.deal_id = t.deal_id)");

            while (resultSet.next()) {
                connection.setAutoCommit(false);

                int r = statement2.executeUpdate("insert into alert_notification values\n"
                        + "(" + notifId + ", " + resultSet.getInt("alert_id") + ", "
                        + resultSet.getInt("deal_id") + ", 'N')");

                notifId = notifId + 1;

                connection.commit();
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<AlertNotification> getAllAlertNotifications(String accountId) {
        ArrayList<AlertNotification> dealIds = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            //get all deal id for account id's deal alerts
            resultSet = statement.executeQuery("select *\n"
                    + "from alert_notification\n"
                    + "where alert_id IN\n"
                    + "	(select alert_id\n"
                    + "	from deal_alert\n"
                    + "	where account_id = '" + accountId + "')");

            while (resultSet.next()) {
                dealIds.add(new AlertNotification(resultSet.getInt("notif_id"), resultSet.getInt("alert_id"),
                        resultSet.getInt("deal_id"), resultSet.getString("mark_read")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Internal error while getting alert notifications");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dealIds;
    }

    @Override
    public String markRead(int dealId, String accountId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            int r = statement.executeUpdate("update alert_notification\n"
                    + "set mark_read = 'Y'\n"
                    + "where deal_id = " + dealId + "\n"
                    + "and alert_id in\n"
                    + "	(select alert_id\n"
                    + "		from deal_alert\n"
                    + "		where account_id = '" + accountId + "')");

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
//                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "This notification is marked as read";
    }

    @Override
    public String saveItem(int dealId, String accountId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from save_item\n"
                    + "where account_id = '" + accountId + "'\n"
                    + "and deal_id = " + dealId);

            if (resultSet.next()) {
                return "voteConfirmation";
            }

            int saveId = 0;
            resultSet = null;
            resultSet = statement.executeQuery("select MAX(save_id) from save_item ");

            if (resultSet.next()) {
                //save id is present
                saveId = resultSet.getInt(1) + 1;
            } else {
                saveId = 1;
            }

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("insert into save_item values "
                    + "(" + saveId + ", " + dealId + ", '" + accountId + "')");

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ("threadBody");
    }

    @Override
    public ArrayList<SavedItem> getSavedItems(String accountId) {
        ArrayList<SavedItem> savedItems = new ArrayList<>();
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select t.*, s.save_id\n"
                    + "from thread t, save_item s\n"
                    + "where s.account_id = '" + accountId + "'\n"
                    + "and s.deal_id = t.deal_id");

            while (resultSet.next()) {
                Thread thread = new Thread(resultSet.getInt("deal_id"), resultSet.getString("title"),
                        resultSet.getString("content"), resultSet.getDouble("price"), resultSet.getString("store"),
                        resultSet.getString("author"), resultSet.getString("date_time"), resultSet.getInt("rating"),
                        resultSet.getString("status"), 0);

                savedItems.add(new SavedItem(resultSet.getInt("save_id"), thread, accountId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Internal error while getting thread");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedItems;
    }

    @Override
    public String deleteSavedItem(int saveId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("delete from save_item\n"
                    + "where save_id = " + saveId);

            connection.commit();
            connection.setAutoCommit(true);
            return ("saveItem");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
//                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String updateName(String accountId, String name) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            int r = statement.executeUpdate("update account\n"
                    + "set name = '" + name + "'\n"
                    + "where account_id = '" + accountId + "'");

            connection.commit();
            connection.setAutoCommit(true);
            return ("editAccountInfo");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String changePassword(String accountId, String oldPassword, String newPassword) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select *\n"
                    + "from account\n"
                    + "where account_id = '" + accountId + "'");

            while (resultSet.next()) {
                if (!(resultSet.getString("password").equals(oldPassword))) {
                    return "The current password entered does not match the account current password";
                } else {

                    connection.setAutoCommit(false);

                    int r = statement.executeUpdate("update account\n"
                            + "set password = '" + newPassword + "'\n"
                            + "where account_id = '" + accountId + "'");

                    connection.commit();
                    connection.setAutoCommit(true);
                    return ("Password changed successfully");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public String deleteAccount(String accountId) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            int r = statement.executeUpdate("delete from account\n"
                    + "where account_id = '" + accountId + "'");

            connection.commit();
            connection.setAutoCommit(true);
            return ("deleted");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
