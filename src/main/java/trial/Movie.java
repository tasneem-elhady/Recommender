package trial;

public class Movie {
    public String[] getGenre() {
        return genre;
    }

    public String title;
    public String id;
    public String []genre;

    public String getUniqueNumbers() {
        return uniqueNumbers;
    }

    public String uniqueNumbers;

    public Movie(String title, String id, String[] genre) {
        this.title = title;
        this.id = id;
        this.genre = genre;
        this.uniqueNumbers = id.substring(id.length()-3);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
