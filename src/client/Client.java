package client;

import exceptions.*;
import model.IModel;
import model.User;
import static model.Noisecloud.*;

import java.io.*;
import java.util.Collection;


public class Client {
    private final IModel model;
    private final View view;
    private final BufferedReader stdin;


    private Client() throws IOException {
        model = new Stub("localhost", 12345);
        view = new View();
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

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

    private void login_menu() throws IOException {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.login_menu();

            String username, password;
            switch (stdin.readLine()) {
                case "1":
                    System.out.print("Nome de utilizador: ");
                    username = stdin.readLine();
                    System.out.print("          Password: ");
                    password = stdin.readLine();
                    try {
                        model.login(username, password);
                        main_menu();
                    } catch (UserNotFoundException | InvalidPasswordException | RemoteModelException e) {
                        view.err(e);
                    }
                    break;
                case "2":
                    System.out.print(" Nome de utilizador: ");
                    username = stdin.readLine();
                    System.out.print("           Password: ");
                    password = stdin.readLine();
                    try {
                        model.addUser(username, password);
                        System.out.println("Conta criada");
                    } catch (UsernameAlreadyExistsException | RemoteModelException e) {
                        view.err(e);
                    }
                    break;
                case "3":
                    cont = false;
                    break;
                default:
                    view.err("Opção inválida");
            }
        }
    }

    public void main_menu() throws IOException {
        boolean cont = true;

        while (cont) {
            System.out.println();
            view.main_menu();

            switch (stdin.readLine()) {
                case "1":
                    /*System.out.print("    Título: ");
                    String title = stdin.readLine();
                    System.out.print("Intérprete: ");
                    String artist = stdin.readLine();
                    System.out.print("       Ano: ");
                    int year;
                    try {
                        year = Integer.parseInt(stdin.readLine());
                    } catch (NumberFormatException e) {
                        view.err(e);
                        continue;
                    }
                    System.out.print("      Tags: ");
                    String[] tags = stdin.readLine().split("/");*/
                    System.out.print("  Ficheiro: ");
                    String filepath = normalizePath(stdin.readLine());

                    try {
                        int id = model.upload("jose", "manel", 1900, new String[]{"fixe"}, filepath);
                        System.out.println("Upload feito com sucesso. ID da música: " + id);
                    } catch (RemoteModelException e) {
                        view.err(e);
                    }
                    break;
                case "2":
                    break;
                case "3":
                    cont = false;
                    break;
                default:
                    view.err("Opção inválida");
            }
        }
    }



    ///////////
    //  OLD  //
    ///////////

    public void dunno() throws IOException {
        String input, output;
        while (!(input = stdin.readLine()).equals("quit")) {
            String[] cmd = input.split(" ");
            try {
                switch (cmd[0]) {
                    case "adicionar":
                        model.addUser(cmd[1], cmd[2]);
                        output = "Utilizador adicionado com sucesso";
                        break;

                    case "login":
                        output = model.login(cmd[1], cmd[2]) ? "Login com sucesso" : "Login já realizado";
                        break;

                    case "logout":
                        output = model.logout(cmd[1]) ? "Logout com sucesso" : "Logout já realizado";
                        break;

                    case "listar":
                        Collection<User> users = model.listUsers();
                        output = users.isEmpty() ? "Não existem utilizadores" : users.toString();
                        break;

                    case "upload":      // upload title artist year tag1/tag2/tag3/... fi le pa th         // tem de ter pelo menos uma tag
                        String filepath = concat(cmd).replace("\\", "/");
                        if ((new File(filepath)).isFile()) {
                            int id = model.upload(cmd[1], cmd[2], Integer.parseInt(cmd[3]), cmd[4].split("/"), filepath);
                            output = "Upload feito com sucesso. ID da música: " + id;
                        } else {
                            output = "Ficheiro inválido";
                        }
                        break;

                    default:
                        output = "Operação desconhecida. Insira novamente";
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                output = "Dados incorretos. Insira novamente";
            } catch (RemoteModelException | InvalidPasswordException | UserNotFoundException | UsernameAlreadyExistsException e) {
                output = e.getMessage();
            }

            System.out.println(output);
        }
    }

    private static String concat(String[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append(array[5]);
        int end = array.length;
        for (int j = 6; j < end; ++j)
            sb.append(" ").append(array[j]);
        return sb.toString();
    }
}
