package gorillasquad.local;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jason on 6/14/2017.
 */

public class CommentHandler {

    private String TAG = "CommentHandler";
    private Database db;
    private String myId;
    private ArrayList<Post> comments;
    private Context c;
    CommentAdapter ca;
    PostHandler ph;
    private Post mainPost;

    public CommentHandler(String id, Post p, Context c, PostHandler ph) {
        comments = new ArrayList<Post>();
        db = Database.getDB();
        myId = id;
        this.mainPost = p;
        ca = new CommentAdapter(c,comments,ph,p.getOwnerHash());
        //db.getRef("comments/"+mainPostId).orderByChild("timestamp").addChildEventListener(commentListener);
    }

    public Post getMainPost() { return mainPost; }

    public ArrayList<Post> getComments() {
        return comments;
    }

    public CommentAdapter getCommentAdapter() {
        return ca;
    }

    public void setPost(Post p){
        db.getRef("comments/"+mainPost.getKey()).removeEventListener(commentListener);
        mainPost = p;
        db.getRef("comments/"+mainPost.getKey()).orderByChild("timestamp").addChildEventListener(commentListener);
    }

    ChildEventListener commentListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG,"added: " + dataSnapshot.toString());

            Post post = dataSnapshot.getValue(Post.class);
            comments.add(post);
            ca.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Post post;
/*
            Log.d(TAG,"change: " + dataSnapshot.toString());
            for(Post p:comments) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    post = messageSnapshot.getValue(Post.class);
                    Log.d(TAG,""+p.getText());
                    if(p.getKey().equals(post.getKey())){
                        comments.set(comments.indexOf(p),post);
                    }
                }
                Log.d(TAG,"Text: " + p.getKey());

            }
*/
            ca.notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };

}
