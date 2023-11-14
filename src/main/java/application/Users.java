package application;

import java.io.*;
import java.util.ArrayList;

public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<User> list_users;
    private final static String usersFile = "users.ser";
    public Users() {
        if (list_users == null) {
            list_users = new ArrayList<User>();
        }
    }

    public User getUser(String username) {
        for (User user : list_users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public ArrayList<String> getUsernames() {
        ArrayList<User> users = getUsers();
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    public void add(User user) {
        add(user.getUsername());
    }

    public void addAlbum(User user, String album) {
        for (User listUser : list_users) {
            if (listUser.getUsername().equals(user.getUsername())) {
                listUser.addAlbum(album);
                return;
            }
        }
    }

    public void add(String username) {

        // user exists already
        if (findUser(username) != null) {
            return;
        }

        list_users.add(new User(username));
    }

    public void remove(String username) {
        for (int i = 0; i < list_users.size(); i++) {
            if (list_users.get(i).getUsername().equals(username)) {
                list_users.remove(i);
                return;
            }
        }
    }

    public static void store(Users users){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile, false));
            oos.writeObject(users);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Users load(){
        Users users = null;
        try {
            FileInputStream filestream = new FileInputStream(usersFile);
            ObjectInputStream ois = new ObjectInputStream(filestream);
            Object object = ois.readObject();
            users = (Users) object;
            ois.close();
        } catch (Exception e) {
            System.out.println("Trying to load empty users file...");
            users = new Users();
            store(users);
        }
        return users;
    }

    public User findUser(String username) {
        if (list_users.isEmpty())
            return null;

        for (int i = 0; i < list_users.size(); i++) {
            System.out.println(list_users.get(i).getUsername());
            if (list_users.get(i).getUsername().equals(username)) {
                return list_users.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < list_users.size(); i++) {
            str += list_users.get(i).getUsername() + "\n";
        }

        return str;

    }

    public int size() {
        return list_users.size();
    }
    public ArrayList<User> getUsers() {
        return list_users;
    }

}
