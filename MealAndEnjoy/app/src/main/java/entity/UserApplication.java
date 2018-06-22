package entity;

import android.app.Application;

/**
 * Created by ll on 2018/6/19.
 */

public class UserApplication extends Application {

    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
