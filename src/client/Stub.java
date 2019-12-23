package client;

import model.IModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;


// package-private
class Stub implements IModel {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean login;


    public Stub(String host, int port) throws IOException {
        this.socket =  new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
        this.login = false;
    }

    public void end(){
        try {
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addUser(String name, String pass)  {
        out.println("addUser" + " " + name + " " + pass);
        out.flush();
        String s = null;
        try {
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public String login(String name, String pass) {
        out.println("login " + name + " " + pass);
        out.flush();
        String s = null;
        try {
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(s.equals("Login com sucesso"))
            this.login = true;
        return s;
    }

    public String logout(String name) {
        String s = null;
        if(this.login) {
            out.println("logout " + name);
            out.flush();
            try {
                s = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(s.equals("Logout com sucesso"))
                this.login = false;
        }
        else
            s = "Impossível realizar a operação. Faça login primeiro.";
        return s;
    }

    public String listUsers() {
        String s = null;
        if(this.login) {
            out.println("users");
            out.flush();
            try {
                s = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            s = "Impossível realizar a operação. Faça login primeiro.";
        return s;
    }

    public String upload(String name, String artist, String year, String tags, byte[] file) {
        String s = null;
        if(this.login) {
            out.println("addUser " + name + " " + artist + " " + year + " " + tags + " " + Arrays.toString(file));
            out.flush();
            try {
                s = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            s = "Impossível realizar a operação. Faça login primeiro.";
        return s;
    }
}
