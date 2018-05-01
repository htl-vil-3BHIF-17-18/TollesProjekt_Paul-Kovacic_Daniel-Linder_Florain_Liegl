package bll;

import java.awt.*;
import java.io.Serializable;

public class Settings implements Serializable {

    private static final long serialVersionUID = 8367156940742286833L;
    private String aliasName;
    private boolean onlyUndone;

    public Settings(String aliasName, boolean onlyUndone) {
        this.aliasName = aliasName;
        this.onlyUndone = onlyUndone;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public boolean isOnlyUndone() {
        return onlyUndone;
    }

    public void setOnlyUndone(boolean onlyUndone) {
        this.onlyUndone = onlyUndone;
    }
}
