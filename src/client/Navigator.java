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

    public int navigator_width() {
        if (horizontal) {
            final int size = Math.min(this.size, this.num_columns * this.num_lines);
            final int num_columns = Math.min(size, this.num_columns);
            final int last_column = num_columns - 1;
            int max_length = 0;
            int last_column_max_length = 0;

            for (int i = 0; i < size; ++i) {
                int length = strings.get(start_index + i).length();
                if (length > max_length)
                    max_length = length;
                if (i % num_columns == last_column && length > last_column_max_length)
                    last_column_max_length = length;
            }

            return max_length*last_column + TAB_LENGTH*last_column + last_column_max_length;
        } else {
            return 0; // todo
        }
    }

    private int printV(List<String> strings) {
        final int size = strings.size();

        if (num_columns == 1 || size <= num_lines) {
            int max_string_length = 0;

            for (String str : strings) {
                out.println(str);
                final int length = str.length();
                if (length > max_string_length)
                    max_string_length = length;
            }

            return max_string_length;
        } else {
            final int max_string_length = maxLength(strings);
            final int num_columns = size / num_lines + Math.min(size % num_lines, 1);
            final int jmax = num_columns - 1;
            int last_column_max_length = 0;

            for (int i = 0; i < num_lines; ++i) {
                out.print(enlarge(strings.get(i), max_string_length));

                int index = i + num_lines;
                for (int j = 1;
                     j < jmax && index < size;
                     ++j, index += num_lines) {
                    out.print(TAB + enlarge(strings.get(index), max_string_length));
                }

                if (index < size) {
                    final String str = strings.get(index);
                    out.print(TAB + str);
                    final int length = str.length();
                    if (length > last_column_max_length)
                        last_column_max_length = length;
                }

                out.println();
            }

            return max_string_length*jmax + TAB_LENGTH*jmax + last_column_max_length;
        }
    }

    private int printH(List<String> strings) {
        final int size = strings.size();
        final int num_columns = Math.min(this.num_columns, size);
        final int xmax = num_columns - 1;
        final int max_string_length = maxLength(strings);
        int last_column_max_length = 0;

        int i = 0;
        for (; i < size; ++i) {
            final int x = i % num_columns;

            if (x > 0)
                out.print(TAB);

            if (x == xmax) {
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

        return max_string_length*xmax + TAB_LENGTH*xmax + last_column_max_length;
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
