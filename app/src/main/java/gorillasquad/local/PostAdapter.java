package gorillasquad.local;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jason on 6/13/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {


    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PostAdapter(Context context, ArrayList<Post> items) {
        super(context, R.layout.post, items);
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
            TextView tt1 = (TextView) v.findViewById(R.id.postText);;
            tt1.setText(p.getText());
        }

        return v;
    }
}
