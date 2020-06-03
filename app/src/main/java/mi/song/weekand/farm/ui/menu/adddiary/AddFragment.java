package mi.song.weekand.farm.ui.menu.adddiary;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentAddBinding;

public class AddFragment extends Fragment implements AddInterface.View {
    private FragmentAddBinding fragmentAddBinding;

    private AddInterface.Presenter presenter;
    private FirebaseUser user;

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

                        presenter.saveDiary(title, contents);
                        break;

                    case R.id.add_menu_img_add:
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

    }

    @Override
    public void clear() {
        fragmentAddBinding.addFormTitle.setText("");
        fragmentAddBinding.addFormContents.setText("");
    }
}
