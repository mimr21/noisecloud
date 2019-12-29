package model;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;


/** Classe que lê linhas de texto e bytes de ficheiros */
public class EpicInputStream {
    private final DataInputStream in;
    private BufferedReader reader;

    private static final int FILE_BUFFER_SIZE = 8192;       // == 8*1024 == 8KB


    public EpicInputStream(DataInputStream in) {
        this.in = in;
        this.reader = null;
    }

    public String readLine() throws IOException {
        String line;

        if (reader == null || (line = reader.readLine()) == null) {
            reader = new BufferedReader(new StringReader(in.readUTF()));
            line = reader.readLine();
        }

        return line;
    }

    public void readFileTo(File file) throws IOException {
        if (file.createNewFile()) {
            // lê o tamanho do ficheiro
            long length = Long.parseLong(readLine());

            // lê os bytes para o ficheiro
            byte[] bytes = new byte[FILE_BUFFER_SIZE];
            int count = 0;
            FileOutputStream file_out = new FileOutputStream(file);
            while ((length -= count) > 0 && (count = in.read(bytes)) > 0)
                file_out.write(bytes, 0, count);
            file_out.flush();
            file_out.close();
        } else {
            throw new FileAlreadyExistsException(file.getAbsolutePath());
        }
    }
}
