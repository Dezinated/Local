package gorillasquad.local;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason on 6/13/2017.
 */

public class Post implements Parcelable {

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
    private int ownerHash;

    public Post() {
        upVotes = new ArrayList<>();
        downVotes = new ArrayList<>();
    }

    public Post(String author, String text, String key, long timestamp, int rating, int reports,String icon,String colour,int ownerHash) {
        this.author = author;
        this.text = text;
        this.key = key;
        this.timestamp = timestamp;
        this.rating = rating;
        this.reports = reports;
        this.upVotes = new ArrayList<>();
        this.downVotes = new ArrayList<>();
        this.icon = icon;
        this.colour = colour;
        this.ownerHash = ownerHash;
    }

    public Post(Parcel in){
        this.author = in.readString();
        this.text = in.readString();
        this.key = in.readString();
        this.timestamp = in.readLong();
        this.rating = in.readInt();
        this.reports = in.readInt();
        this.upVotes = in.readArrayList(String.class.getClassLoader());
        this.downVotes = in.readArrayList(String.class.getClassLoader());
        this.icon = in.readString();
        this.colour = in.readString();
        this.ownerHash = in.readInt();
    }

    public String getKey() {
        return key;
    }

    public void setOwnerHash(int ownerHash) { this.ownerHash = ownerHash; }

    public int getOwnerHash() { return ownerHash; }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public List<String> getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(List<String> upVotes) {
        this.upVotes = upVotes;
    }

    public List<String> getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(List<String> downVotes) {
        this.downVotes = downVotes;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

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
        if(timestamp == 0){
            map.put("timestamp", ServerValue.TIMESTAMP);
        }else {
            map.put("timestamp", timestamp);
        }
        map.put("rating",rating);
        map.put("key",key);
        map.put("ownerHash",ownerHash);
        map.put("reports",reports);
        map.put("upVotes",upVotes);
        map.put("downVotes",downVotes);
        return map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(author);
        out.writeString(text);
        out.writeString(key);
        out.writeLong(timestamp);
        out.writeInt(rating);
        out.writeInt(reports);
        out.writeList(upVotes);
        out.writeList(downVotes);
        out.writeString(icon);
        out.writeString(colour);
        out.writeInt(ownerHash);
    }

    public static final Parcelable.Creator<Post> CREATOR
            = new Parcelable.Creator<Post>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

}
