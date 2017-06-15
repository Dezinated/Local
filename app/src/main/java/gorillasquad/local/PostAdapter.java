package gorillasquad.local;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private MainPostHandler mph;
    private PostHandler ph;

    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PostAdapter(Context context, ArrayList<Post> items,PostHandler ph) {
        super(context, R.layout.post, items);
        this.mph = mph;
        this.ph = ph;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.post,null);

        final Post p = getItem(position);

        TextView postText = (TextView) v.findViewById(R.id.postText);
        TextView rating = (TextView) v.findViewById(R.id.rating);

        postText.setText(p.getText());
        rating.setText(p.getRating()+"");

        ImageButton upVoteButton = (ImageButton) v.findViewById(R.id.upVoteButton);
        ImageButton downVoteButton = (ImageButton) v.findViewById(R.id.downVoteButton);

        Button reportButton = (Button) v.findViewById(R.id.reportButton);
        Button commentButton = (Button) v.findViewById(R.id.commentButton);

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.vote(p.getKey(),true);
            }
        });
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.vote(p.getKey(),false);
            }
        });


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPost.class);
                intent.putExtra("postId",p.getKey());
                getContext().startActivity(intent);
            }
        });

        return v;
    }

}
