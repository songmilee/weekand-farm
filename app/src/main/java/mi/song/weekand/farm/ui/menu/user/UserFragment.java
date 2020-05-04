package mi.song.weekand.farm.ui.menu.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

import mi.song.weekand.farm.BuildConfig;
import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentUserBinding;
import mi.song.weekand.farm.util.FileUtils;
import mi.song.weekand.farm.util.RequestCode;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends Fragment implements UserMenuInterface.View {
    private String TAG = UserFragment.class.getSimpleName();

    private FragmentUserBinding binding;
    private FirebaseUser user;

    private boolean isEdit = false;
    private String photoUri = null;

    private UserMenuInterface.Presenter presenter;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "on create view");
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_user, container, false);
        binding.setUser(this);

        init();

        return binding.getRoot();
    }

    private void init(){
        binding.userToolbar.setOnMenuItemClickListener(v ->{
            int id = v.getItemId();
            switch (id){
                case R.id.user_menu_edit:
                    setEditable(true);
                    showEditMenu(true);

                    break;

                case R.id.user_menu_ok:
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
        presenter = new UserMenuPresenter(this, user);

        //set user info
        binding.userInfoName.setText(user.getDisplayName());
        binding.userInfoEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
    }

    @Override
    public void showEditMenu(boolean isShow){
        binding.userToolbar.getMenu().findItem(R.id.user_menu_ok).setVisible(isShow);
        binding.userToolbar.getMenu().findItem(R.id.user_menu_add).setVisible(isShow);
        binding.userToolbar.getMenu().findItem(R.id.user_menu_edit).setVisible(!isShow);
    }

    @Override
    public void setEditable(boolean setEdit){
        isEdit = setEdit;

        binding.userInfoName.setFocusable(setEdit);
        binding.userInfoName.setFocusableInTouchMode(setEdit);

        binding.userInfoEmail.setFocusable(setEdit);
        binding.userInfoEmail.setFocusableInTouchMode(setEdit);
    }

    private void updateInfo(){
        hideKeyboard();

        String email = binding.userInfoEmail.getText().toString();
        String name = binding.userInfoName.getText().toString();

        boolean result = presenter.updateInfo(name, photoUri, email);
        if(!result){
            setEditable(false);
            showEditMenu(false);
        }
    }

    @Override
    public void sendMessage(String msg) {
        if(msg != null)
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), getString(R.string.user_update_info), Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(binding.userLayout.getWindowToken(), 0);
    }

    @Override
    public void showPhotoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.user_menu_img_title)
                            .setItems(R.array.user_menu_img_dialog, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            getImageByPhoto();
                                            break;
                                        case 1:
                                            getImageByCamera();
                                            break;
                                    }
                                }
                            })
                            .create();

        dialog.show();
    }

    private void getImageByPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(intent, RequestCode.REQ_PHOTO_IMG);
    }

    private void getImageByCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;
            FileUtils fileUtils = new FileUtils();
            try{
                photoFile = fileUtils.createTempFile(getActivity());

                if(photoFile != null){
                    Uri pUri = null;

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        pUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID, photoFile);
                    } else {
                        pUri = Uri.fromFile(photoFile);
                    }

                    photoUri = pUri.toString();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pUri);
                    startActivityForResult(intent, RequestCode.REQ_CAMERA_IMG);
                }
            } catch (Exception e) {
                Log.d(TAG, "error occured : " + e.getMessage());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode.REQ_PHOTO_IMG && resultCode == RESULT_OK){
            if (data != null){
                Uri pUri = data.getData();
                photoUri = pUri.toString();

                Glide.with(this).load(pUri).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
            }
        } else if(requestCode == RequestCode.REQ_CAMERA_IMG && resultCode == RESULT_OK){
            Glide.with(this).load(photoUri).placeholder(R.drawable.ic_person_black_24dp).centerCrop().into(binding.userInfoProfile);
        }
    }
}
