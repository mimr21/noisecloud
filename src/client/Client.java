package client;

import exceptions.*;
import common.IModel;
import common.Song;
import common.User;
import static common.Noisecloud.normalizePath;
import static client.Navigator.Orientation;

import java.io.*;
import java.util.*;


public class Client {
    private final IModel model;
    private final Notifications notif;
    private final View view;
    private final BufferedReader stdin;

    private static final String INVALID_OPTION = "Opção inválida";


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
        notif = new Notifications("localhost", 12345);
        view = new View();
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    private void run() {
        view.logo();
        System.out.println();

        login_menu();

        try {
            model.end();
            notif.end();
        } catch (IOException e) {
            view.err(e);
        }
    }

    // Menu de Log In

    private void login_menu() {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.login_menu();

            try {
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
                        view.err(INVALID_OPTION);
                }
            } catch (IOException e) {
                view.err(e);
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

    private void main_menu() {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.main_menu();

            try {
                switch (stdin.readLine()) {
                    case "1":
                        songs();
                        break;
                    case "2":
                        top_downloads();
                        break;
                    case "3":
                        search_songs_by_title();
                        break;
                    case "4":
                        search_songs_by_artist();
                        break;
                    case "5":
                        search_songs_by_tag();
                        break;
                    case "6":
                        download();
                        break;
                    case "7":
                        upload();
                        break;
                    case "8":
                        users();
                        break;
                    case "9":   // log out
                        cont = false;
                        break;
                    default:
                        view.err(INVALID_OPTION);
                }
            } catch (IOException e) {
                view.err(e);
            }
        }
    }

    private void songs() throws IOException {
        try {
            Collection<Song> songs = model.listSongs();
            if (songs.isEmpty()) {
                System.out.println("Não há músicas");
            } else {
                List<String> strs = new ArrayList<>();
                for (Song song : songs)
                    strs.add(song.getID() + ") " + song.prettyPrint());

                navigator(strs, 6, 3);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void top_downloads() throws IOException {
        System.out.print("Nº músicas: ");
        try {
            int top = Integer.parseInt(stdin.readLine());
            Collection<Song> songs = model.mostDownloaded(top);
            if (songs.isEmpty()) {
                System.out.println("Não há músicas");
            } else {
                List<String> strs = new ArrayList<>();
                for (Song song : songs)
                    strs.add(song.getID() + ") " + song.prettyPrint() + " [" + song.getDownloads() + "]");

                navigator(strs, 6, 4);
            }
        } catch (NumberFormatException | RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_title() throws IOException {
        System.out.print("Título: ");
        String title = stdin.readLine();
        try {
            List<Song> songs = (List<Song>) model.searchTitle(title);
            if (songs.isEmpty()) {
                System.out.println("Não há músicas com o título '" + title + "'");
            } else {
                List<String> strs = new ArrayList<>();
                for (Song song : songs)
                    strs.add(song.getID() + ") " + song.prettyPrint());

                navigator(strs, 6, 3);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_artist() throws IOException {
        System.out.print("Intérprete: ");
        String artist = stdin.readLine();
        try {
            Collection<Song> songs = model.searchArtist(artist);
            if (songs.isEmpty()) {
                System.out.println("Não há músicas do intérprete '" + artist + "'");
            } else {
                List<String> strs = new ArrayList<>();
                for (Song song : songs)
                    strs.add(song.getID() + ") " + song.prettyPrint());

                navigator(strs, 6, 3);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void search_songs_by_tag() throws IOException {
        System.out.print("Tag: ");
        String tag = stdin.readLine();
        try {
            Collection<Song> songs = model.searchTag(tag);
            if (songs.isEmpty()) {
                System.out.println("Não há músicas com a tag '" + tag + "'");
            } else {
                List<String> strs = new ArrayList<>();
                for (Song song : songs)
                    strs.add(song.getID() + ") " + song.prettyPrint() + " " + Arrays.toString(song.getTags()));

                navigator(strs, 6, 2);
            }
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void download() throws IOException {
        System.out.print("IDs: ");

        String[] ids_str = stdin.readLine().split(" ");
        int size = ids_str.length;
        int[] ids = new int[size];

        try {
            for (int i = 0; i < size; ++i)
                ids[i] = Integer.parseInt(ids_str[i]);
        } catch (NumberFormatException e) {
            view.err(e);
            return;
        }

        for (int id : ids) {
            try {
                model.download(id);
                System.out.println(id + ") Download efetuado com sucesso");
            } catch (SongNotFoundException | RemoteModelException e) {
                view.err(e);
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

    private void users() throws IOException {
        try {
            Collection<User> users = model.listUsers();
            List<String> strs = new ArrayList<>();
            for (User user : users)
                strs.add(user.getUsername());

            navigator(strs, 7, 9);
        } catch (RemoteModelException e) {
            view.err(e);
        }
    }

    private void navigator(List<String> strings, int num_lines, int num_columns) throws IOException {
        Navigator nav = new Navigator(strings, Orientation.VERTICAL, num_lines, num_columns);
        int width = nav.print_page();
        view.printNavigatorBar(width);

        boolean cont = true;
        while (cont) {
            String[] args = stdin.readLine().split(" ");
            try {
                switch (args[0]) {
                    case "s":
                        cont = false;
                        break;
                    case "a":
                        width = nav.previous_page();
                        view.printNavigatorBar(width);
                        break;
                    case "p":
                        width = nav.next_page();
                        view.printNavigatorBar(width);
                        break;
                    case "r":
                        width = nav.resize(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                        view.printNavigatorBar(width);
                        break;
                    case "o":
                        width = nav.switchOrientation();
                        view.printNavigatorBar(width);
                        break;
                    default:
                        view.err(INVALID_OPTION);
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                view.err(e);
            }
        }
    }
}
