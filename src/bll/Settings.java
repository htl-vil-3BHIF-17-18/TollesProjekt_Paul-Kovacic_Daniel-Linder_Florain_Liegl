package bll;

import javax.swing.*;
import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = 8367156940742286833L;
    private String aliasName;
    private boolean onlyTodo;

    public Settings(String aliasName, boolean onlyTodo) {
        this.aliasName = aliasName;
        this.onlyTodo = onlyTodo;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public boolean isOnlyTodo() {
        return onlyTodo;
    }

    public void setOnlyTodo(boolean onlyTodo) {
        this.onlyTodo = onlyTodo;
    }
}
