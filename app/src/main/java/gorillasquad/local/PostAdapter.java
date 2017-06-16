package gorillasquad.local;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private String TAG = "PostAdapter";
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
                ph.votePost(p,true);
            }
        });
        downVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph.votePost(p,false);
            }
        });



        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Reporting to the cyber cops");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Report Post");
                mBuilder.setMessage("Are you sure you want to report this post?");
                mBuilder.setPositiveButton("Report", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast toast = Toast.makeText(getContext(), "Post reported for review", Toast.LENGTH_LONG);
                        toast.show();
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog aleartDialog = mBuilder.create();
                aleartDialog.show();

            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPost.class);
                Log.d(TAG,p.getKey());
                intent.putExtra("postId",p.getKey());
                intent.putExtra("ownerHash",p.getOwnerHash());
                getContext().startActivity(intent);
            }
        });

        return v;
    }

}
