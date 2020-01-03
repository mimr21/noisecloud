package common;

import java.util.concurrent.locks.ReentrantLock;


public class User implements Lockable, Cerealizable, Comparable<User> {
    private final String username;
    private String password;
    private boolean log;

    private final ReentrantLock lock = new ReentrantLock(true);


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.log = false;
    }

    public User(User u) {
        this.username = u.username;
        this.password = u.password;
        this.log = u.log;
    }

    private User(String username, String password, boolean log) {
        this.username = username;
        this.password = password;
        this.log = log;
    }

    public static User descerealize(String[] str_arr) {
        return new User(str_arr[0], str_arr[1], Boolean.parseBoolean(str_arr[2]));
    }

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public boolean getLog() {return this.log;}

    public void setPassword(String p) {this.password = p;}
    public void setLog(boolean l) {this.log = l;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        User u = (User) o;
        return this.username.equals(u.username);
    }

    public User clone() {return new User(this);}

    public String toString() {
        return new StringBuilder()
                .append("Utilizador {nome_de_utilizador='").append(username)
                .append("', password='").append(password)
                .append("', logged_in=").append(log)
                .append("}")
                .toString();
    }

    public int hashCode() {
        return username.hashCode();
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public String[] cerealize() {
        return new String[]{username, password, String.valueOf(log)};
    }

    public int compareTo(User u) {
        return this.username.compareToIgnoreCase(u.username);
    }

    public boolean isValid(String pass) {
        return this.password.equals(pass);
    }
}
