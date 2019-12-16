package server;

public class User {
    private String username;
    private String password;
    private boolean log;

    public User(String n, String p){
        this.username = n;
        this.password = p;
        this.log = false;
    }

    public User(User u){
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.log = u.getLog();
    }

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}
    public boolean getLog() {return this.log;}

    public void setUsername(String n) {this.username = n;}
    public void setPassword(String p) {this.password = p;}
    public void setLog(boolean l){this.log = l;}

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        User u = (User) o;
        return (this.username.equals(u.getUsername()) && this.password.equals(u.getPassword()));
    }

    public User clone(){return new User(this);}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Utilizador {");
        sb.append("Username=").append(this.username);
        return sb.toString();
    }

    public boolean isValid(String pass){
        return this.password.equals(pass);
    }
}
