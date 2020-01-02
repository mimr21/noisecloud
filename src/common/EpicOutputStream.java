package common;

import java.io.*;


/** Classe que escreve linhas de texto e bytes de ficheiros */
public class EpicOutputStream {
    private final DataOutputStream out;

    private static final String EOL = "\n";
    private static final int FILE_BUFFER_SIZE = 8192;       // == 8*1024 == 8KB


    public EpicOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public void flush() throws IOException {
        out.flush();
    }

    // Strings

    /** Escreve uma String numa linha */
    public void println(String str) throws IOException {
        out.writeUTF(str + EOL);
    }

    // Tipos primitivos

    /** Escreve um char numa linha */
    public void println(char c) throws IOException {
        out.writeUTF(c + EOL);
    }

    /** Escreve um boolean numa linha */
    public void println(boolean b) throws IOException {
        out.writeUTF(b + EOL);
    }

    /** Escreve um int numa linha */
    public void println(int n) throws IOException {
        out.writeUTF(n + EOL);
    }

    /** Escreve um long numa linha */
    public void println(long l) throws IOException {
        out.writeUTF(l + EOL);
    }

    /** Escreve um double numa linha */
    public void println(double d) throws IOException {
        out.writeUTF(d + EOL);
    }

    /** Escreve um float numa linha */
    public void println(float f) throws IOException {
        out.writeUTF(f + EOL);
    }

    // Arrays de Strings

    /** Escreve numa linha o tamanho do array e nas linhas seguintes as Strings */
    public void print(String[] str_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append(str_arr.length).append(EOL);

        for (String str : str_arr)
            sb.append(str).append(EOL);

        out.writeUTF(sb.toString());
    }

    // Arrays de tipos primitivos

    /** Escreve os carateres seguidos numa linha */
    public void println(char[] c_arr) throws IOException {
        println(String.valueOf(c_arr));
    }

    /** Escreve os booleans numa linha, separados por um espaço */
    public void println(boolean[] b_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        int size = b_arr.length;

        if (size > 0) {
            sb.append(b_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(" ").append(b_arr[i]);
        }

        out.writeUTF(sb.toString() + EOL);
    }

    /** Escreve os ints numa linha, separados por um espaço */
    public void println(int[] n_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        int size = n_arr.length;

        if (size > 0) {
            sb.append(n_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(" ").append(n_arr[i]);
        }

        out.writeUTF(sb.toString() + EOL);
    }

    /** Escreve os longs numa linha, separados por um espaço */
    public void println(long[] l_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        int size = l_arr.length;

        if (size > 0) {
            sb.append(l_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(" ").append(l_arr[i]);
        }

        out.writeUTF(sb.toString() + EOL);
    }

    /** Escreve os doubles numa linha, separados por um espaço */
    public void println(double[] d_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        int size = d_arr.length;

        if (size > 0) {
            sb.append(d_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(" ").append(d_arr[i]);
        }

        out.writeUTF(sb.toString() + EOL);
    }

    /** Escreve os floats numa linha, separados por um espaço */
    public void println(float[] f_arr) throws IOException {
        StringBuilder sb = new StringBuilder();

        int size = f_arr.length;

        if (size > 0) {
            sb.append(f_arr[0]);
            for (int i = 1; i < size; ++i)
                sb.append(" ").append(f_arr[i]);
        }

        out.writeUTF(sb.toString() + EOL);
    }

    // Ficheiros

    /** Escreve o tamanho do ficheiro numa linha, e os bytes do ficheiro a seguir */
    public void print(File src) throws IOException {
        if (!src.isFile())
            throw new FileNotFoundException(src.getAbsolutePath());

        // envia o tamanho do ficheiro
        println(src.length());

        // envia o ficheiro
        byte[] bytes = new byte[FILE_BUFFER_SIZE];
        int count;
        FileInputStream file_in = new FileInputStream(src);
        while ((count = file_in.read(bytes)) > 0)
            out.write(bytes, 0, count);
        file_in.close();
    }
}
