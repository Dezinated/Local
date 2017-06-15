package gorillasquad.local;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by Jason on 6/14/2017.
 */

public class ViewPost extends AppCompatActivity {

    private String TAG = "ViewPost";

    private String postId;
    private int ownerHash;
    private PostHandler ph;
    private String myId;

    public ViewPost() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        setTitle("");
        Log.d(TAG,"Created view post");

        myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ph = new PostHandler(myId,this);

        ListView commentList = (ListView) findViewById(R.id.commentsList);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)myinflater.inflate(R.layout.view_post, commentList, false);
        ViewGroup footer = (ViewGroup)myinflater.inflate(R.layout.enter_message, commentList, false);
        commentList.addHeaderView(header, null, false);
        commentList.addFooterView(footer, null, false);

        postId = getIntent().getStringExtra("postId");
        ownerHash = getIntent().getIntExtra("ownerHash",0);

        ph.getCh().setPostId(postId);
        commentList.setAdapter(ph.getCh().getCommentAdapter());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    public void buttonClicked(View v) {
        int amount = 3;

        v = (View) v.getParent();
        EditText postText = (EditText) v.findViewById(R.id.newPost);
        if(postText.getText().length() < amount) {
            Toast.makeText(this, "Enter a message with more than "+amount+" characters.", Toast.LENGTH_SHORT).show();
        }else {
            Log.d(TAG,myId);
            Log.d(TAG,postId);
            int hash = ph.getHash(myId,postId);
            if(hash == ownerHash)
                ph.addPost(postText.getText().toString(),"comments/"+postId+"/","OP","#666699",hash);
            else
                ph.addPost(postText.getText().toString(),"comments/"+postId+"/",hash);
        }
    }
}
