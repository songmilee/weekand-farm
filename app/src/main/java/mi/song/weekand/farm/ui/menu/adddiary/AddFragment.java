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
import mi.song.weekand.farm.model.Photo;
import mi.song.weekand.farm.util.ImageUtils;
import mi.song.weekand.farm.util.ProgressUtil;
import mi.song.weekand.farm.util.RequestCode;

public class AddFragment extends Fragment implements AddInterface.View {
    private FragmentAddBinding fragmentAddBinding;

    private AddInterface.Presenter presenter;
    private FirebaseUser user;

    private ProgressUtil progress;

    private Photo photo;

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress = ProgressUtil.getInstance(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        presenter = new AddPresenter(this, user);
        photo = new Photo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAddBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false);
        init();

        return fragmentAddBinding.getRoot();
    }

    private void init(){
        fragmentAddBinding.addMenuToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.add_menu_submit:
                        String title = fragmentAddBinding.addFormTitle.getText().toString();
                        String contents = fragmentAddBinding.addFormContents.getText().toString();

                        showProgressBar();
                        presenter.saveDiary(title, contents, photo);
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
        new ImageUtils(this).showPhotoSelectDialog(photo);
    }

    @Override
    public void clear() {
        fragmentAddBinding.addFormTitle.setText("");
        fragmentAddBinding.addFormContents.setText("");
        fragmentAddBinding.addFormImg.setImageResource(R.drawable.img_placeholder);
    }

    @Override
    public void showProgressBar() {
        progress.showDialog();
    }

    @Override
    public void closeProgressBar() {
        progress.dismissDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RequestCode.REQ_PHOTO_IMG && resultCode == Activity.RESULT_OK){
            if(data != null){
                photo.setUri(data.getData().toString());
                Glide.with(this).load(photo.getUri()).placeholder(R.drawable.img_placeholder).centerCrop().into(fragmentAddBinding.addFormImg);
            }
        } else if(resultCode == RequestCode.REQ_CAMERA_IMG && resultCode == Activity.RESULT_OK){
            Glide.with(this).load(photo.getUri()).placeholder(R.drawable.img_placeholder).centerCrop().into(fragmentAddBinding.addFormImg);
        }
    }
}
