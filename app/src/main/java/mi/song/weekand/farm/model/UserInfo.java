package mi.song.weekand.farm.model;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

public class UserInfo {
    @NonNull
    String uuid;

    String name;
    String email;
    Uri photoUri;

    public UserInfo(FirebaseUser user){
        uuid = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
        photoUri = user.getPhotoUrl();
    }


}
