package mi.song.weekand.farm.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class FireStoreDB {
    private final String TAG = FireStoreDB.class.getSimpleName();

    private FirebaseFirestore db;

    public FireStoreDB(){
        db = FirebaseFirestore.getInstance();
    }

    public void addData(String collections, HashMap<String, Object> data){
        db.collection(collections)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document added with id : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "firestore error message : " + e.getMessage());
                    }
                });
    }

    public void readData(String collections){
        db.collection(collections).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot document = task.getResult();
                        } else {
                            Log.d(TAG, "Error getting documents : " + task.getException().getMessage());
                        }
                    }
                });
    }
}
