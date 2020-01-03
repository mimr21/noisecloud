import client.Client;

import java.io.*;


public class Test {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream(new File("test\\input_luis_f.txt")));

        Client.main(null);
    }
}
