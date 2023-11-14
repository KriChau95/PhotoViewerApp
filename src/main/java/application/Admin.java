package application;

import java.io.Serial;
import java.util.ArrayList;

public class Admin extends User{
    @Serial
    private static final long serialVersionUID = 1L;
    Users users;

    public Admin(String username) {
        super(username);
        users = new Users();
    }

    public boolean create(String username) {
        if (!userExists(username)){
            users.add(new User(username));
            return true;
        }
        return false;
    }

    public boolean remove(String username) {
        users.remove(username);
        return true;
    }

    public ArrayList<String> list() {
        return users.getUsernames();
    }

    private boolean userExists(String username) {
        ArrayList<String> usernames = users.getUsernames();
        for (String s : usernames) {
            if (s != null) {
                if (s.equals(username)) return true;
            }
        }
        return false;
    }

}
