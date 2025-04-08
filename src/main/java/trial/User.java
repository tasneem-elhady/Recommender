package trial;

public class User {
    public  String name;
    public String id;
    public  String [] likedMovies;
/* 
    public User(String name, String id, String[] likedMovies) {
        this.name = name;
        this.id = id;
        this.likedMovies = likedMovies;
    } */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String[] getLikedMovies() {
        return likedMovies;
    }
}
