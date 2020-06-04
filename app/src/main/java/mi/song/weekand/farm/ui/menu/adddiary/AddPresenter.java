package mi.song.weekand.farm.ui.menu.adddiary;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import mi.song.weekand.farm.R;

public class AddPresenter implements AddInterface.Presenter {
    AddInterface.View view;
    FirebaseUser user;
    FirebaseFirestore db;

    CollectionReference ref;
    FirebaseStorage storage;

    public AddPresenter(AddInterface.View view, FirebaseUser user){
        this.view = view;
        this.user = user;

        db = FirebaseFirestore.getInstance();
        ref = db.collection("corp_list");
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public void saveDiary(String title, String contents, String photoUri) {
        if(photoUri != null){
            StorageReference ref = createStoragePath();
            ref.putFile(Uri.parse(photoUri)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri downUri = task.getResult();
                        insertCorpDiary(title, contents, downUri.toString());
                    } else {
                        view.sendMessage("Sorry Error Occurred when upload the image");
                    }

                    view.closeProgressBar();
                }
            });
        } else {
            insertCorpDiary(title, contents, null);
        }
    }

    private void insertCorpDiary(String title, String contents, String url){
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid());
        data.put("title", title);
        data.put("contents", contents);
        if(url != null) data.put("url", url);

        ref.document()
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.sendMessage(view.getContext().getString(R.string.save_success));
                        view.closeProgressBar();
                        view.clear();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.sendMessage(view.getContext().getString(R.string.save_fail));
                        view.closeProgressBar();
                        e.printStackTrace();
                    }
                });
    }

    private StorageReference createStoragePath(){
        String filename = System.currentTimeMillis() + ".jpg";
        return storage.getReference().child(filename);
    }

}