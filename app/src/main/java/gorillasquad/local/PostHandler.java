package gorillasquad.local;

import android.util.Log;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostHandler {
    String TAG = "PostHandler";

    private Database db;
    private String myId;

    public PostHandler(String id){
        db = Database.getDB();
        myId = id;
    }

    public void addPost(String text) {
        Log.d(TAG,"Adding new post");
        String[] keys = new String[] {"author","text","timestamp","rating","reports"};
        long time = System.currentTimeMillis();
        db.addNew("root/post",keys,myId,text,time,0,0);
    }

    public void upVote(int postId) {

    }

    public void downVote(int postId) {

    }

    public void report(int id, String text) {

    }
}
