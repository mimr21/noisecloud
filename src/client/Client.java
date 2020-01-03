package client;

import exceptions.*;
import common.IModel;
import common.Song;
import common.User;
import static common.Noisecloud.normalizePath;

import java.io.*;
import java.util.*;


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
                    login();
                    break;

                case "2":
                    sign_up();
                    break;

                case "3":   // sair
                    cont = false;
                    break;

                default:
                    view.err("Opção inválida");
            }
        }
    }

    private void login() throws IOException {
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

    private void sign_up() throws IOException {
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
                    songs();
                    break;

                case "2":
                    search_songs_by_title();
                    break;

                case "3":
                    search_songs_by_artist();
                    break;

                case "4":
                    search_songs_by_tag();
                    break;

                case "5":
                    upload();
                    break;

                case "6":
                    users();
                    break;

                case "7":   // log out
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
        } catch (FileNotFoundException e) {
            view.err("'" + e.getMessage() + "' not found");
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void songs() throws IOException {
        try {
            List<Song> songs = (List<Song>) model.listSongs();
            if (songs.isEmpty())
                System.out.println("Não há músicas");
            else {
                List<String> strs = new ArrayList<>();
                int i = 0;

                for (Song song : songs)
                    strs.add(++i + ") " + song.prettyPrint());

                view.printV(strs, 3);

                download(songs);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_title() throws IOException {
        System.out.print("Título: ");
        String title = stdin.readLine();
        try {
            List<Song> songs = (List<Song>) model.searchTitle(title);
            if (songs.isEmpty())
                System.out.println("Não há músicas com o título '" + title + "'");
            else {
                List<String> strs = new ArrayList<>();
                int i = 0;

                for (Song song : songs)
                    strs.add(++i + ") " + song.prettyPrint());

                view.printV(strs, 3);

                download(songs);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_artist() throws IOException {
        System.out.print("Intérprete: ");
        String artist = stdin.readLine();
        try {
            List<Song> songs = (List<Song>) model.searchArtist(artist);
            if (songs.isEmpty())
                System.out.println("Não há músicas do intérprete '" + artist + "'");
            else {
                List<String> strs = new ArrayList<>();
                int i = 0;

                for (Song song : songs)
                    strs.add(++i + ") " + song.prettyPrint());

                view.printV(strs, 3);

                download(songs);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_tag() throws IOException {
        System.out.print("Tag: ");
        String tag = stdin.readLine();
        try {
            List<Song> songs = (List<Song>) model.searchTag(tag);
            if (songs.isEmpty())
                System.out.println("Não há músicas com a tag '" + tag + "'");
            else {
                List<String> strs = new ArrayList<>();
                int i = 0;

                for (Song song : songs)
                    strs.add(++i + ") " + song.prettyPrint() + " " + Arrays.toString(song.getTags()));

                view.printV(strs, 2);

                download(songs);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void users() {
        try {
            List<User> users = (List<User>) model.listUsers();
            for (User user : users)
                System.out.println(user.getUsername());
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void download(List<Song> songs) throws IOException {
        System.out.print("download: ");

        int index;
        try {
            index = Integer.parseInt(stdin.readLine());
        } catch (NumberFormatException e) {
            return;
        }
        --index;

        if (index < 0 || index >= songs.size()) {
            view.err("Índice inválido");
            return;
        }

        try {
            model.download(songs.get(index).getID());
            System.out.println("Download efetuado com sucesso");
        } catch (SongNotFoundException | RemoteModelException e) {
            view.err(e);
        }
    }
}
