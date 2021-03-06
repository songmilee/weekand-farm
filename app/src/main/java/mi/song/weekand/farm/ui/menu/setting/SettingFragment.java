package mi.song.weekand.farm.ui.menu.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import mi.song.weekand.farm.R;
import mi.song.weekand.farm.databinding.FragmentSettingBinding;
import mi.song.weekand.farm.ui.login.LoginActivity;
import mi.song.weekand.farm.ui.user.UserActivity;


public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        init();

        return binding.getRoot();
    }

    private void init() {
        binding.settingMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        launchUserInfo();
                        break;
                    case 1:
                        break;
                    case 2:
                        logOut();
                        launchLoginActivity();
                        break;
                }
            }
        });
    }

    private void launchUserInfo() {
        startActivity(new Intent(getContext(), UserActivity.class));
    }

    private void logOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
