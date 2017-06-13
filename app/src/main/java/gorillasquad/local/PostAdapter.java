package gorillasquad.local;

import android.content.Context;
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

    private PostHandler ph;

    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PostAdapter(Context context, ArrayList<Post> items, PostHandler ph) {
        super(context, R.layout.post, items);
        this.ph = ph;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.post,null);
        }

        Post p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.postText);
            tt1.setText(p.getText());
        }

        ImageButton upVoteButton = (ImageButton) v.findViewById(R.id.upVoteButton);
        ImageButton downVoteButton = (ImageButton) v.findViewById(R.id.downVoteButton);

        Button reportButton = (Button) v.findViewById(R.id.reportButton);
        Button commentButton = (Button) v.findViewById(R.id.commentButton);

        upVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ph.upVote(p.getKey());
            }
        });
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ph.downVote(p.getKey());
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
            }
        });

        return v;
    }

}
