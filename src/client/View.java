package client;

import static java.lang.System.out;


// package-private
class View {
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
        out.println("1) Upload");
        out.println("2) Utilizadores");
        out.println("3) Procurar");
        out.println("4) Log out");
    }

    public void linebreak(int n) {
        for (int i = 0; i < n; ++i)
            out.println();
    }

    public void err(String msg) {
        out.println("Erro: " + msg);
    }

    public void err(Exception e) {
        out.println("Erro: " + e.getMessage());
    }
}
