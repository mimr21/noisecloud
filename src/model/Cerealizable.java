package model;


public interface Cerealizable {
    static final String ARGS_SEPARATOR = "&";
    // private static final String ARRAY_SEPARATOR = "%";


    String cerealize();


    static String cerealize(String[] str_arr) {
        StringBuilder sb = new StringBuilder();

        int size = str_arr.length;

        if (size > 0) {
            sb.append(str_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append("|").append(str_arr[i]);
        }

        return sb.toString();
    }

    static String[] descerealize(String str) {
        return str.split("\\|");
    }
}
