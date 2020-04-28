package mi.song.weekand.farm.ui.login;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginInterface.Presenter {
    LoginInterface.View view;

    public LoginPresenter(LoginInterface.View view){
        this.view = view;
    }

    @Override
    public boolean isValidateForm(String email, String pw) {
        boolean result = true;

        if(TextUtils.isEmpty(email)){
            result = false;
            view.setEmailFieldNotify("required!");
        } else {
            view.setEmailFieldNotify(null);
        }

        if(TextUtils.isEmpty(pw)){
            result = false;
            view.setPwFieldNotify("required!");
        } else {
            view.setPwFieldNotify(null);
        }

        return result;
    }

    @Override
    public void loginWithAccount(FirebaseAuth auth, String email, String pw) {
        auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                view.dismissProgress();

                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    view.launchCorpActivity(user);
                } else {
                    view.sendMessage(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void createUserAccount(FirebaseAuth auth, String email, String pw) {
        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                view.dismissProgress();

                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    view.launchCorpActivity(user);
                } else {
                    view.sendMessage(task.getException().getMessage());
                }
            }
        });

    }
}
