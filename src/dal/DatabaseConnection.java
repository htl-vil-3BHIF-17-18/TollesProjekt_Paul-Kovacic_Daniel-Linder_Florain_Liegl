package dal;

import java.sql.*;
import java.util.*;

import bll.*;

public class DatabaseConnection {
    private String username;
    private String password;

    public DatabaseConnection(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@192.168.128.152:1521:ora11g");
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        Connection con = null;
        try {
            con = this.createConnection();
            Statement stmtSelect = con.createStatement();
            ResultSet rs = stmtSelect.executeQuery("SELECT * FROM task");

            while (rs.next()) {
                tasks.add(new Task(rs.getString(1).equals("Y"), Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), rs.getDate(5), rs.getDate(6)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public List<Task> getUndoneTasks() {
        List<Task> tasks = new ArrayList<>();
        Connection con = null;
        try {
            con = this.createConnection();
            Statement stmtSelect = con.createStatement();
            ResultSet rs = stmtSelect.executeQuery("SELECT * FROM task WHERE done LIKE 'N'");

            while (rs.next()) {
                tasks.add(new Task(rs.getString(1).equals("Y"), Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), this.convertDate(rs.getDate(5)), this.convertDate(rs.getDate(6))));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public void addEntry(Task task) {
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtInsert = con.prepareStatement("INSERT INTO task (done, cat, subject, description, von, bis) VALUES (?,?,?,?,?,?)");
            stmtInsert.setString(1, task.isDone() ? "Y" : "N");
            stmtInsert.setString(2, task.getCategory().toString());
            stmtInsert.setString(3, task.getSubject().toString());
            stmtInsert.setString(4, task.getDescription());
            stmtInsert.setDate(5, this.convertDate(task.getFrom()));
            stmtInsert.setDate(6, this.convertDate(task.getUntil()));


            stmtInsert.execute();
            con.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeEntry(Task task) {
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtDelete = con.prepareStatement("DELETE FROM task WHERE category LIKE ? AND subject LIKE ? AND von = ?");
            stmtDelete.setString(1, task.getCategory().toString());
            stmtDelete.setString(2, task.getSubject().toString());
            stmtDelete.setDate(3, this.convertDate(task.getFrom()));
            stmtDelete.execute();
            con.commit();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEntry(Task oldTask, Task newTask) {
        Connection con = null;
        try {
            con = this.createConnection();
            PreparedStatement stmtUpdate = con.prepareStatement("UPDATE task SET done LIKE ?, category LIKE ?, subject LIKE ?, description LIKE ?, von = ?, until = ? WHERE category LIKE ? AND subject LIKE ? AND von = ?");
            stmtUpdate.setString(1, newTask.isDone() ? "Y" : "N");
            stmtUpdate.setString(2, newTask.getCategory().toString());
            stmtUpdate.setString(3, newTask.getSubject().toString());
            stmtUpdate.setString(4, newTask.getDescription());
            stmtUpdate.setDate(5, this.convertDate(newTask.getFrom()));
            stmtUpdate.setDate(6, this.convertDate(newTask.getUntil()));
            stmtUpdate.setString(7, oldTask.getCategory().toString());
            stmtUpdate.setString(8, oldTask.getSubject().toString());
            stmtUpdate.setDate(9, this.convertDate(oldTask.getFrom()));
            stmtUpdate.execute();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkConnection() {
        Connection con = null;
        boolean connnected = true;
        try {
            con = this.createConnection();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            connnected = false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connnected;
    }

    public java.util.Date getTimestampDB() {
        Connection con = null;
        java.sql.Date timestamp = null;
        try {
            con = this.createConnection();
            Statement stmtTimestamp = con.createStatement();
            ResultSet rs = stmtTimestamp.executeQuery("SELECT SCN_TO_TIMESTAMP(MAX(ora_rowscn)) from task");
            timestamp = rs.getDate(1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return timestamp;
    }

    private java.sql.Date convertDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    private java.util.Date convertDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }
}