package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private Map<String, User> users;        //username, server.User
    private Map<Integer, Media> media;
    private int currentID;

    public Model() {
        this.users = new HashMap<>();
        this.media = new HashMap<>();
        this.currentID = 0;
    }

    public int addUser(String name, String pass) throws UsernameAlreadyExistsException{
        if(!this.users.containsKey(name))
            this.users.put(name, new User(name, pass));
        else
            throw new UsernameAlreadyExistsException("Nome de utilizador já existente");
        return 1;
    }

    public boolean login(String name, String pass) throws UserNotFoundException, InvalidPasswordException {
        if(!this.users.containsKey(name))
            throw new UserNotFoundException("Utilizador não existente.");
        else
            if(!this.users.get(name).isValid(pass))
                throw new InvalidPasswordException("Password incorreta.");

        User u = users.get(name);
        if(u.getLog())
            return false;           //se já tiver feito login
        else {
            u.setLog(true);
            return true;
        }
    }

    public String listUsers(){
        if(this.users.size() == 0)
            return "Não existem utilizadores.";
        else
            return this.users.toString();
    }

    public int upload(String file, String name, String artist, int year, String[] tags){
        int id = this.currentID++;
        byte[] fbytes = new byte[file.length()];

        for (int i=0 ; i < fbytes.length; i++) {
            fbytes[i] = Byte.parseByte(file);       /*ainda não tá bem */
        }

        Media m = new Media(id, name, artist, year, tags, 0, fbytes);
        this.media.put(id, m);
        return id;
    }
}
