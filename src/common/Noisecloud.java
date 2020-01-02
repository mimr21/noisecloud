package common;


public class Noisecloud {
    public static final int MAXSIZE = 8192;       // == 8*1024 == 8KB
    
    private static final String SEPARATOR;
    private static final String ALT_SEPARATOR;
    private static final String SERVER_STORAGE_PATH;
    private static final String CLIENT_DOWNLOADS_PATH;


    static {
        SEPARATOR = System.getProperty("file.separator");
        ALT_SEPARATOR = SEPARATOR.equals("\\") ? "/" : "\\";

        String dir = System.getProperty("user.dir") + SEPARATOR;
        SERVER_STORAGE_PATH = dir + "_server_storage" + SEPARATOR;
        CLIENT_DOWNLOADS_PATH = dir + "_client_downloads" + SEPARATOR;
    }


    private Noisecloud() {}


    public static String storagePath(String filename) {
        return SERVER_STORAGE_PATH + filename;
    }

    public static String downloadsPath(String filename) {
        return CLIENT_DOWNLOADS_PATH + filename;
    }

    public static String getFilename(String filepath) {
        int i1 = filepath.lastIndexOf(SEPARATOR);
        int i2 = filepath.lastIndexOf(ALT_SEPARATOR);
        int i = Math.max(i1, i2);

        return filepath.substring(i + 1);
    }

    public static String normalizePath(String filepath) {
        return filepath.replace(ALT_SEPARATOR, SEPARATOR);
    }
}
