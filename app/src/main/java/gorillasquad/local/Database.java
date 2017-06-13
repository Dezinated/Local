package gorillasquad.local;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        for(int i=0;i<values.length;i++) {
            newAddition.child(keys[i]).setValue(values[i]);
        }
    }

}
