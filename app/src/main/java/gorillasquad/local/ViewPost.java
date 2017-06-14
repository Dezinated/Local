package gorillasquad.local;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by Jason on 6/14/2017.
 */

public class ViewPost extends AppCompatActivity {

    private String TAG = "ViewPost";

    private Post p;
    private CommentAdapter ca;

    public ViewPost() {

    }

    public ViewPost(Post p) {
        this.p = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);
        setTitle("");
        Log.d(TAG,"Created view post");

        ListView postList = (ListView) findViewById(R.id.commentsList);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)myinflater.inflate(R.layout.view_post, postList, false);
        ViewGroup footer = (ViewGroup)myinflater.inflate(R.layout.enter_message, postList, false);
        postList.addHeaderView(header, null, false);
        postList.addFooterView(footer, null, false);

        ArrayList<Post> comments = new ArrayList<>();
        comments.add(new Post());
        ca = new CommentAdapter(this,comments);

        postList.setAdapter(ca);
        ca.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
