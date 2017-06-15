package gorillasquad.local;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 6/14/2017.
 */

public class CommentAdapter extends ArrayAdapter<Post> {

    private PostHandler ph;

    public CommentAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CommentAdapter(Context context, ArrayList<Post> items,PostHandler ph) {
        super(context, R.layout.post, items);
        this.ph = ph;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.comment,null);

        final Post p = getItem(position);

        return v;
    }
}
