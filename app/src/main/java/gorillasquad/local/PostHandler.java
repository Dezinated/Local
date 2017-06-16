package gorillasquad.local;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import gorillasquad.local.emojis.Nature;

import static android.content.ContentValues.TAG;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostHandler {
    String TAG = "PostHandler";

    private Database db;
    private String myId;
    private Context c;

    private MainPostHandler mph;
    private CommentHandler ch;

    public PostHandler(String id, Context c){
        db = Database.getDB();
        myId = id;
        mph = new MainPostHandler(id,c,this);
        ch = new CommentHandler(id,"",c,this);
    }

    public MainPostHandler getMph() {
        return mph;
    }
    public CommentHandler getCh() {
        return ch;
    }

    public Nature[] icons = {Nature.DOG_FACE, Nature.CAT_FACE, Nature.MOUSE_FACE, Nature.HAMSTER_FACE, Nature.RABBIT_FACE, Nature.MONKEY_FACE, Nature.KOALA_FACE};

    public String[] colours = {"#0074D9", "#FF851B", "#01FF70", "#85144b", "#468499", "#800080","#daa520"};

    public int getHash(String id, String postId) {
        return Math.abs(id.hashCode()
                + postId.hashCode());
    }

    public String getMyId() { return myId; }

    public String iconFromHash(int hash) {
        return icons[hash % icons.length].toString();
    }

    public String colourFromHash(int hash) {
        return colours[hash % colours.length];
    }

    public void addPost(String text,String location,int hash) {
        Log.d(TAG,"Adding new post");
        addPost(text,location,iconFromHash(hash),colourFromHash(hash),hash);
    }

    public void addPost(String text, String location, String icon, String colour,int hash){
        String key = db.addNew("root/"+location);
        if(hash == 0){
            hash = getHash(myId,key);
        }
        Post p = new Post(myId,text,key,0,0,0,icon,colour,hash);
        Log.d(TAG,p.getKey());
        db.set("root/"+location+"/"+key,p.toMap());
    }



    public void voteComment(String postId, boolean upVote){
        for(Post p:ch.getComments()) {
            if (p.getKey().equals(postId)) {
                vote(p,upVote);
                db.update("root/comments/"+ch.getMainPostId()+"/"+p.getKey(),p.toMap());
            }
        }
    }

    public void votePost(Post p, boolean upVote){
        vote(p,upVote);
        db.update("root/post/"+p.getKey(),p.toMap());
    }

    public void vote(Post p,boolean upVote) {

        Log.d(TAG,"Vote");
        if(upVote) {
            if(p.getUpVotes().contains(myId)) { //if they press up again then remove their vote
                p.removeVote(myId);
                p.setRating(p.getRating() - 1);
            }else if(p.getDownVotes().contains(myId)) { //if they switch ratings
                p.removeVote(myId);
                p.setRating(p.getRating() + 2);
                p.addVote(upVote, myId);
            }else{ //first time they press the up button
                p.addVote(upVote, myId);
                p.setRating(p.getRating() + 1);
            }
        }else {
            if (p.getDownVotes().contains(myId)) { //if they press down again then remove their vote
                p.removeVote(myId);
                p.setRating(p.getRating() + 1);
            } else if (p.getUpVotes().contains(myId)) { //if they switch ratings
                p.removeVote(myId);
                p.setRating(p.getRating() - 2);
                p.addVote(upVote, myId);
            } else { //first time they press the down button
                p.addVote(upVote, myId);
                p.setRating(p.getRating() - 1);
            }
        }


    }

    public void report(int id, String text) {

    }



    //create async call to get post values and update array list here or some shit
}
