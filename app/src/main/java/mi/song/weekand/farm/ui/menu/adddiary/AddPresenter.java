package mi.song.weekand.farm.ui.menu.adddiary;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddPresenter implements AddInterface.Presenter {
    AddInterface.View view;
    FirebaseUser user;
    FirebaseFirestore db;

    CollectionReference ref;

    public AddPresenter(AddInterface.View view, FirebaseUser user){
        this.view = view;
        this.user = user;

        db = FirebaseFirestore.getInstance();
        ref = db.collection("corp_list");

    }

    @Override
    public void saveDiary(String title, String contents) {
        HashMap<String, String> data = new HashMap<>();
        data.put("uid", user.getUid());
        data.put("title", title);
        data.put("contents", contents);

        ref.document().set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.sendMessage("일기 저장에 성공하였습니다.");
                        view.clear();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.sendMessage("내부에 이상이 있어 일기 저장에 실패했습니다.");
                        e.printStackTrace();
                    }
                });
    }
}