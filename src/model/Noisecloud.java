package model;

public class Noisecloud {
    private static final String SERVER_STORAGE_PATH;
    private static final String CLIENT_DOWNLOADS_PATH;


    static {
        String project_path = System.getProperty("user.dir").replace("\\", "/");
        SERVER_STORAGE_PATH = project_path + "/_server_storage/";
        CLIENT_DOWNLOADS_PATH = project_path + "/_client_downloads/";
    }


    private Noisecloud() {}


    public static String storagePath(String filename) {
        return SERVER_STORAGE_PATH + filename;
    }

    public static String downloadsPath(String filename) {
        return CLIENT_DOWNLOADS_PATH + filename;
    }

    public static String getFilename(String filepath) {
        int i = filepath.lastIndexOf("/");
        return filepath.substring(i+1);
    }
}
