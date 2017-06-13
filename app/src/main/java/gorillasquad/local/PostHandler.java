package gorillasquad.local;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostHandler {
    String TAG = "PostHandler";

    private Database db;
    private String myId;
    private ArrayList<Post> posts;
    private Context c;
    PostAdapter pa;

    public PostHandler(String id, Context c){
        posts = new ArrayList<Post>();
        db = Database.getDB();
        myId = id;
        pa = new PostAdapter(c,getPosts(),this);
        db.getPostsRef().addChildEventListener(postListener);
        Log.d(TAG,db.getPostsRef().toString());
    }

    public void addPost(String text) {
        Log.d(TAG,"Adding new post");
        String[] keys = new String[] {"key","author","text","timestamp","rating","reports"};
        long time = System.currentTimeMillis();
        db.addNew("root/post",keys,"key",myId,text,time,0,0);
    }

    public void upVote(int postId) {

    }

    public void downVote(int postId) {

    }

    public void report(int id, String text) {

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
            posts.add(post);
            pa.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    //create async call to get post values and update array list here or some shit
}
