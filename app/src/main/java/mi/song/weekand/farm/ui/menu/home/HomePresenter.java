package mi.song.weekand.farm.ui.menu.home;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;

public class HomePresenter implements HomeInterface.Presenter {
    FirebaseFirestore db;
    FirebaseUser user;
    CollectionReference ref;
    HomeInterface.View view;

    Observable firebaseResult;

    public HomePresenter(HomeInterface.View view){
        this.view = view;

        db = FirebaseFirestore.getInstance();
        ref = db.collection("corp_list");
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    //rxjava2를 이용한 firestore 데이터 읽어오기
    private void observeByTime(Long time){
        firebaseResult = Observable.create(subscriber -> {
            //복합색인 설정
            ref.orderBy("createdat")
                    .whereGreaterThan("createdat", time)
                    .whereEqualTo("uid", user.getUid())
                    .limit(10)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        ArrayList<Map<String, Object>> dataList = new ArrayList<>();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            dataList.add(document.getData());
                        }

                        subscriber.onNext(dataList);
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(task.getException());
                    }
                }
            });
        });
    }

    private void observeByMax(){
        firebaseResult = Observable.create(subscriber -> {
            //복합색인 설정
            ref.orderBy("createdat", Query.Direction.DESCENDING)
                    .whereEqualTo("uid", user.getUid())
                    .limit(10)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        ArrayList<Map<String, Object>> dataList = new ArrayList<>();
                        for(QueryDocumentSnapshot document : task.getResult()){
                            dataList.add(0, document.getData());
                        }

                        subscriber.onNext(dataList);
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(task.getException());
                    }
                }
            });
        });
    }

    public void getCorpItemList(){
        observeByMax();
        publishCorpList();
    }

    public void getCorpItemList(Long time){
        observeByTime(time);
        publishCorpList();
    }

    private void publishCorpList(){
        firebaseResult.subscribe(
                next -> view.setCorpItemData((ArrayList<Map<String, Object>>)next),
                error -> view.sendMessage(((Exception)error).getMessage())
        );
    }
}
