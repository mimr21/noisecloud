package server;

import exceptions.InvalidPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.IModel;
import model.Media;
import model.User;

import java.util.HashMap;
import java.util.Map;


// package-private
class Model implements IModel {
    private Map<String, User> users;        //username, server.User
    private Map<Integer, Media> media;
    private int currentID;

    public Model() {
        this.users = new HashMap<>();
        this.media = new HashMap<>();
        this.currentID = 0;
    }

    public void end() {}

    public String addUser(String name, String pass) throws UsernameAlreadyExistsException{
        if(!this.users.containsKey(name))
            this.users.put(name, new User(name, pass));
        else
            throw new UsernameAlreadyExistsException("Nome de utilizador já existente");
        return "Utilizador adicionado com sucesso";
    }

    public String login(String name, String pass) throws UserNotFoundException, InvalidPasswordException {
        if(!this.users.containsKey(name))
            throw new UserNotFoundException("Utilizador não existente.");
        else
            if(!this.users.get(name).isValid(pass))
                throw new InvalidPasswordException("Password incorreta.");

        User u = users.get(name);
        if(u.getLog())
            return "Login já realizado";           //se já tiver feito login
        else {
            u.setLog(true);
            return "Login com sucesso";
        }
    }

    public String logout(String name) throws UserNotFoundException{
        if(!this.users.containsKey(name))
            throw new UserNotFoundException("Utilizador não existente.");

        User u = users.get(name);
        if(!u.getLog())
            return "Logout já realizado";            //se já tiver feito logout
        else{
            u.setLog(false);
            return "Logout com sucesso";
        }
    }


    public String listUsers(){
        if(this.users.size() == 0)
            return "Não existem utilizadores.";
        else
            return this.users.toString();
    }

    public String upload(String name, String artist, String year, String tags, byte[] file){
        int id = this.currentID++;
        String[] t = tags.split("/");
        Media m = new Media(id, name, artist, Integer.parseInt(year), t, 0, file);
        this.media.put(id, m);
        return ("Upload feito com sucesso. ID da música: " + id);
    }
}
