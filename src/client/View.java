package client;

import java.util.List;
import static java.lang.System.out;


// package-private
class View {
    private static final String TAB = "    ";


    public View() {}

    public void logo() {
        out.println(" ____ __           __                          __                      __");
        out.println("|    |  |  _____  |__|  _____   _____   ____  |  |  _____   __ __   __|  |");
        out.println("|       | |  _  | |  | |__ --| |  -__| |  __| |  | |  _  | |  |  | |  _  |");
        out.println("|__|____| |_____| |__| |_____| |_____| |____| |__| |_____| |_____| |_____|");
    }

    public void login_menu() {
        out.println("1) Entrar");
        out.println("2) Criar conta");
        out.println("3) Sair");
    }

    public void main_menu() {
        out.println("1) Todas as músicas");
        out.println("2) Procurar por título");
        out.println("3) Procurar por intérprete");
        out.println("4) Procurar por tag");
        out.println("5) Upload");
        out.println("6) Utilizadores");
        out.println("7) Log out");
    }

    public void err(String msg) {
        out.println("Erro: " + msg);
    }

    public void err(Exception e) {
        out.println("Erro: " + e.getMessage());
    }

    /** Imprime a lista 'strs' na vertical, com 'num_columns' colunas */
    public void printV(List<String> strs, final int num_columns) {
        if (num_columns <= 1) {
            for (String str : strs)
                out.println(str);
        } else {
            final int num_strings = strs.size();
            final int string_width = maxWidth(strs);
            final int num_strings_per_column = num_strings / num_columns;
            final int remain = num_strings % num_columns;

            int i = 0;
            int jmax = num_columns - 1;
            for (; i < num_strings_per_column; ++i) {
                // primeiro elemento
                out.print(enlarge(strs.get(i), string_width));

                // elementos no meio
                int k;
                for (int j = 1; j < jmax; ++j) {
                    k = i + j * num_strings_per_column + Math.min(j, remain);
                    out.print(TAB + enlarge(strs.get(k), string_width));
                }

                // último elemento
                k = i + jmax * num_strings_per_column + Math.min(jmax, remain);
                out.println(TAB + strs.get(k));
            }

            // última linha
            if (remain == 1) {
                out.println(strs.get(i));
            } else if (remain > 1) {
                // primeiro elemento
                out.print(enlarge(strs.get(i), string_width));

                // elementos no meio
                final int iInc = num_strings_per_column + 1;
                jmax = remain - 1;
                for (int j = 1; j < jmax; ++j) {
                    i += iInc;
                    out.print(TAB + enlarge(strs.get(i), string_width));
                }

                // último elemento
                out.println(TAB + strs.get(i + iInc));
            }
        }
    }

    /** Imprime a lista 'strs' na horizontal, com 'num_columns' colunas */
    public void printH(List<String> strs, int num_columns) {
        if (num_columns <= 0)
            num_columns = 1;

        final int num_strings = strs.size();
        final int string_width = maxWidth(strs);

        int i = 0;
        for (; i < num_strings; ++i) {
            int x = i % num_columns;

            if (x > 0)
                out.print(TAB);

            if (x == num_columns-1)
                out.println(strs.get(i));
            else
                out.print(enlarge(strs.get(i), string_width));
        }

        if (i % num_columns != 0)
            out.println();
    }

    private int maxWidth(List<String> strs) {
        int max = 0;

        for (String str : strs) {
            int length = str.length();
            if (length > max)
                max = length;
        }

        return max;
    }

    /** Aumenta a largura da String 'str' para 'newLength' carateres, preenchendo-a com espaços */
    private String enlarge(String str, final int newLength) {
        final int diff = newLength - str.length();

        if (diff <= 0)
            return str;
        else
            return str + " ".repeat(diff);
    }
}
