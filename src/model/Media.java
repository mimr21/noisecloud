package model;


public class Media {
    private int id;
    private String name;
    private String artist;
    private int year;
    private String[] tags;
    private int downloads;
    private byte[] music;

    private int MAXVALUE = 1024;

    public Media(int id, String n, String a, int y, String[] t, int d, byte[] m){
        this.id = id;
        this.name = n;
        this.artist = a;
        this.year = y;
        System.arraycopy(t, 0 , this.tags, 0 , t.length);
        this.downloads = d;
        System.arraycopy(m, 0 , this.music, 0, m.length);
    }

    public Media(Media m){
        this.id = m.getID();
        this.name = m.getName();
        this.artist = m.getArtist();
        this.year = m.getYear();
        this.tags = m.getTags();
        this.downloads = m.getDownloads();
        this.music = m.getMusic();
    }

    public int getID() {return this.id;}
    public String getName() {return this.name;}
    public String getArtist() {return this.artist;}
    public int getYear() {return this.year;}
    public String[] getTags() {
        String [] t = new String[this.tags.length];
        System.arraycopy(this.tags, 0 , t, 0, this.tags.length);
        return t;
    }
    public int getDownloads() { return this.downloads;}
    public byte[] getMusic() {
        byte[] m = new byte[this.MAXVALUE];
        System.arraycopy(this.music, 0 , m, 0, this.music.length);
        return m;
    }


    public void setID(int id) {this.id = id;}
    public void setFilename(String f) {this.name = f;}
    public void setArtist(String a) {this.artist = a;}
    public void setYear(int y) {this.year = y;}
    public void setTags(String[] t) {System.arraycopy(t, 0 ,this.tags, 0, t.length);}
    public void setDownloads(int d) {this.downloads = d;}
    public void setMusic(byte[] m) {System.arraycopy(m, 0 ,this.music, 0, m.length);}

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Media m = (Media) o;
        return (this.id == m.getID() /*&&
                this.name.equals(m.getName()) &&
                this.artist.equals(m.getArtist()) &&
                this.year == m.getYear() &&
                this.tags.equals(m.getTags()) &&
                this.downloads == m.getDownloads()
                this.music.equals(m.getMusic())*/);
    }

    public Media clone(){return new Media(this);}
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Música {");
        sb.append("id=").append(this.id);
        sb.append(", título='").append(this.name);
        sb.append("', intérprete='").append(this.artist);
        sb.append("', tags=").append(this.tags.toString());
        sb.append("', downloads=").append(this.downloads);
        sb.append("}");
        return sb.toString();
    }

}
