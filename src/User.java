public class User {
    private String username;
    private String password;

    public User(String n, String p){
        this.username = n;
        this.password = p;
    }

    public User(User u){
        this.username = u.getUsername();
        this.password = u.getPassword();
    }

    public String getUsername() {return this.username;}
    public String getPassword() {return this.password;}

    public void setUsername(String n) {this.username = n;}
    public void setPassword(String p) {this.password = p;}

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
}
