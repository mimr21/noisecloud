package model;

import java.io.*;


/** Classe que escreve linhas de texto e bytes de ficheiros */
public class EpicOutputStream {
    private DataOutputStream out;

    private static final String EOL = "\n";
    private static final int FILE_BUFFER_SIZE = 8192;       // == 8*1024 == 8KB


    public EpicOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public void flush() throws IOException {
        out.flush();
    }

    public void println(String line) throws IOException {
        out.writeUTF(line + EOL);
    }

    public void println(char c) throws IOException {
        out.writeUTF(c + EOL);
    }

    public void println(boolean b) throws IOException {
        out.writeUTF(b + EOL);
    }

    public void println(int n) throws IOException {
        out.writeUTF(n + EOL);
    }

    public void println(long l) throws IOException {
        out.writeUTF(l + EOL);
    }

    public void println(double d) throws IOException {
        out.writeUTF(d + EOL);
    }

    public void println(float f) throws IOException {
        out.writeUTF(f + EOL);
    }

    public void println(Cerealizable c) throws IOException {
        out.writeUTF(c.cerealize() + EOL);
    }

    public void sendFile(File file) throws IOException {
        if (!file.isFile())
            throw new FileNotFoundException(file.getAbsolutePath());

        // envia o tamanho do ficheiro
        println(file.length());

        // envia o ficheiro
        byte[] bytes = new byte[FILE_BUFFER_SIZE];
        int count;
        FileInputStream file_in = new FileInputStream(file);
        while ((count = file_in.read(bytes)) > 0)
            out.write(bytes, 0, count);
        file_in.close();
    }
}
