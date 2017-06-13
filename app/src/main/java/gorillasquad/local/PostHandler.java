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
        db.getPostsRef().orderByChild("timestamp").addChildEventListener(postListener);
        Log.d(TAG,db.getPostsRef().toString());
    }

    public void addPost(String text) {
        Log.d(TAG,"Adding new post");

        long time = System.currentTimeMillis();
        Post p = new Post(myId,text,time,0,0);
        db.addNew("root/post",p.toMap(),true);
    }

    public void vote(String postId,boolean upVote) {
        for(Post p:posts) {
            if(p.getKey().equals(postId)){
                if(upVote)
                    p.setRating(p.getRating()+1);
                else
                    p.setRating(p.getRating()-1);
                db.update("root/post/"+p.getKey(),p.toMap());
            }
        }
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
    //create async call to get post values and update array list here or some shit
}
