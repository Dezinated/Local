package gorillasquad.local;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 6/13/2017.
 */

public class Post {

    private String key;
    private String text;
    private String author;
    private long timestamp;
    private int rating;
    private int reports;

    public Post() {

    }

    public Post(String author, String text, long timestamp, int rating, int reports) {
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
        this.rating = rating;
        this.reports = reports;
    }

    public Post(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) { this.rating = rating; }

    public String getKey() { return key; }

    public void setAuthor(String author) { this.author = author; }

    public Map<String, Object> toMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("text",text);
        map.put("author",author);
        map.put("timestamp",timestamp);
        map.put("rating",rating);
        map.put("reports",reports);
        return map;
    }
}
