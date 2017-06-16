package gorillasquad.local;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jason on 6/14/2017.
 */

public class ViewPost extends AppCompatActivity {

    private String TAG = "ViewPost";

    private PostHandler ph;
    private String myId;
    private Post p;

    public ViewPost() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        setTitle("");
        Log.d(TAG,"Created view post");

        p = getIntent().getParcelableExtra("post");

        myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ph = new PostHandler(myId,this, p);

        ListView commentList = (ListView) findViewById(R.id.commentsList);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)myinflater.inflate(R.layout.view_post, commentList, false);
        ViewGroup footer = (ViewGroup)myinflater.inflate(R.layout.enter_message, commentList, false);
        commentList.addHeaderView(header, null, false);
        commentList.addFooterView(footer, null, false);




        ph.getCh().setPost(p);
        commentList.setAdapter(ph.getCh().getCommentAdapter());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView postText = (TextView) findViewById(R.id.viewPostText);
        final TextView ratingText = (TextView) findViewById(R.id.viewPostRating);
        postText.setText(p.getText());
        ratingText.setText(p.getRating()+"");

        final ImageButton upVoteButton = (ImageButton) findViewById(R.id.upVoteButton);
        final ImageButton downVoteButton = (ImageButton) findViewById(R.id.downVoteButton);

        if(p.getUpVotes().contains(myId)){
            upVoteButton.setImageResource(R.drawable.up_arrow_highlight);
        }else if(p.getDownVotes().contains(myId)){
            downVoteButton.setImageResource(R.drawable.down_arrow_highlight);
        }

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.votePost(p,true);
                ratingText.setText(p.getRating()+"");
                upVoteButton.setImageResource(R.drawable.up_arrow);
                downVoteButton.setImageResource(R.drawable.down_arrow);
                if(p.getUpVotes().contains(myId)){
                    upVoteButton.setImageResource(R.drawable.up_arrow_highlight);
                }else if(p.getDownVotes().contains(myId)){
                    downVoteButton.setImageResource(R.drawable.down_arrow_highlight);
                }
            }
        });
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.votePost(p,false);
                ratingText.setText(p.getRating()+"");
                upVoteButton.setImageResource(R.drawable.up_arrow);
                downVoteButton.setImageResource(R.drawable.down_arrow);
                if(p.getUpVotes().contains(myId)){
                    upVoteButton.setImageResource(R.drawable.up_arrow_highlight);
                }else if(p.getDownVotes().contains(myId)){
                    downVoteButton.setImageResource(R.drawable.down_arrow_highlight);
                }
            }
        });


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
            int hash = ph.getHash(myId,p.getKey());
            ph.addPost(postText.getText().toString(),"comments/"+p.getKey()+"/",hash);
        }
    }
}
