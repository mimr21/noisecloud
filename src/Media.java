import java.util.ArrayList;
import java.util.List;

public class Media {
    private int id;
    private String name;
    private String artist;
    private int year;
    private List<String> tags;
    private int downloads;

    public Media(int id, String n, String a, int y, List<String> t, int d){
        this.id = id;
        this.name = n;
        this.artist = a;
        this.year = y;
        this.tags = new ArrayList<>(t);
        this.downloads = d;
    }

    public Media(Media m){
        this.id = m.getID();
        this.name = m.getName();
        this.artist = m.getArtist();
        this.year = m.getYear();
        this.tags = m.getTags();
        this.downloads = m.getDownloads();
    }

    public int getID() {return this.id;}
    public String getName() {return this.name;}
    public String getArtist() {return this.artist;}
    public int getYear() {return this.year;}
    public List<String> getTags() {return new ArrayList<>(tags);}
    public int getDownloads() { return this.downloads;}

    public void setID(int id) {this.id = id;}
    public void setFilename(String f) {this.name = f;}
    public void setArtist(String a) {this.artist = a;}
    public void setYear(int y) {this.year = y;}
    public void setTags(List<String> t) {this.tags = new ArrayList<>(t);}
    public void setDownloads(int d) {this.downloads = d;}

    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Media m = (Media) o;
        return (this.id == m.getID() /*&&
                this.name.equals(m.getName()) &&
                this.artist.equals(m.getArtist()) &&
                this.year == m.getYear() &&
                this.tags.equals(m.getTags()) &&
                this.downloads == m,getDownloads()*/);
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
