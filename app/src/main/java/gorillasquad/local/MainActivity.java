package gorillasquad.local;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PostAdapter pa;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posts = new ArrayList<>();

        ListView yourListView = (ListView) findViewById(R.id.postList);
        pa = new PostAdapter(this, posts);
        yourListView.setAdapter(pa);



        pa.add(new Post("Test"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.add(new Post("Test2"));
        pa.notifyDataSetChanged();
    }


}
