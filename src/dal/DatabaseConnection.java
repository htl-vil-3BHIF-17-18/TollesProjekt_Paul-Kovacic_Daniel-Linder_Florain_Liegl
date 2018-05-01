package dal;

import bll.Categories;
import bll.Subjects;
import bll.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private String username;
    private String password;
    private Connection con;

    public DatabaseConnection(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Connection con;

        Class.forName("oracle.jdbc.OracleDriver");
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@192.168.128.152:1521:ora11g");
        } catch (Exception e) {
            con = DriverManager.getConnection("jdbc:oracle:thin:" + this.username + "/" + this.password + "@212.152.179.117:1521:ora11g");
        }
        return con;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
//        Connection con = null;

//            con = this.createConnection();
        try {
            Statement stmtSelect = this.con.createStatement();
            ResultSet rs = null;
            try {
                rs = stmtSelect.executeQuery("SELECT * FROM task");
            } catch (SQLException e) {
                this.createTable();
            }

            while (rs != null && rs.next()) {
                tasks.add(new Task(rs.getString(1).equals("Y"), Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), rs.getDate(5), rs.getDate(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.con != null) {
                    this.con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return tasks;
    }

    public List<Task> getUndoneTasks() {
        List<Task> tasks = new ArrayList<>();
//        Connection con = null;
        try {
//            con = this.createConnection();
            Statement stmtSelect = con.createStatement();
            ResultSet rs = null;
            try {
                rs = stmtSelect.executeQuery("SELECT * FROM task WHERE done LIKE 'N'");
            } catch (SQLException e) {
                this.createTable();
            }
            while (rs != null && rs.next()) {
                tasks.add(new Task(rs.getString(1).equals("Y"), Categories.valueOf(rs.getString(2)), Subjects.valueOf(rs.getString(3)), rs.getString(4), this.convertDate(rs.getDate(5)), this.convertDate(rs.getDate(6))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public void addEntry(Task task) {
//        Connection con = null;
        try {
//            con = this.createConnection();
            PreparedStatement stmtInsert = this.con.prepareStatement("INSERT INTO task (done, cat, subject, description, von, bis) VALUES (?,?,?,?,?,?)");
            stmtInsert.setString(1, task.isDone() ? "Y" : "N");
            stmtInsert.setString(2, task.getCategory().toString());
            stmtInsert.setString(3, task.getSubject().toString());
            stmtInsert.setString(4, task.getDescription());
            stmtInsert.setDate(5, this.convertDate(task.getFrom()));
            stmtInsert.setDate(6, this.convertDate(task.getUntil()));
            stmtInsert.execute();
            this.con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeEntry(Task task) {
//        Connection con = null;
        try {
//            con = this.createConnection();
            PreparedStatement stmtDelete = this.con.prepareStatement("DELETE FROM task WHERE cat LIKE ? AND subject LIKE ? AND von = ?");
            stmtDelete.setString(1, task.getCategory().toString());
            stmtDelete.setString(2, task.getSubject().toString());
            stmtDelete.setDate(3, this.convertDate(task.getFrom()));
            stmtDelete.execute();
            this.con.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEntry(Task oldTask, Task newTask) {
//        Connection con = null;
        try {
//            con = this.createConnection();
            PreparedStatement stmtUpdate = con.prepareStatement("UPDATE task SET done = ?, cat = ?, subject = ?, description = ?, von = ?, bis = ? WHERE cat LIKE ? AND subject LIKE ? AND bis = ?");
            stmtUpdate.setString(1, newTask.isDone() ? "Y" : "N");
            stmtUpdate.setString(2, newTask.getCategory().toString());
            stmtUpdate.setString(3, newTask.getSubject().toString());
            stmtUpdate.setString(4, newTask.getDescription());
            stmtUpdate.setDate(5, this.convertDate(newTask.getFrom()));
            stmtUpdate.setDate(6, this.convertDate(newTask.getUntil()));
            stmtUpdate.setString(7, oldTask.getCategory().toString());
            stmtUpdate.setString(8, oldTask.getSubject().toString());
            stmtUpdate.setDate(9, this.convertDate(oldTask.getUntil()));
            stmtUpdate.execute();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkConnection() {
        boolean connected = true;
        try {
            this.con = this.createConnection();

        } catch (Exception e) {
            e.printStackTrace();
            connected = false;
        }
        return connected;
    }

    private void createTable() {
        try {
            Statement stmtCreate = con.createStatement();
            stmtCreate.execute("CREATE TABLE task (\n" +
                    "    done VARCHAR2(1),\n" +
                    "    cat VARCHAR2(20),\n" +
                    "    subject VARCHAR2(20),\n" +
                    "    description VARCHAR2(50),\n" +
                    "    von DATE,\n" +
                    "    bis DATE,\n" +
                    "    CONSTRAINT pkTask PRIMARY KEY (cat, subject, bis),\n" +
                    "    CONSTRAINT ckDatum CHECK (von <= bis),\n" +
                    "    CONSTRAINT ckErledigt CHECK (upper(done) LIKE 'Y' OR upper(done) LIKE 'N')\n" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Date convertDate(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }

    private java.util.Date convertDate(Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    public String getUsername() {
        return username;
    }
}