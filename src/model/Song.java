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

    private final ReentrantLock lock = new ReentrantLock(true);


    public Song(int id, String title, String artist, int year, String[] tags, int downloads, String filename) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.tags = tags.clone();
        this.downloads = downloads;
        this.filename = filename;
    }

    public Song(Song m) {
        this.id = m.id;
        this.title = m.title;
        this.artist = m.artist;
        this.year = m.year;
        this.tags = m.tags.clone();
        this.downloads = m.downloads;
        this.filename = m.filename;
    }

    public static Song descerealize(String[] str_arr) {
        String[] tags = new String[str_arr.length - 6];
        System.arraycopy(str_arr, 6, tags, 0, tags.length);

        return new Song(Integer.parseInt(str_arr[0]),
                str_arr[1],
                str_arr[2],
                Integer.parseInt(str_arr[3]),
                tags,
                Integer.parseInt(str_arr[4]),
                str_arr[5]);
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

    public String[] cerealize() {
        String[] r = new String[6 + tags.length];

        r[0] = String.valueOf(id);
        r[1] = title;
        r[2] = artist;
        r[3] = String.valueOf(year);
        r[4] = String.valueOf(downloads);
        r[5] = filename;
        System.arraycopy(tags, 0, r, 6, tags.length);

        return r;
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
