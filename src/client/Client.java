package client;

import exceptions.*;
import model.IModel;
import model.User;

import java.io.*;
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

                    case "upload":      // upload title artist year tag1/tag2/tag3/... fi le pa th         // tem de ter pelo menos uma tag
                        String filepath = concat(cmd, 5, cmd.length, " ").replace("\\", "/");
                        if ((new File(filepath)).isFile()) {
                            int id = model.upload(cmd[1], cmd[2], Integer.parseInt(cmd[3]), cmd[4].split("/"), filepath);
                            output = "Upload feito com sucesso. ID da música: " + id + ".";
                        } else {
                            output = "Ficheiro inválido.";
                        }
                        break;

                    default:
                        output = "Operação desconhecida. Insira novamente.";
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

    public static String concat(String[] array, int start, int end, String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(array[start]);
        for (int j = start+1; j < end; ++j)
            sb.append(separator).append(array[j]);
        return sb.toString();
    }
}
