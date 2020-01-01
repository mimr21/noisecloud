package client;

import exceptions.*;
import model.IModel;
import model.Song;
import model.User;
import static model.Noisecloud.*;

import java.io.*;
import java.util.Collection;


public class Client {
    private final IModel model;
    private final View view;
    private final BufferedReader stdin;


    public static void main(String[] args) {
        Client client;

        try {
            client = new Client();
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        client.run();
    }


    private Client() throws IOException {
        model = new Stub("localhost", 12345);
        view = new View();
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    private void run() {
        view.logo();
        System.out.println();

        try {
            login_menu();
        } catch (IOException e) {
            view.err(e);
        }

        try {
            model.end();
        } catch (IOException e) {
            view.err(e);
        }
    }

    // Menu de Log In

    private void login_menu() throws IOException {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.login_menu();

            switch (stdin.readLine()) {
                case "1":
                    entrar();
                    break;

                case "2":
                    criar_conta();
                    break;

                case "3":   // sair
                    cont = false;
                    break;

                default:
                    view.err("Opção inválida");
            }
        }
    }

    private void entrar() throws IOException {
        System.out.print("Nome de utilizador: ");
        String username = stdin.readLine();
        System.out.print("          Password: ");
        String password = stdin.readLine();
        try {
            model.login(username, password);
            main_menu();
        } catch (UserNotFoundException | InvalidPasswordException | RemoteModelException e) {
            view.err(e);
        }
    }

    private void criar_conta() throws IOException {
        System.out.print(" Nome de utilizador: ");
        String username = stdin.readLine();
        System.out.print("           Password: ");
        String password = stdin.readLine();
        try {
            model.addUser(username, password);
            System.out.println("Conta criada");
        } catch (UsernameAlreadyExistsException | RemoteModelException e) {
            view.err(e);
        }
    }

    // Menu principal

    private void main_menu() throws IOException {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.main_menu();

            switch (stdin.readLine()) {
                case "1":
                    upload();
                    break;

                case "2":
                    todas_as_musicas();
                    break;

                case "3":
                    procurar_musicas();
                    break;

                case "4":
                    todos_os_utilizadores();
                    break;

                case "5":   // log out
                    cont = false;
                    break;

                default:
                    view.err("Opção inválida");
            }
        }
    }

    private void upload() throws IOException {
        System.out.print("                  Título: ");
        String title = stdin.readLine();
        System.out.print("              Intérprete: ");
        String artist = stdin.readLine();
        System.out.print("                     Ano: ");
        int year;
        try {
            year = Integer.parseInt(stdin.readLine());
        } catch (NumberFormatException e) {
            view.err(e);
            return;
        }
        System.out.print("Tags (separadas por '/'): ");
        String[] tags = stdin.readLine().split("/");
        System.out.print("                Ficheiro: ");
        String filepath = normalizePath(stdin.readLine());

        try {
            int id = model.upload(title, artist, year, tags, filepath);
            System.out.println("Upload feito com sucesso. ID da música: " + id);
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void todas_as_musicas() {
        try {
            Collection<Song> songs = model.listSongs();
            if (songs.isEmpty()) {
                System.out.println("Não há músicas");
            } else {
                for (Song song : songs)
                    System.out.println(song.toString());
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void procurar_musicas() throws IOException {
        System.out.print("Tag: ");
        String tag = stdin.readLine();
        try {
            Collection<Song> songs = model.search(tag);
            if (songs.isEmpty()) {
                System.out.println("Não há músicas com a tag '" + tag + "'");
            } else {
                for (Song song : songs)
                    System.out.println(song.toString());
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void todos_os_utilizadores() {
        try {
            Collection<User> users = model.listUsers();
            for (User user : users)
                System.out.println(user.getUsername());
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }
}
