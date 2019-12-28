package model;


public interface Cerealizable {
    static final String ARGS_SEPARATOR = "&";
    static final String ARRAY_SEPARATOR = "%";

    String cerealize();

    static String cerealize(String[] str_arr) {
        StringBuilder sb = new StringBuilder();

        int size = str_arr.length;

        if (size > 0) {
            sb.append(str_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(ARRAY_SEPARATOR).append(str_arr[i]);
        }

        return sb.toString();
    }
}
