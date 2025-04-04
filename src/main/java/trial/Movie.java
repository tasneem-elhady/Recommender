package trial;

public class Movie {
    public String[] getGenre() {
        return genre;
    }

    public String title;
    public String id;
    public String []genre;

    public Movie(String title, String id, String[] genre) {
        this.title = title;
        this.id = id;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
