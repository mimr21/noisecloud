package common;

import static common.Noisecloud.MAXSIZE;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;


/** Classe que lê linhas de texto e bytes de ficheiros */
public class EpicInputStream {
    private final DataInputStream in;
    private BufferedReader reader;


    public EpicInputStream(DataInputStream in) {
        this.in = in;
        this.reader = null;
    }

    // Strings

    public String readLine() throws IOException {
        String line;

        if (reader == null || (line = reader.readLine()) == null) {
            reader = new BufferedReader(new StringReader(in.readUTF()));
            line = reader.readLine();
        }

        return line;
    }

    // Tipos primitivos

    public char readLineToChar() throws IOException {
        return readLine().charAt(0);
    }

    public boolean readLineToBoolean() throws IOException {
        return Boolean.parseBoolean(readLine());
    }

    public int readLineToInt() throws IOException {
        return Integer.parseInt(readLine());
    }

    public long readLineToLong() throws IOException {
        return Long.parseLong(readLine());
    }

    public double readLineToDouble() throws IOException {
        return Double.parseDouble(readLine());
    }

    public float readLineToFloat() throws IOException {
        return Float.parseFloat(readLine());
    }

    // Arrays de Strings

    public String[] readStringArray() throws IOException {
        int size = readLineToInt();

        String[] r = new String[size];

        for (int i = 0; i < size; ++i)
            r[i] = readLine();

        return r;
    }

    // Arrays de tipos primitivos

    public char[] readLineToCharArray() throws IOException {
        return readLine().toCharArray();
    }

    public boolean[] readLineToBooleanArray() throws IOException {
        String[] strs = readLine().split(" ");
        int size = strs.length;
        boolean[] r = new boolean[size];

        for (int i = 0; i < size; ++i)
            r[i] = Boolean.parseBoolean(strs[i]);

        return r;
    }

    public int[] readLineToIntArray() throws IOException {
        String[] strs = readLine().split(" ");
        int size = strs.length;
        int[] r = new int[size];

        for (int i = 0; i < size; ++i)
            r[i] = Integer.parseInt(strs[i]);

        return r;
    }

    public long[] readLineToLongArray() throws IOException {
        String[] strs = readLine().split(" ");
        int size = strs.length;
        long[] r = new long[size];

        for (int i = 0; i < size; ++i)
            r[i] = Long.parseLong(strs[i]);

        return r;
    }

    public double[] readLineToDoubleArray() throws IOException {
        String[] strs = readLine().split(" ");
        int size = strs.length;
        double[] r = new double[size];

        for (int i = 0; i < size; ++i)
            r[i] = Double.parseDouble(strs[i]);

        return r;
    }

    public float[] readLineToFloatArray() throws IOException {
        String[] strs = readLine().split(" ");
        int size = strs.length;
        float[] r = new float[size];

        for (int i = 0; i < size; ++i)
            r[i] = Float.parseFloat(strs[i]);

        return r;
    }

    // Ficheiros

    public void readFile(File dest) throws IOException {
        if (dest.createNewFile()) {
            // lê o tamanho do ficheiro
            long length = Long.parseLong(readLine());

            // lê os bytes para o ficheiro
            byte[] bytes = new byte[MAXSIZE];
            int count = 0;
            FileOutputStream file_out = new FileOutputStream(dest);
            while ((length -= count) > 0 && (count = in.read(bytes)) > 0)
                file_out.write(bytes, 0, count);
            file_out.flush();
            file_out.close();
        } else {
            throw new FileAlreadyExistsException(dest.getAbsolutePath());
        }
    }
}
