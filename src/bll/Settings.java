package bll;

import java.io.Serializable;

public class Settings implements Serializable {
    private static final long serialVersionUID = 4017998631671601012L;
    private boolean onlyTodo;

    public Settings(boolean onlyTodo) {
        this.onlyTodo = onlyTodo;
    }


    public boolean isOnlyTodo() {
        return onlyTodo;
    }

    public void setOnlyTodo(boolean onlyTodo) {
        this.onlyTodo = onlyTodo;
    }
}
