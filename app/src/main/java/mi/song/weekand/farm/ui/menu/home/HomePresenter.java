package mi.song.weekand.farm.ui.menu.home;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomePresenter implements HomeInterface.Presenter {
    FirebaseFirestore db;
    FirebaseUser user;
    CollectionReference ref;
    HomeInterface.View view;

    public HomePresenter(HomeInterface.View view){
        this.view = view;

        db = FirebaseFirestore.getInstance();
        ref = db.collection("corp_list");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getCorpItemList(){
        ref.whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Map<String, Object>> dataList = new ArrayList<>();

                    for(QueryDocumentSnapshot document : task.getResult()){
                        dataList.add(document.getData());
                    }

                    view.setCorpItemData(dataList);

                } else {
                    view.sendMessage("Load data failed");
                }
            }
        });
    }
}
