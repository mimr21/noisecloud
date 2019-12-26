package model;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;


public class Song implements Lockable {
    private final int id;
    private String title;
    private String artist;
    private int year;
    private String[] tags;
    private int downloads;
    private ReentrantLock lock;


    public Song(int id, String title, String artist, int year, String[] tags, int downloads) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.tags = tags.clone();
        this.downloads = downloads;
        this.lock = new ReentrantLock(true);
    }

    public Song(Song m) {
        this.id = m.id;
        this.title = m.title;
        this.artist = m.artist;
        this.year = m.year;
        this.tags = m.tags.clone();
        this.downloads = m.downloads;
        this.lock = new ReentrantLock(true);
    }

    public int getID() {return this.id;}
    public String getTitle() {return this.title;}
    public String getArtist() {return this.artist;}
    public int getYear() {return this.year;}
    public String[] getTags() {return this.tags.clone();}
    public int getDownloads() { return this.downloads;}

    //public void setID(int id) {this.id = id;}
    public void setTitle(String t) {this.title = t;}
    public void setArtist(String a) {this.artist = a;}
    public void setYear(int y) {this.year = y;}
    public void setTags(String[] t) {this.tags = t.clone();}
    public void setDownloads(int d) {this.downloads = d;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Song s = (Song) o;
        return this.id == s.id /*&&
                this.name.equals(m.getName()) &&
                this.artist.equals(m.getArtist()) &&
                this.year == m.getYear() &&
                this.tags.equals(m.getTags()) &&
                this.downloads == m.getDownloads()
                this.music.equals(m.getMusic())*/;
    }

    public Song clone() {return new Song(this);}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Música {");
        sb.append("id=").append(this.id);
        sb.append(", título='").append(this.title);
        sb.append("', intérprete='").append(this.artist);
        sb.append("', ano=").append(this.year);
        sb.append(", tags=").append(Arrays.toString(this.tags));
        sb.append(", downloads=").append(this.downloads);
        sb.append("}");
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
}
