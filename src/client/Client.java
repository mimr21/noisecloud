package client;

import exceptions.InvalidPasswordException;
import exceptions.RemoteModelException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import model.IModel;
import model.User;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;


public class Client {
    public static void main(String[] args) throws IOException {
        IModel model = new Stub("127.0.0.1", 12345);

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String input, output;

        while (!(input = stdin.readLine()).equals("quit")) {
            String[] cmd = input.split(" ");
            try {
                switch (cmd[0]) {
                    case "adicionar":
                        model.addUser(cmd[1], cmd[2]);
                        output = "Utilizador adicionado com sucesso.";
                        break;

                    case "login":
                        output = model.login(cmd[1], cmd[2]) ? "Login com sucesso." : "Login já realizado.";
                        break;

                    case "logout":
                        output = model.logout(cmd[1]) ? "Logout com sucesso." : "Logout já realizado.";
                        break;

                    case "listar":
                        Collection<User> users = model.listUsers();
                        output = users.isEmpty() ? "Não existem utilizadores." : users.toString();
                        break;

                    case "upload":                                                      //upload name artist year tag1/tag2/tag3 ... file
                        //String currentDirectory = System.getProperty("user.dir");       //tem de ter pelo menos uma tag
                        //String path = currentDirectory + "\\" + cmd[1];
                        //byte[] file = Files.readAllBytes(new File(path).toPath());
                        int id = model.upload(cmd[1], cmd[2], Integer.parseInt(cmd[3]), cmd[4]);
                        output = "Upload feito com sucesso. ID da música: " + id + ".";
                        break;

                    default:
                        output = "Operação não reconhecida. Insira novamente.";
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                output = "Dados incorretos. Insira novamente.";
            } catch (RemoteModelException | InvalidPasswordException | UserNotFoundException | UsernameAlreadyExistsException e) {
                output = e.getMessage();
            }

            System.out.println(output);
        }

        model.end();
    }
}
