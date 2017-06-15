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

    public String addNew(String location){
        Map<String,Object> values = new HashMap<>();
        DatabaseReference db = database.getReference(location);
        DatabaseReference newAddition = db.push();

        return newAddition.getKey().toString();
    }

    public void set(String location,Map<String,Object> values) {
        DatabaseReference db = database.getReference(location);
        db.setValue(values);
    }

    public void update(String location,Map<String,Object> values) {
        DatabaseReference db = database.getReference(location);
        db.updateChildren(values);
    }

    public void getValue(){
        DatabaseReference db = database.getReference();

    }

    public DatabaseReference getRef(String location){
        return database.getReference("root/"+location);
    }

}
