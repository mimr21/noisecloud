package client;

import static java.lang.System.out;


// package-private
class View {
    private static final String NAVIGATOR_BAR = "[ sair: s | anterior: a | próximo: p | redimensionar: r <i> <j> | mudar orientação: o ]";
    private static final String PADDING = "#";
    private static final int NAVIGATOR_BAR_WIDTH;

    static {
        NAVIGATOR_BAR_WIDTH = NAVIGATOR_BAR.length();
    }


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
        out.println("1) Todas as músicas           4) Procurar por intérprete        7) Upload");
        out.println("2) Top downloads              5) Procurar por tag               8) Utilizadores");
        out.println("3) Procurar por título        6) Download                       9) Log out");
    }

    public void printNavigatorBar(int width) {
        int diff = width - NAVIGATOR_BAR_WIDTH;

        if (diff <= 0) {
            out.println(NAVIGATOR_BAR);
        } else {
            int left_padding = diff / 2;
            int right_padding = left_padding + diff % 2;

            out.println(PADDING.repeat(left_padding) + NAVIGATOR_BAR + PADDING.repeat(right_padding));
        }
    }

    public void err(String msg) {
        out.println("Erro: " + msg);
    }

    public void err(Exception e) {
        out.println("Erro: " + e.getMessage());
    }
}
