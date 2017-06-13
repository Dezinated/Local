package gorillasquad.local;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 6/13/2017.
 */

public class Database {

    private static Database instance = null;
    private FirebaseDatabase database;

    private Database() {
        database = FirebaseDatabase.getInstance();
    }

    public static Database getDB() {
        if(instance == null)
            instance = new Database();

        return instance;
    }

    public void addNew(String location,String[] keys, Object... values ){
        DatabaseReference db = database.getReference(location);

        DatabaseReference newAddition = db.push();
        Map entry = new HashMap<String,Object>();
        for(int i=0;i<values.length;i++) {
            if(keys[i].equals("key")) {
                entry.put("key", newAddition.getKey().toString());
            }else {
                entry.put(keys[i], values[i]);
            }
            //dont do this
            //newAddition.child(keys[i]).setValue(values[i]);
            //it will make onchildadded only trigger once and onchildchanged multiple times
            //just update info all in 1 go
        }
        newAddition.setValue(entry);
    }

    public void getValue(){
        DatabaseReference db = database.getReference();

    }

    public DatabaseReference getPostsRef(){
        return database.getReference("root/post");
    }

}
