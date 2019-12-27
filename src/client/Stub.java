package client;

import exceptions.RemoteModelException;
import model.EpicInputStream;
import model.EpicOutputStream;
import model.IModel;
import model.User;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;


// package-private
class Stub implements IModel {
    private Socket socket;
    private EpicInputStream in;
    private EpicOutputStream out;
    private boolean login;


    public Stub(String host, int port) throws IOException {
        this.socket =  new Socket(host, port);
        this.in = new EpicInputStream(new DataInputStream(socket.getInputStream()));
        this.out = new EpicOutputStream(new DataOutputStream(socket.getOutputStream()));
        this.login = false;
    }

    public void end() {
        try {
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addUser(String name, String pass) throws RemoteModelException {
        if (login) {
            try {
                out.println("addUser " + name + " " + pass);
                out.flush();

                String s = in.readLine();

                //return s;
            } catch (IOException e) {
                throw new RemoteModelException(e.getMessage());
            }
        } else {
            throw new RemoteModelException("Login já realizado.");
        }
    }

    public boolean login(String name, String pass) throws RemoteModelException {
        if (login) {
            return true;
        } else {
            try {
                out.println("login " + name + " " + pass);
                out.flush();

                String s = in.readLine();

                if (s.equals("Login com sucesso"))
                    login = true;

                //return s;
                return false;
            } catch (IOException e) {
                throw new RemoteModelException(e.getMessage());
            }
        }
    }

    public boolean logout(String name) throws RemoteModelException {
        String s;

        if (login) {
            try {
                out.println("logout " + name);
                out.flush();

                s = in.readLine();

                if (s.equals("Logout com sucesso"))
                    login = false;
            } catch (IOException e) {
                throw new RemoteModelException(e.getMessage());
            }
        } else {
            s = "Impossível realizar a operação. Faça login primeiro.";
        }

        //return s;
        return false;
    }

    public Collection<User> listUsers() throws RemoteModelException {
        String s;

        if (login) {
            try {
                out.println("users");
                out.flush();

                s = in.readLine();
            } catch (IOException e) {
                throw new RemoteModelException(e.getMessage());
            }
        } else {
            s = "Impossível realizar a operação. Faça login primeiro.";
        }

        //return s;
        return null;
    }

    public int upload(String title, String artist, int year, String tags) throws RemoteModelException {
        String s;

        if (login) {
            try {
                out.println("addUser " + title + " " + artist + " " + year + " " + tags);
                out.flush();

                s = in.readLine();
            } catch (IOException e) {
                throw new RemoteModelException(e.getMessage());
            }
        } else {
            s = "Impossível realizar a operação. Faça login primeiro.";
        }

        //return s;
        return 0;
    }
}
