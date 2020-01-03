package client;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


public class Navigator {
    private List<String> strings;
    private final int size;
    private int start_index;
    private boolean horizontal;
    private int num_columns;
    private int num_lines;

    private static final String TAB = "    ";
    private static final int TAB_LENGTH;

    static {
        TAB_LENGTH = TAB.length();
    }

    public static enum Orientation {
        HORIZONTAL,
        VERTICAL
    }


    public Navigator(List<String> strings, Orientation orientation, int num_lines, int num_columns) {
        this.strings = new ArrayList<>(strings);
        this.size = this.strings.size();
        this.start_index = 0;
        this.horizontal = orientation == Orientation.HORIZONTAL;
        this.num_columns = num_columns > 0 ? num_columns : 1;
        this.num_lines = num_lines > 0 ? num_lines : 1;
    }

    public int print_page() {
        int end_index = start_index + num_columns*num_lines;
        List<String> toPrint = strings.subList(start_index, Math.min(end_index, size));

        if (horizontal)
            return printH(toPrint);
        else
            return printV(toPrint);
    }

    public int previous_page() {
        int new_index = start_index - num_columns*num_lines;
        start_index = Math.max(new_index, 0);
        return print_page();
    }

    public int next_page() {
        int new_index = start_index + num_columns*num_lines;

        if (new_index < size)
            start_index = new_index;

        return print_page();
    }

    public int resize(int num_lines, int num_columns) {
        this.num_columns = num_columns;
        this.num_lines = num_lines;
        return print_page();
    }

    public int switchOrientation() {
        horizontal = !horizontal;
        return print_page();
    }

    private int printV(List<String> strings) {
        if (num_columns <= 1) {
            int max_string_length = 0;

            for (String str : strings) {
                out.println(str);
                int length = str.length();
                if (length > max_string_length)
                    max_string_length = length;
            }

            return max_string_length;
        } else {
            final int size = strings.size();
            final int max_string_length = maxLength(strings);
            final int num_strings_per_column = size / num_columns;
            final int remain = size % num_columns;
            int last_column_max_length = 0;

            int i = 0;
            int jmax = num_columns - 1;
            for (; i < num_strings_per_column; ++i) {
                // primeiro elemento
                out.print(enlarge(strings.get(i), max_string_length));

                // elementos no meio
                int k;
                for (int j = 1; j < jmax; ++j) {
                    k = i + j * num_strings_per_column + Math.min(j, remain);
                    out.print(TAB + enlarge(strings.get(k), max_string_length));
                }

                // último elemento
                k = i + jmax * num_strings_per_column + Math.min(jmax, remain);
                final String str = strings.get(k);
                out.println(TAB + str);
                final int length = str.length();
                if (length > last_column_max_length)
                    last_column_max_length = length;
            }

            // última linha
            if (remain == 1) {
                out.println(strings.get(i));
            } else if (remain > 1) {
                // primeiro elemento
                out.print(enlarge(strings.get(i), max_string_length));

                // elementos no meio
                final int iInc = num_strings_per_column + 1;
                jmax = remain - 1;
                for (int j = 1; j < jmax; ++j) {
                    i += iInc;
                    out.print(TAB + enlarge(strings.get(i), max_string_length));
                }

                // último elemento
                out.println(TAB + strings.get(i + iInc));
            }

            return navigator_width(max_string_length, last_column_max_length, size);
        }
    }

    private int printH(List<String> strings) {
        final int size = strings.size();
        final int max_string_length = maxLength(strings);
        int last_column_max_length = 0;

        int i = 0;
        for (; i < size; ++i) {
            int x = i % num_columns;

            if (x > 0)
                out.print(TAB);

            if (x == num_columns-1) {
                final String str = strings.get(i);
                out.println(str);
                final int length = str.length();
                if (length > last_column_max_length)
                    last_column_max_length = length;
            } else {
                out.print(enlarge(strings.get(i), max_string_length));
            }
        }

        if (i % num_columns != 0)
            out.println();

        return navigator_width(max_string_length, last_column_max_length, size);
    }

    private int navigator_width(int max_string_length, int last_column_max_length, int size) {
        int x = Math.min(num_columns, size) - 1;

        return max_string_length*x + TAB_LENGTH*x + last_column_max_length;
    }

    private static int maxLength(List<String> strings) {
        int max = 0;

        for (String string : strings) {
            int length = string.length();
            if (length > max)
                max = length;
        }

        return max;
    }

    /** Aumenta a largura da String 'string' para 'newLength' carateres, preenchendo-a com espaços */
    private static String enlarge(String string, int newLength) {
        int diff = newLength - string.length();

        if (diff <= 0)
            return string;
        else
            return string + " ".repeat(diff);
    }
}
