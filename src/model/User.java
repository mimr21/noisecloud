package model;

import java.util.concurrent.locks.ReentrantLock;


public class User implements Lockable, Cerealizable, Comparable<User> {
    private final String username;
    private String password;
    private boolean log;

    private final ReentrantLock lock;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.log = false;
        this.lock = new ReentrantLock(true);
    }

    public User(User u) {
        this.username = u.username;
        this.password = u.password;
        this.log = u.log;
        this.lock = new ReentrantLock(true);
    }

    private User(String username, String password, boolean log) {
        this.username = username;
        this.password = password;
        this.log = log;
        this.lock = new ReentrantLock(true);
    }

    public static User descerealize(String s) {
        String[] args = s.split(Cerealizable.ARGS_SEPARATOR);
        return new User(args[0], args[1], Boolean.parseBoolean(args[2]));
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
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizador {nome_de_utilizador=")
                .append(username)
                .append("}");
        return sb.toString();
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

    public String cerealize() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(Cerealizable.ARGS_SEPARATOR)
                .append(password).append(Cerealizable.ARGS_SEPARATOR)
                .append(log);
        return sb.toString();
    }

    public int compareTo(User u) {
        return this.username.compareTo(u.username);
    }

    public boolean isValid(String pass) {
        return this.password.equals(pass);
    }
}
