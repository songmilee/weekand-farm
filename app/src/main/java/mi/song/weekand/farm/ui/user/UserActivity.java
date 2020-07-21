package mi.song.weekand.farm.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentUserBinding;
import mi.song.weekand.farm.model.Photo;
import mi.song.weekand.farm.util.ImageUtils;
import mi.song.weekand.farm.util.ProgressUtil;
import mi.song.weekand.farm.util.RequestCode;

public class UserActivity extends AppCompatActivity implements UserInterface.View {
    private String TAG = UserActivity.class.getSimpleName();

    private FragmentUserBinding binding;
    private FirebaseUser user;

    private boolean isEdit = false;
    private Photo photo = null;

    private UserInterface.Presenter presenter;
    private ProgressUtil progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress = ProgressUtil.getInstance(getContext());
        photo = new Photo();

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_user);
        binding.setUser(this);

        init();
    }

    private void init() {
        binding.userToolbar.setOnMenuItemClickListener(v -> {
            int id = v.getItemId();
            switch (id) {
                case R.id.user_menu_edit:
                    setEditable(true);
                    showEditMenu(true);

                    break;

                case R.id.user_menu_ok:
                    progress.showDialog();
                    updateInfo();
                    break;

                case R.id.user_menu_add:
                    showPhotoDialog();
                    break;
            }
            return true;
        });

        //get firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //set presenter
        presenter = new UserPresenter(this, user);

        //set user info
        binding.userInfoName.setText(user.getDisplayName());
        binding.userInfoEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
    }

    @Override
    public void showEditMenu(boolean isShow) {
        binding.userToolbar.getMenu().findItem(R.id.user_menu_ok).setVisible(isShow);
        binding.userToolbar.getMenu().findItem(R.id.user_menu_add).setVisible(isShow);
        binding.userToolbar.getMenu().findItem(R.id.user_menu_edit).setVisible(!isShow);
    }

    @Override
    public void setEditable(boolean setEdit) {
        isEdit = setEdit;

        binding.userInfoName.setFocusable(setEdit);
        binding.userInfoName.setFocusableInTouchMode(setEdit);

        binding.userInfoEmail.setFocusable(setEdit);
        binding.userInfoEmail.setFocusableInTouchMode(setEdit);
    }

    private void updateInfo() {
        hideKeyboard();

        String email = binding.userInfoEmail.getText().toString();
        String name = binding.userInfoName.getText().toString();

        boolean result = presenter.updateInfo(name, photo.getUri(), email);
        if (!result) {
            setEditable(false);
            showEditMenu(false);
        }
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public void sendMessage(String msg) {
        progress.dismissDialog();

        if (msg != null)
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), getString(R.string.user_update_info), Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(binding.userLayout.getWindowToken(), 0);
    }

    @Override
    public void showPhotoDialog() {

        new ImageUtils(this).showPhotoSelectDialog(photo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.REQ_PHOTO_IMG && resultCode == RESULT_OK) {
            if (data != null) {
                Uri pUri = data.getData();
                photo.setUri(pUri.toString());

                Glide.with(this).load(pUri).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
            }
        } else if (requestCode == RequestCode.REQ_CAMERA_IMG && resultCode == RESULT_OK) {
            Glide.with(this).load(photo.getUri()).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
        }
    }

    @Override
    public void showProgress() {
        progress.showDialog();
    }

    @Override
    public void dismissProgress() {
        progress.dismissDialog();
    }
}
