package bll;

import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = 8367156940742286833L;
    private String aliasName;
    private boolean onlyTodo;
    private boolean showProgressbar;

    public Settings(String aliasName, boolean onlyTodo, boolean showProgressbar) {
        this.aliasName = aliasName;
        this.onlyTodo = onlyTodo;
        this.showProgressbar = showProgressbar;
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

    public boolean isShowProgressbar() {
        return showProgressbar;
    }

    public void setShowProgressbar(boolean showProgressbar) {
        this.showProgressbar = showProgressbar;
    }
}
