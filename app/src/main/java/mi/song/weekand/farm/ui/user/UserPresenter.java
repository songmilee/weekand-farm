package mi.song.weekand.farm.ui.user;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserPresenter implements UserInterface.Presenter {
    private String TAG = UserPresenter.class.getSimpleName();

    private FirebaseUser user;
    private UserInterface.View view;

    public UserPresenter(UserInterface.View view, FirebaseUser user){
        this.view = view;
        this.user = user;
    }

    @Override
    public boolean validateEmailForm(String email) {
        if(email.equals(user.getEmail())) return false;
        if(TextUtils.isEmpty(email))
            return false;

        String pattern = "([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)";
        boolean result = pattern.matches(email);

        return result;
    }

    @Override
    public boolean validateDisplayName(String name) {
        if(name.equals(user.getDisplayName())) return false;

        if(TextUtils.isEmpty(name))
            return false;
        return true;
    }

    @Override
    public void updateEmail(String email) {
        user.updateEmail(email)
            .addOnSuccessListener(v -> {
               view.sendMessage(null);
            })
            .addOnFailureListener(v -> {
                Log.d(TAG, "update failed : " + v.getMessage());
            });

        view.setEditable(false);
        view.showEditMenu(false);
    }

    @Override
    public void updateProfile(String userName, String photoUri) {
        UserProfileChangeRequest request;

        if(photoUri != null)
            request = new UserProfileChangeRequest.Builder().setDisplayName(userName).setPhotoUri(Uri.parse(photoUri)).build();
        else
            request = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();

        user.updateProfile(request)
            .addOnSuccessListener(v -> {
                view.sendMessage(null);
            })
            .addOnFailureListener(v -> {
                Log.d(TAG, "update failed : " + v.getMessage());
            });

        view.setEditable(false);
        view.showEditMenu(false);
    }

    @Override
    public boolean updateInfo(String userName, String photoUri, String email) {
        boolean isUpdate = false;

        if(validateDisplayName(userName) || photoUri != null){
            isUpdate = true;
            updateProfile(userName, photoUri);
        } else {
            view.dismissProgress();
            return false;
        }

        if(validateEmailForm(email)){
            updateEmail(email);
        } else if(!isUpdate) {
            view.dismissProgress();
            return false;
        }

        return true;
    }
}
