package gorillasquad.local;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    private List<String> upVotes;
    private List<String> downVotes;
    private String icon;
    private String colour;

    public Post() {
        upVotes = new ArrayList<>();
        downVotes = new ArrayList<>();
    }

    public Post(String author, String text, long timestamp, int rating, int reports,String icon,String colour) {
        this.author = author;
        this.text = text;
        this.timestamp = timestamp;
        this.rating = rating;
        this.reports = reports;
        this.upVotes = new ArrayList<>();
        this.downVotes = new ArrayList<>();
        this.icon = icon;
        this.colour = colour;
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

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) { this.reports = reports; }

    public String getKey() { return key; }

    public int getTimestamp() {
        return rating;
    }

    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getAuthour() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public List<String> getUpVotes() { return upVotes; }

    public void setUpVotes(List<String> upVotes) { this.upVotes = upVotes; }

    public List<String> getDownVotes() { return downVotes; }

    public void setDownVotes(List<String> downVotes) { this.downVotes = downVotes; }

    public String getColour() { return colour; }

    public void setColour(String colour) { this.colour = colour; }

    public void addVote(boolean upVote, String id){
        if(upVotes == null || downVotes == null)
            return;
        if(upVote)
            upVotes.add(id);
        else
            downVotes.add(id);
    }

    public void removeVote(String id){
        if(upVotes == null || downVotes == null)
            return;

        if(upVotes.contains(id)) {
            Log.d("POST","Removing from upvotes");
            upVotes.remove(upVotes.indexOf(id));
        }
        if(downVotes.contains(id)) {
            Log.d("POST","Removing from downvotes");
            downVotes.remove(downVotes.indexOf(id));
        }
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("text",text);
        if(author != null) {
            if(!author.equals("")) { //incase it is null putting this on a new line wont throw nullexception
                map.put("author", author);
            }
        }
        map.put("icon", icon);
        map.put("colour", colour);
        map.put("timestamp",timestamp);
        map.put("rating",rating);
        map.put("reports",reports);
        map.put("upVotes",upVotes);
        map.put("downVotes",downVotes);
        return map;
    }
}
