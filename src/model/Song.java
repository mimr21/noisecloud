package model;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;


public class Song implements Lockable, Cerealizable, Comparable<Song> {
    private final int id;
    private String title;
    private String artist;
    private int year;
    private String[] tags;
    private int downloads;
    private String filename;

    private final ReentrantLock lock;


    public Song(int id, String title, String artist, int year, String[] tags, int downloads, String filename) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.tags = tags.clone();
        this.downloads = downloads;
        this.filename = filename;
        this.lock = new ReentrantLock(true);
    }

    public Song(Song m) {
        this.id = m.id;
        this.title = m.title;
        this.artist = m.artist;
        this.year = m.year;
        this.tags = m.tags.clone();
        this.downloads = m.downloads;
        this.filename = m.filename;
        this.lock = new ReentrantLock(true);
    }

    public static Song descerealize(String s) {
        String[] args = s.split(Cerealizable.ARGS_SEPARATOR);
        return new Song(Integer.parseInt(args[0]), args[1], args[2], Integer.parseInt(args[3]),
                args[4].split(Cerealizable.ARRAY_SEPARATOR), Integer.parseInt(args[5]), args[6]);
    }

    public int getID() {return this.id;}
    public String getTitle() {return this.title;}
    public String getArtist() {return this.artist;}
    public int getYear() {return this.year;}
    public String[] getTags() {return this.tags.clone();}
    public int getDownloads() {return this.downloads;}
    public String getFilename() {return filename;}

    public void setTitle(String t) {this.title = t;}
    public void setArtist(String a) {this.artist = a;}
    public void setYear(int y) {this.year = y;}
    public void setTags(String[] t) {this.tags = t.clone();}
    public void setDownloads(int d) {this.downloads = d;}
    public void setFilename(String filename) {this.filename = filename;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Song s = (Song) o;
        return this.id == s.id;
    }

    public Song clone() {return new Song(this);}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Música {id=").append(id)
                .append(", título='").append(title)
                .append("', intérprete='").append(artist)
                .append("', ano=").append(year)
                .append(", tags=").append(Arrays.toString(tags))
                .append(", downloads=").append(downloads)
                .append(", nome_do_ficheiro='").append(filename)
                .append("'}");
        return sb.toString();
    }

    public int hashCode() {
        return id;
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public String cerealize() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(Cerealizable.ARGS_SEPARATOR)
                .append(title).append(Cerealizable.ARGS_SEPARATOR)
                .append(artist).append(Cerealizable.ARGS_SEPARATOR)
                .append(year).append(Cerealizable.ARGS_SEPARATOR)
                .append(Cerealizable.cerealize(tags)).append(Cerealizable.ARGS_SEPARATOR)
                .append(downloads).append(Cerealizable.ARGS_SEPARATOR)
                .append(filename);
        return sb.toString();
    }

    public int compareTo(Song s) {
        int r;
        r = this.title.compareTo(s.title);
        if (r == 0) {
            r = this.artist.compareTo(s.artist);
            if (r == 0)
                r = Integer.compare(this.id, s.id);
        }
        return r;
    }
}
