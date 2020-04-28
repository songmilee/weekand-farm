package mi.song.weekand.farm.ui.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public interface LoginInterface {
    interface View{
        void launchCorpActivity(FirebaseUser user);
        void setEmailFieldNotify(String msg);
        void setPwFieldNotify(String msg);
        void showProgress();
        void dismissProgress();
        void sendMessage(String msg);
    }

    interface Presenter{
        boolean isValidateForm(String email, String pw);
        void loginWithAccount(FirebaseAuth auth, String email, String pw);
        void createUserAccount(FirebaseAuth auth, String email, String pw);
    }
}
