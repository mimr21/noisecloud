import model.Noisecloud;

public class Test {
    public static void main(String[] args) {
        //String filepath = "E:\\git\\noisecloud\\src\\client\\Client.java".replace("\\", "/");
        String filepath = "";
        System.out.println(filepath.lastIndexOf("/"));
        System.out.println("|" + Noisecloud.getFilename(filepath) + "|");
    }
}
