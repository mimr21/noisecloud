import client.Client;

import java.io.*;


public class Test {
    public static void main(String[] args) throws IOException {
        File[] dirs = new File[]{new File("_client_downloads"), new File("_server_storage")};
        for (File dir : dirs) {
            File[] files = dir.listFiles();
            if (files == null)
                continue;
            for (File file : files) {
                if (!file.isDirectory())
                    file.delete();
            }
        }

        System.setIn(new FileInputStream(new File("test\\input.txt")));

        Client.main(null);
    }
}
