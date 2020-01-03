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

    public void print(List<String> strs, final int STRINGS_PER_LINE) {
        final int NUM_STRINGS = strs.size();
        final int STRING_WIDTH = maxWidth(strs);

        int i = 0;
        for (; i < NUM_STRINGS; ++i) {
            int x = i % STRINGS_PER_LINE;

            if (x > 0)
                out.print(TAB);

            if (x == STRINGS_PER_LINE-1)
                out.println(strs.get(i));
            else
                out.print(enlarge(strs.get(i), STRING_WIDTH));
        }

        if (i % STRINGS_PER_LINE != 0)
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

    private String enlarge(String str, int newLength) {
        int diff = newLength - str.length();

        if (diff <= 0)
            return str;
        else
            return str + " ".repeat(diff);
    }
}
