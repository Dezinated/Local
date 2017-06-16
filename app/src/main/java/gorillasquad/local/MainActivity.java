package gorillasquad.local;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";

    PostHandler ph;
    String myId;

    Dialog connectingDialog ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Remove title bar


        mAuth = FirebaseAuth.getInstance();
        connectingDialog = getLoadingDialog();
        connectingDialog.show();


        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Logging in");
                if (task.isSuccessful()) {
                    connectingDialog.dismiss();
                    Log.d(TAG, "signInAnonymously:success");
                    myId = mAuth.getCurrentUser().getUid();
                    ph = new PostHandler(myId,MainActivity.this);

                    ListView postList = (ListView) findViewById(R.id.postList);
                    postList.setAdapter(ph.getMph().getPostAdapter());

                    LayoutInflater myinflater = getLayoutInflater();
                    ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.enter_message, postList, false);
                    postList.addHeaderView(myHeader, null, true);


                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
            }
        });
    }


    public void buttonClicked(View v) {
        int amount = 3;

        v = (View) v.getParent();
        EditText postText = (EditText) v.findViewById(R.id.newPost);
        if(postText.getText().length() < amount) {
            Toast.makeText(MainActivity.this, "Enter a message with more than "+amount+" characters.", Toast.LENGTH_SHORT).show();
        }else {
            ph.addPost(postText.getText().toString(),"post",0);
        }
    }

    private Dialog getLoadingDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Loading");
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.connecting, null));
        builder.setNegativeButton("Close App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setCancelable(false);
        return builder.create();
    }

}
