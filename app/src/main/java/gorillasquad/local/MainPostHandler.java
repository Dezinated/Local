package gorillasquad.local;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Jason on 6/14/2017.
 */

public class MainPostHandler {

    private Database db;
    private String myId;
    private ArrayList<Post> posts;
    private Context c;
    PostAdapter pa;
    PostHandler ph;

    public MainPostHandler(String id, Context c, PostHandler ph) {
        posts = new ArrayList<Post>();
        db = Database.getDB();
        myId = id;
        pa = new PostAdapter(c,posts,ph);
        db.getRef("post").orderByChild("timestamp").addChildEventListener(postListener);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public PostAdapter getPostAdapter() {
        return pa;
    }

    ChildEventListener postListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Post post = dataSnapshot.getValue(Post.class);
            Log.d(TAG,"Text: " + dataSnapshot.toString());
            posts.add(0,post);
            pa.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Post post = dataSnapshot.getValue(Post.class);
            Log.d(TAG,"Text: " + dataSnapshot.toString());
            Log.d(TAG,"Text: " + post.getKey());
            for(Post p:posts) {
                Log.d(TAG,"Text: " + p.getKey());
                if(p.getKey().equals(post.getKey())){
                    posts.set(posts.indexOf(p),post);
                }
            }

            pa.notifyDataSetChanged();
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
