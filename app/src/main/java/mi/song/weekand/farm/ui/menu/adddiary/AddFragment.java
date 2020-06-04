package mi.song.weekand.farm.ui.menu.adddiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentAddBinding;
import mi.song.weekand.farm.util.ImageUtils;
import mi.song.weekand.farm.util.RequestCode;

public class AddFragment extends Fragment implements AddInterface.View {
    private FragmentAddBinding fragmentAddBinding;

    private AddInterface.Presenter presenter;
    private FirebaseUser user;

    private String photoUri = null;

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false);
        init();

        return fragmentAddBinding.getRoot();
    }

    private void init(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        presenter = new AddPresenter(this, user);

        fragmentAddBinding.addMenuToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.add_menu_submit:
                        String title = fragmentAddBinding.addFormTitle.getText().toString();
                        String contents = fragmentAddBinding.addFormContents.getText().toString();

                        showProgressBar();
                        presenter.saveDiary(title, contents, photoUri);
                        break;

                    case R.id.add_menu_img_add:
                        updateImages();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void sendMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateImages() {
        new ImageUtils(this).showPhotoSelectDialog(photoUri);
    }

    @Override
    public void clear() {
        fragmentAddBinding.addFormTitle.setText("");
        fragmentAddBinding.addFormContents.setText("");
        fragmentAddBinding.addFormImg.setImageResource(R.drawable.img_placeholder);
    }

    @Override
    public void showProgressBar() {
        fragmentAddBinding.addProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar() {
        fragmentAddBinding.addProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RequestCode.REQ_PHOTO_IMG && resultCode == Activity.RESULT_OK){
            if(data != null){
                photoUri = data.getData().toString();
                Glide.with(this).load(photoUri).placeholder(R.drawable.img_placeholder).centerCrop().into(fragmentAddBinding.addFormImg);
            }
        } else if(resultCode == RequestCode.REQ_CAMERA_IMG && resultCode == Activity.RESULT_OK){
            Glide.with(this).load(photoUri).placeholder(R.drawable.img_placeholder).centerCrop().into(fragmentAddBinding.addFormImg);
        }
    }
}
